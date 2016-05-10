/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package semMon;

 /**
 * Imprimirá los números en el orden en el que los diferentes
 * hilos de múltiplos los vayan cogiendo.
 */
public class Mezclador extends Thread {
	static MultBufferMonitor monitorMezclador = new MultBufferMonitor();
	static int[] bufferMezclador;

	Mezclador(MultBufferMonitor monitor) {
		bufferMezclador = new int[3];
		monitorMezclador = monitor;
	}
/**
 * Método para hallar el número mínimo del
 * buffer del mezclador.
 * @return pos Posición del número más pequeño del buffer.
 */
	public int minimo() {
		int minimo = 1 << 20;
		int pos = 0;
		for (int i = 0; i < bufferMezclador.length; i++) {
			if (bufferMezclador[i] < minimo) {
				minimo = bufferMezclador[i];
				pos = i;
			}
		}
		return pos;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10333; i++) {
			try {
				monitorMezclador.entrarImprimirMezc(bufferMezclador);
				System.out.print(bufferMezclador[minimo()] + " ");
				monitorMezclador.outImprimirMezc(minimo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
