import javax.swing.*;
import java.nio.file.AccessMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockMain {

    static final class Account {
        private final Integer id;
        private final String name;
        private final Lock lock = new ReentrantLock();
        private long balance = 0;

        public Account(Integer id, String name, long balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
        }
        public  void transfer(Account to, long amount) {
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }*/
            this.balance -= amount;
            to.credit(amount);
        }
        public void credit(long amount) {
            this.balance += amount;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    static final class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    static final class DeadLockResolver {

        public static void transfer(Account from, Account to, long amount) throws InsufficientBalanceException {

            /*Account first = from.id < to.id ? from : to;
            Account second = from != first ? from : to;

            synchronized (second) {
                synchronized (first) {
                    if(from.balance-amount < 0) {
                        throw new InsufficientBalanceException("Insufficient Balance Exception");
                    }
                    from.transfer(to, amount);
                }
            }*/

            while(true) {
                boolean fromLock = from.lock.tryLock();
                boolean toLock = false;
                if (fromLock) {
                    try {
                        toLock = to.lock.tryLock();
                        if (toLock) {
                            try {
                                from.transfer(to, amount);
                                return;
                            } finally {
                                to.lock.unlock();
                            }
                        }
                    } finally {
                        from.lock.unlock();
                    }
                }
                System.out.println(fromLock+" "+toLock);
                if (!fromLock || !toLock)
                    continue;
            }

            /*synchronized (from) {
                synchronized (to) {
                    from.transfer(to, amount);
                }
            }*/
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /*Account account1 = new Account(0, 0+"", 1000);
        Account account2 = new Account(1, 1+"", 1000);

        Thread t1 = new Thread(() -> {
           //account1.transfer(account2, 50);
            DeadLockResolver.transfer(account1, account2, 80);
        });
        Thread t2 = new Thread(() -> {
           //account2.transfer(account1, 50);
            DeadLockResolver.transfer(account2, account1, 50);

        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(account1);
        System.out.println(account2); */

        final int NUM_ACCOUNTS = 5;
        Account[] accounts = new Account[NUM_ACCOUNTS];
        for(int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts[i] = new Account(i, i+"", 1000);
        }
        final List<Thread> threadList = new ArrayList<>();
        final Random random = new Random();
        for(int i = 0; i < 5; i++) {
            Thread t = new Thread(()-> {
                for(int j = 0; j < 100; j++) {
                    Account from = accounts[random.nextInt(NUM_ACCOUNTS)];
                    Account to = accounts[random.nextInt(NUM_ACCOUNTS)];
                    if(!from.id.equals(to.id)) {
                        try {
                            DeadLockResolver.transfer(from, to, random.nextInt(1000));
                        } catch (InsufficientBalanceException e) {
                            e.printStackTrace();
                        }
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
        for(Account account : accounts) {
            sum += account.balance;
            System.out.println(account);
        }
        System.out.println(sum);
    }
}
