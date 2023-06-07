import java.util.ArrayList;
import java.util.List;

public class AtomicityMain {

    static final class Counter {
        volatile int num = 0;
        Object lock = new Object();

        static int val = 0;

        public void increment() {
            synchronized (lock) { // lock represents external lock
                this.num++;
            }
        }

        public static synchronized void incVal() { // method belongs to class
            val++;
        }
    }

    public static void main(String[] args) {

        final Counter counter = new Counter();

        final List<Thread> writeThreadList = new ArrayList<>();
        final List<Thread> readThreadList = new ArrayList<>();

        Thread t1 = new Thread(() -> {
           for(int i = 0; i < 3; i++) {
               Thread t = new Thread(() -> {
                   for(int j = 0; j < 10; j++) {
                       counter.increment();
                       //Counter.incVal();
                       try {
                           Thread.sleep(1000);
                       } catch (InterruptedException ex) {
                           ex.printStackTrace();
                       }
                   }
               });
               t.start();
               writeThreadList.add(t);
           }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 10; i++) {

                Thread t = new Thread(() -> {
                    for(int j = 0; j < 10; j++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("instance count = "+counter.num);
                        //System.out.println("static count = "+Counter.val);
                    }
                });
                t.start();
                readThreadList.add(t);
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
            System.out.println("join ended");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        for(Thread t : writeThreadList) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        for(Thread t : readThreadList) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("counter num = "+counter.num);
        System.out.println("join for all ended");

    }
}
