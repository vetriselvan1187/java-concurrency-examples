import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProducerConsumerMain {

    static final class BoundedQueue<T> {
        private int capacity;
        LinkedList<T> list;
        //List<T> list;

        public BoundedQueue(int capacity) {
            this.capacity = capacity;
            //this.list = Collections.synchronizedList(new LinkedList<T>());
            this.list = new LinkedList<>();
        }
        public synchronized void add(T message) {
            while (list.size() == capacity) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            synchronized (list) {
                list.addLast(message);
            }
            notifyAll();
        }
        public synchronized T remove() throws NoSuchElementException  {
            while (list.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            T message = null;
            synchronized (list) {
                 message = list.removeFirst();
            }
            notifyAll();
            return message;
        }
        @Override
        public String toString() {
            return "BoundedQueue{" +
                    "list=" + list +
                    '}';
        }
    }

    static final class Producer implements Runnable {
        private volatile boolean flag = false;
        BoundedQueue<String> boundedQueue;
        public Producer(BoundedQueue<String> boundedQueue) {
            this.boundedQueue = boundedQueue;
        }
        @Override
        public void run() {
            int i = 0;
            while(!flag) {
                boundedQueue.add(i+" th message");
                i++;
            }
        }
        public void setFlag() {
            flag = true;
        }
    }

    static final class Consumer implements Runnable {
        private volatile boolean flag = false;
        BoundedQueue<String> boundedQueue;
        public Consumer(BoundedQueue<String> boundedQueue) {
            this.boundedQueue = boundedQueue;
        }
        @Override
        public void run() {
            while(!flag) {
                System.out.println(Thread.currentThread().getId()+" "+boundedQueue.remove());
            }
        }
        public void setFlag() {
            flag = true;
        }
    }

    public static void main(String[] args) {
        final BoundedQueue<String> boundedQueue = new BoundedQueue<>(50);
        Producer producer = new Producer(boundedQueue);
        Consumer consumer = new Consumer(boundedQueue);

        Thread p1 = new Thread(producer);
        Thread c1 = new Thread(consumer);
        Thread c2 = new Thread(consumer);

        p1.start();
        c1.start();
        c2.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        producer.setFlag();
        consumer.setFlag();
        System.out.println(boundedQueue);
    }
}
