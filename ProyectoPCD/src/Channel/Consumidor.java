package Channel;

import messagepassing.CommunicationScheme;
public class Consumidor implements Runnable {
    private CommunicationScheme canal;
    public Consumidor(CommunicationScheme c) {
        this.canal = c;
    }
    public void run() {
        while (true) {
            Object o = canal.receive();
            System.out.println("Recibido objeto " + o);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
