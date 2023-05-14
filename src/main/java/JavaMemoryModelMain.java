public class JavaMemoryModelMain {

    static int x = 0, y = 0, a = 0, b = 0;

    // singleton
    static final class Singleton {
        private static Singleton singleton = null;

        private Singleton() {
        }

        public static Singleton getInstance() {
            if(singleton == null) {
                singleton = new Singleton();
            }
            return singleton;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /*for(int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
               Singleton singleton = Singleton.getInstance();
               System.out.println(singleton);
            });
            t.start();
        }
        System.out.println();
        Thread.sleep(1000);
        System.out.println("singleton = "+Singleton.getInstance());
        */


        Thread t1 = new Thread(() -> {
            //x = b;
            a = 1;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            x = b;
        });
        Thread t2 = new Thread(() -> {
           //y = a;
             b = 2;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            y = a;
           //b = 2;
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("x = "+x+" y = "+y+" a = "+a+" b = "+b);
    }
}
// x = 0, y = 0
// x = 0, y = 1
// x = 2, y = 0
// x = 2, y = 1
