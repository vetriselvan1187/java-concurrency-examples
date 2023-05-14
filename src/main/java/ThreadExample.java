public class ThreadExample {

    // creating runnable
    static final class NamePrinter implements Runnable {
        private final String name;
        private final int num;
        public NamePrinter(String name, int num) {
            this.name = name;
            this.num = num;
        }
        public void run() {
            for(int i = 0; i < num; i++) {
                System.out.println(name);
            }
        }
    }

    public static void main(String[] args) {

        // create a new thread with runnable
        /*Thread t = new Thread(new NamePrinter("Myname", 10));
        t.start();
        String name = "MyNameLambda";
        int num = 20;
        Thread t1 = new Thread(()-> {
            for(int i = 0; i < num; i++) {
                System.out.println(name);
            }
        });
        t1.start();
        */

        // Pause a thread
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Thread t2 = new Thread(new InterruptRunnable());
        t2.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // Interrupt a thread
        t2.interrupt();


        // Join Thread
        /*Thread t = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Thread is running");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
        System.out.println("Main thread is finished");
    }


    static final class InterruptRunnable implements Runnable {

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("interrupt runnable");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
