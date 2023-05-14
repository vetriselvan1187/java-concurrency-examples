import java.util.concurrent.CountDownLatch;

public class CountDownLatchMain {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);

        for(int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                // some processing here
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                latch.countDown();
                System.out.println("count = "+latch.getCount());
            });
            t.start();
        }

        try {
            System.out.println("Waiting for all threads to finish");
            latch.await();// count reaches zero on latch
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
