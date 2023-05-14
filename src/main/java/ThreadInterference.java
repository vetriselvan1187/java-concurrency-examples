/**
 *
 */
public class ThreadInterference {

    static final class Counter {
        int num = 0;
        public synchronized void increment() {
            this.num++;
        }
    }

    public static void main(String[] args) {

        Counter counter = new Counter();
        Thread t1 = new Thread(() -> {
           for(int i = 0; i < 100000; i++) {
               counter.increment();
           }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(counter.num);
    }
}
