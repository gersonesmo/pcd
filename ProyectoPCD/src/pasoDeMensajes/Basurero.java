

/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package pasoDeMensajes;

import messagepassing.CommunicationScheme;

/**
 * Hilo encargado de mandar la señal para eliminar los
 * elementos sobrantes del buffer.
 */
public class Basurero extends Thread {
	private CommunicationScheme buzon;

	public Basurero(CommunicationScheme buzon) {
		this.buzon = buzon;
	}

	public void run() {
		for (int i = 0; i < 10000; i++) {
			buzon.send("Eliminar");
		}
	}
}
