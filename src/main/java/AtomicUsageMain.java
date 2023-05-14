import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicUsageMain {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        int[] array = new int[] {1, 2, 3, 4, 5};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array);

        AtomicReference<Integer> atomicReference = new AtomicReference<>();
        atomicReference.set(10);


        System.out.println(atomicIntegerArray.incrementAndGet(0));
        Thread t1 = new Thread(() -> {
           for(int i = 0; i < 1000; i++) {
               atomicInteger.incrementAndGet();
           }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) {
                atomicInteger.incrementAndGet();
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
        System.out.println(atomicInteger.get());
    }
}
