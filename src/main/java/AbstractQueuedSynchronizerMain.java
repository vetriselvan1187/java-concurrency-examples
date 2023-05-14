import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/***
 * Creating Semaphore using AbstractQueueSynchronizer
 */
public class AbstractQueuedSynchronizerMain {

    static final class Semaphore {

        static class Sync extends AbstractQueuedSynchronizer {

            public Sync(int permits) {
                setState(permits);
            }

            public boolean tryAcquire(int acquires) {
                return tryAcquireShared(acquires) >= 0;
            }

            public int tryAcquireShared(int acquires) {
                for(;;) {
                    int available = getState();
                    int remaining = available-acquires;
                    if(remaining < 0 || compareAndSetState(available, remaining)) {
                        return remaining;
                    }
                }
            }

            public boolean tryRelease(int releases) {
                return tryReleaseShared(releases);
            }

            public boolean tryReleaseShared(int releases) {
                for(;;) {
                    int current = getState();
                    int next = current + releases;
                    if(next < current)
                        throw new Error("Max Count Reached");
                    if(compareAndSetState(current, next))
                        return true;
                }
            }
        }
        private final Sync sync;
        public Semaphore(int permits) {
            sync = new Sync(permits);
        }
        public void acquire(int permits) {
            sync.acquire(permits);
        }
        public void release(int permits) {
            sync.release(permits);
        }
        public boolean hasQueuedThreads() {
            return sync.hasQueuedThreads();
        }
        public Collection<Thread> getNumberOfWaitingThreads() {
            return sync.getQueuedThreads();
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(8);

        for(int i = 0; i < 20; i++) {
            Thread t = new Thread(() -> {
                semaphore.acquire(1);
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(0, 5)*1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("hasQueuedThreads = "+semaphore.hasQueuedThreads());
                List<Long> threadIds = new ArrayList<>();
                for(Thread thread : semaphore.getNumberOfWaitingThreads()) {
                    threadIds.add(thread.getId());
                }
                System.out.println("Waiting Threads = "+threadIds);
                semaphore.release(1);
            });
            t.start();
        }
    }
}
