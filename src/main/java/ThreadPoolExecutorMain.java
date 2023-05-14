import java.util.concurrent.*;

public class ThreadPoolExecutorMain {


    static final class ExtendedThreadPoolExecutor extends ThreadPoolExecutor {

        public ExtendedThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepAliveTime,
                                          TimeUnit unit, BlockingQueue queue, RejectedExecutionHandler handler) {
            super(corePoolSize, maxPoolSize, keepAliveTime, unit, queue, handler);
        }

        public void beforeExecute(Thread t, Runnable r) {
            System.out.println("before running the task");
        }

        public void afterExecute(Runnable r, Throwable t) {
            System.out.println("after running the task");
        }

    }

    public static void main(String[] args) {

        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("rejected tasks");
                r.run();
            }
        };

        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 15, TimeUnit.SECONDS,
          //      new ArrayBlockingQueue<>(30), rejectedExecutionHandler);

        ThreadPoolExecutor threadPoolExecutor = new ExtendedThreadPoolExecutor(10, 20, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue(30), rejectedExecutionHandler);

        Runnable r = () -> {
          for(int i = 0; i < 2; i ++) {
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException ex) {
                  ex.printStackTrace();
              }
              System.out.println("Task is running");
          }
        };
        for(int i = 0; i < 20; i++) {
            threadPoolExecutor.submit(r);
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("shutting down");
        threadPoolExecutor.shutdown();
        threadPoolExecutor.submit(() -> System.out.println("Last Task"));
    }
}
