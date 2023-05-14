import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;

public class LockUsageMain {

    static final class RWLockUsage {
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();
        private final Map<String, Integer> map = new HashMap<>();

        public void addOrUpdate(String key, Integer val) {
            writeLock.lock();
            try {
                map.put(key, val);
            } finally {
                writeLock.unlock();
            }
        }

        public Integer getKey(String key) {
            readLock.lock();
            try {
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }

        public Set<String> getKeys() {
            readLock.lock();
            try {
                return map.keySet();
            } finally {
                readLock.unlock();
            }
        }
    }

    static final class ReentrantLockUsage {
        private final ReentrantLock lock;
        private final Condition condition;
        private final Object syncLock = new Object();

        public ReentrantLockUsage(ReentrantLock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        public void execute() {
            //synchronized (syncLock) {
                System.out.println("current id = "+Thread.currentThread().getId());
                lock.lock();
                try {
                    Thread.sleep(3000);
                    System.out.println("Current ID = "+Thread.currentThread().getId()+" ");
                    condition.await();
                    //syncLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            //}
        }
    }

    public static void main(String[] args) {
        /*RWLockUsage rwLockUsage = new RWLockUsage();
        Thread t1 = new Thread(()-> {
            for(int i = 0; i < 100; i++) {
                rwLockUsage.addOrUpdate(i+"", i);
            }
        });
        Thread t2 = new Thread(() -> {
           for(int i = 0; i < 100; i++) {
               System.out.println(rwLockUsage.getKey(i+""));
           }
        });
        t1.start();
        t2.start();*/

        ReentrantLock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();
        ReentrantLockUsage reentrantLockUsage = new ReentrantLockUsage(lock, condition);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                int sleepTime = ThreadLocalRandom.current().nextInt(0, 10);
                /*try {
                    Thread.sleep(sleepTime*1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }*/
                System.out.println("Current ID = "+Thread.currentThread().getId());
                reentrantLockUsage.execute();
            });
            t.start();
        }
        //t.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
