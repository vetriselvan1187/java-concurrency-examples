package problems;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

// Output : 010203040506070809..N
public class PrintZeroOddEven {

    static final class ZeroOddEven {
        private final Integer n;

        private final Semaphore zero = new Semaphore(1);
        private final Semaphore odd = new Semaphore(0);
        private final Semaphore even = new Semaphore(0);

        public ZeroOddEven(Integer n) {
            this.n = n;
        }

        public void printZero(IntConsumer consumer) throws InterruptedException {
            for(int i = 0; i < n; i++) {
                zero.acquire();
                consumer.accept(0);
                if(i%2 == 0) {
                    odd.release();
                } else {
                    even.release();
                }
            }
        }

        public void printOdd(IntConsumer consumer) throws InterruptedException {
            for(int i = 1; i <= n; i = i+2) {
                odd.acquire();
                consumer.accept(i);
                zero.release();
            }
        }

        public void printEven(IntConsumer consumer) throws InterruptedException {
            for(int i = 2; i <= n; i = i+2) {
                even.acquire();
                consumer.accept(i);
                zero.release();
            }
        }
    }

    public static void main(String[] args) {
        final int n = 100;
        final ZeroOddEven zeroOddEven = new ZeroOddEven(n);
        IntConsumer consumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        };

        Thread t1 = new Thread(() -> {
            try {
                zeroOddEven.printZero(consumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                zeroOddEven.printOdd(consumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                zeroOddEven.printEven(consumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
