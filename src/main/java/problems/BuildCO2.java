package problems;

import java.util.concurrent.Semaphore;

/**
 * Build CO2
 */
public class BuildCO2 {
    static final class CO2 {
        final Semaphore semCarbon = new Semaphore(2);
        final Semaphore semOxygen = new Semaphore(0);
        public CO2() {}
        public void releaseCarbon(Runnable runnableCarbon) throws InterruptedException {
            semCarbon.acquire(2);
            runnableCarbon.run();
            semOxygen.release(2);
        }
        public void releaseOxygen(Runnable runnableOxygen) throws InterruptedException {
            semOxygen.acquire();
            runnableOxygen.run();
            semCarbon.release();
        }
    }

    public static void main(String[] args) {
        Runnable runnableCarbon = () -> System.out.print("C");
        Runnable runnableOxygen = () -> System.out.print("O");
        CO2 co2 = new CO2();
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    co2.releaseCarbon(runnableCarbon);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        for(int i = 0; i < 20; i++) {
            Thread t = new Thread(() -> {
                try {
                    co2.releaseOxygen(runnableOxygen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}
