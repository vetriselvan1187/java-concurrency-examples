import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockMain {

    static final class Account {
        private final String name;
        private final Lock lock = new ReentrantLock();
        private long balance = 0;
        public Account(String name, long balance) {
            this.name = name;
            this.balance = balance;
        }

        public void transfer(Account to, long amount) {
            /*try {
                System.out.println("transferring amount from "+this.name+ " to " +to.name);
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }*/
            //System.out.println("transferring amount from "+this.name+ " to " +to.name);
            this.balance -= amount;
            to.credit(amount);
        }

        public void credit(long amount) {
            this.balance += amount;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "name='" + name + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    static final class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    static class DeadLockResolver {

        // we have ordered the lock
        public static boolean transfer(Account from, Account to, long amount) throws InsufficientBalanceException {

                /*synchronized (from) {
                    synchronized (to) {
                        from.transfer(to, amount);
                    }
                }*/

                while(true) {
                    boolean fromLock = from.lock.tryLock();
                    boolean toLock = false;
                    if (fromLock) {
                        try {
                            toLock = to.lock.tryLock();
                            if(toLock) {
                                try {
                                    if(from.balance-amount < 0) {
                                        throw new InsufficientBalanceException("insufficient balance");
                                    }
                                    System.out.println("amount transfer = "+from.name+" "+to.name);
                                    // critical section
                                    from.transfer(to, amount);
                                    return true;
                                } finally {
                                    to.lock.unlock();
                                }
                            }
                        } finally {
                            from.lock.unlock();
                        }
                    }
                    if(!fromLock || !toLock)
                        continue;
                }
            //return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account("account1", 1000);
        Account account2 = new Account("account2", 1000);

        /*Thread t1 = new Thread(() -> {
           //account1.transfer(account2, 150);
            DeadLockResolver.transfer(account2, account1, 100);

        });
        Thread t2 = new Thread(()-> {
           //account2.transfer(account1, 100);
            DeadLockResolver.transfer(account1, account2, 130);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(account1);
        System.out.println(account2);*/

        final int NUM_ACCOUNTS = 5;
        Account[] accounts = new Account[NUM_ACCOUNTS];
        for(int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts[i] = new Account(i+"", 10000);
        }
        final Random random = new Random();
        List<Thread> threadList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                for(int j = 0; j < 1000; j++) {
                    Account from = accounts[random.nextInt(NUM_ACCOUNTS)];
                    Account to = accounts[random.nextInt(NUM_ACCOUNTS)];
                    //System.out.println(" from name = "+from.name+" to name "+to.name);
                    if(!from.name.equals(to.name))
                        try {
                            System.out.println("FROM = "+from.name+" to = "+to.name);
                            boolean res = DeadLockResolver.transfer(from, to, random.nextInt(1000));
                            System.out.println("is transfer successful = "+res);
                        } catch (InsufficientBalanceException ex) {
                            ex.printStackTrace();
                        }
                }
            });
            t.start();
            threadList.add(t);
        }
        for(Thread t : threadList) {
            t.join();
        }
        long sum = 0;
        for(Account a : accounts) {
            sum += a.balance;
            System.out.println(a);
        }
        System.out.println(sum);
    }
}
