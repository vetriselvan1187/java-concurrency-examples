import java.util.concurrent.Semaphore;

public class SemaphoreMain {

    static final class Throttling {
        private final Semaphore semaphore;
        public Throttling(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void throttle() {
            try {
                semaphore.acquire(2); // acquire 1 permit
                // do some processing here
                System.out.println(" current Id = "+Thread.currentThread().getId());
                Thread.sleep(4000);
                semaphore.release(2); // release 1 permit
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(10, true);
        final Throttling throttling = new Throttling(semaphore);
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                throttling.throttle();
            });
            t.start();
        }
    }
}
