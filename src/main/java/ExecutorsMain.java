import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorsMain {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        Runnable r = () -> {
            for(int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("It's printing");
            }
        };
        //service.execute(r);

        Callable c = () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return ThreadLocalRandom.current().nextInt(0, 10);
        };

        List<Future<Integer>> futureList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Future<Integer> future = service.submit(c);
            futureList.add(future);
        }

        System.out.println("all the future objects are returned");
        try {
            for(Future future : futureList) {
                System.out.println(future.get());
            }
        } catch (InterruptedException|ExecutionException ex) {
            ex.printStackTrace();
        }
        // shutting down threadpool
        service.shutdown();
    }
}
