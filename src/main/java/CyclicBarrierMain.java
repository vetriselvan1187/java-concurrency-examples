
import java.util.concurrent.*;

public class CyclicBarrierMain {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            System.out.println("let's proceed together");
        });

        Thread t = new Thread(() -> {

            for(int i = 0; i < 4; i++) {
                Thread thread = new Thread(() -> {
                    for(int j = 0; j < 5; j++) {
                        try {
                            int sleepTime = ThreadLocalRandom.current().nextInt(1, 10);
                            System.out.println(sleepTime);
                            Thread.sleep(sleepTime * 1000);
                            /*if (sleepTime < 2) {
                                throw new InterruptedException("ex");
                            }*/
                            barrier.await();
                            System.out.println("threads started to proceed");
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } catch (BrokenBarrierException ex) {
                            ex.printStackTrace();
                        }
                        barrier.reset();
                    }
                });
                thread.start();
            }
        });
        t.start();
    }
}
