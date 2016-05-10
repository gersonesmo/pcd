/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package pasoDeMensajes;

import messagepassing.CommunicationScheme;

/**
 * Hilo encargado de generar los números del 1 al 10000, y enviarlos
 * mediante el buzón del generador. El hilo controlador se encargará de
 * gestionar dichos números.
 */
public class Generador extends Thread {
	private CommunicationScheme buzon;
	public Generador(CommunicationScheme buzon){
		this.buzon = buzon;
	}
	public void run(){
		for (int i = 0; i < 10000; i++) {
			buzon.send(i+1);
		}
	}
}
