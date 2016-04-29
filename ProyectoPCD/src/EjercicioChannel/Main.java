package EjercicioChannel;

import messagepassing.Channel;

/**
 * Created by GersonEsquembri on 29/04/2016.
 */
public class Main {
    public static void main(String[] args) {
        Channel canal = new Channel();
        Thread e = new Ejecutor(canal);
        Thread s = new Servidor(canal);

        e.start();
        s.start();
        try {
            e.join();
            s.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
