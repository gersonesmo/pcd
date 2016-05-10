/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */


package semMon;

/**
 * Limpiará aquellas posiciones del buffer que hayan sido
 * leídas por todos los hilos de múltiplos, poniendo
 * el número y el contador a 0.
 */
public class RecolectorBasura extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			try {
				Main.basurero.acquire();
				Main.mutex.acquire();
				Main.bufferCompartido[i % 10].num = 0;
				Main.bufferCompartido[i % 10].cont = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.mutex.release();
			Main.generador.release();
		}
	}
}
