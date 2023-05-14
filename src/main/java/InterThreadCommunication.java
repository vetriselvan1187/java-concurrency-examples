public class InterThreadCommunication {

    // spurious wakeup

    static final class Looper implements Runnable {
        private volatile boolean flag = false;
        @Override
        public void run() {
            while(!flag) {
                try {
                    waitHere();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public synchronized void waitHere() throws InterruptedException {
            System.out.println("Doing Some Processing");
            System.out.println("Waiting");
            wait();
        }

        public synchronized void notifyHere() {
            System.out.println("Notified");
            notifyAll();
        }

        public synchronized void notifyHereWithFlag() {
            flag = true;
            notifyAll();
        }
    }

    public static void main(String[] args) {
        final Looper looper = new Looper();
        Thread looperThread = new Thread(looper);
        looperThread.start();

        Thread notifyThread = new Thread(() -> {
           for(int i = 0; i < 10; i++) {
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
                looper.notifyHere();
           }
        });
        notifyThread.start();
        try {
            notifyThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        looper.notifyHereWithFlag();
    }
}
