import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockUsageMain {

    static final class RWLockUsage {
        final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        final Lock readLock = reentrantReadWriteLock.readLock();
        final Lock writeLock = reentrantReadWriteLock.writeLock();
        final Map<String, Integer> map = new HashMap<>();

        public RWLockUsage() {}

        public void addOrUpdate(String key, Integer value) {
            writeLock.lock();
            try {
                System.out.println("write lock is obtained");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                map.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        public Integer get(String key) {
            readLock.lock();
            try {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("read lock is obtained");
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        RWLockUsage rwLockUsage = new RWLockUsage();

        for(int i = 0; i < 10; i++) {
            final int num = i;
            Thread t = new Thread(() -> {
               rwLockUsage.addOrUpdate(num+"", num);
            });
            t.start();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        for(int i = 0; i < 10; i++) {
            final int num = i;
            Thread t = new Thread(() -> {
               System.out.println(rwLockUsage.get(num+""));
            });
            t.start();
        }
    }
}
