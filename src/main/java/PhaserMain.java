import java.util.concurrent.Phaser;

public class PhaserMain {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(3) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                //return super.onAdvance(phase, registeredParties);
                return phase >= 10 || registeredParties == 0;
            }
        };
        phaser.register();
        System.out.println(phaser.getPhase());
        Runnable r = () -> {
            do {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                int phaseNumber = phaser.arriveAndAwaitAdvance();
                if(phaseNumber == 2) {
                    phaser.arriveAndDeregister();
                }
                System.out.println("All the tasks are arrived");
            } while(!phaser.isTerminated());
        };
        for(int i = 0; i < 3; i++) {
            Thread t  = new Thread(r);
            t.start();
        }
        phaser.arriveAndDeregister();
    }
}
