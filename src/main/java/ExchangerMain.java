import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerMain {

    static final class Buffer<T> {
        private int capacity;
        private List<T> list = new ArrayList<>();
        public Buffer(int capacity) {
            this.capacity = capacity;
        }
        public void clear() {
            this.list.clear();
        }
        public boolean isEmpty() {
            return this.list.isEmpty();
        }
        public boolean isFull() {
            return this.list.size() == capacity;
        }
        public void add(T data) {
            if(isFull())
                return;
            this.list.add(data);
        }
    }

    static final class FillingRunnable implements Runnable {
        private volatile boolean isRunning = true;
        Exchanger<Buffer> exchanger;
        Buffer<Integer> buffer;
        public FillingRunnable(Exchanger<Buffer> exchanger, Buffer<Integer> buffer) {
            this.exchanger = exchanger;
            this.buffer = buffer;
        }
        @Override
        public void run() {
            Buffer<Integer> currBuffer = buffer;
            while(isRunning) {
                for(int i = 0; i < 10; i++) {
                    currBuffer.add(i);
                }
                System.out.println("filling = "+currBuffer.list+" "+currBuffer);
                try {
                    Thread.sleep(3000);
                    if (currBuffer.isFull()) {
                        currBuffer = exchanger.exchange(buffer);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void stopRunning() {
            isRunning = false;
        }
    }

    static final class EmptyingRunnable implements Runnable {
        private volatile boolean isRunning = true;
        Exchanger<Buffer> exchanger;
        Buffer<Integer> buffer;
        public EmptyingRunnable(Exchanger<Buffer> exchanger, Buffer<Integer> buffer) {
            this.exchanger = exchanger;
            this.buffer = buffer;
        }
        @Override
        public void run() {
            Buffer<Integer> currBuffer = buffer;
            while(isRunning) {
                try {
                    Thread.sleep(3000);
                    currBuffer.clear();
                    System.out.println("emptying = "+currBuffer.list+" "+currBuffer);
                    if (currBuffer.isEmpty()) {
                        currBuffer = exchanger.exchange(buffer);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void stopRunning() {
            isRunning = false;
        }
    }

    public static void main(String[] args) {

        Exchanger<Buffer> exchanger = new Exchanger<>();

        Buffer<Integer> buffer1 = new Buffer<>(10);
        Buffer<Integer> buffer2 = new Buffer<>(10);

        FillingRunnable fillingRunnable = new FillingRunnable(exchanger, buffer1);
        EmptyingRunnable emptyingRunnable = new EmptyingRunnable(exchanger, buffer2);

        Thread t1 = new Thread(fillingRunnable);
        Thread t2 = new Thread(emptyingRunnable);
        t1.start();
        t2.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        fillingRunnable.stopRunning();
        emptyingRunnable.stopRunning();
    }
}
