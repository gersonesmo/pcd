/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package pasoDeMensajes;

import messagepassing.Channel;
import messagepassing.MailBox;

/**
 * Clase principal, en la que se crearán todos los buzones, canales
 * e hilos, y en la que estos últimos serán iniciados.
 */

public class Principal {
    public static void main(String[] args){
        MailBox generador = new MailBox();
        MailBox basurero = new MailBox();
        MailBox mult2S = new MailBox();
        MailBox mult2R = new MailBox();
        MailBox mult3S = new MailBox();
        MailBox mult3R = new MailBox();
        MailBox mult5S = new MailBox();
        MailBox mult5R = new MailBox();
        Channel controlador2 = new Channel();
        Channel mezclador2 = new Channel();
        Channel controlador3 = new Channel();
        Channel mezclador3 = new Channel();
        Channel controlador5 = new Channel();
        Channel mezclador5 = new Channel();
        Thread hiloGenerador = new Generador(generador);
        Thread hiloBasurero = new Generador(basurero);
        Thread control = new Controlador(generador, basurero, mult2S, mult2R,
                mult3S, mult3R, mult5S, mult5R);
        Thread controladorImpresion = new ControladorImpresion(controlador2,
                controlador3, controlador5);
        Thread mezclador = new Mezclador(mezclador2, mezclador3, mezclador5);
        Thread mult2 = new Mult2(mult2S, mult2R, controlador2, mezclador2);
        Thread mult3 = new Mult3(mult3S, mult3R, controlador3, mezclador3);
        Thread mult5 = new Mult5(mult5S, mult5R, controlador5, mezclador5);

        hiloGenerador.start();
        hiloBasurero.start();
        control.start();
        controladorImpresion.start();
        mezclador.start();
        mult2.start();
        mult3.start();
        mult5.start();

        try {
            hiloGenerador.join();
            hiloBasurero.join();
            control.join();
            controladorImpresion.join();
            mezclador.join();
            mult2.join();
            mult3.join();
            mult5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
