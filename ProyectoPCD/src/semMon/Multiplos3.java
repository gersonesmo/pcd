/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */



package semMon;

import java.util.LinkedList;

/**
 * Hilo que comprobará si el número insertado en el buffer compartido
 * es múltiplo de 3. De ser así, lo guardará en un buffer propio,
 * y luego este buffer será impreso en la consola.
 */
public class Multiplos3 extends Thread {

	private LinkedList<Integer> buffer3 = new LinkedList<>();
	private MultBufferMonitor monitor3;

	Multiplos3(MultBufferMonitor monitor) {
		this.monitor3 = monitor;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			try {
				Main.vacios3.acquire();
				Main.mutex.acquire();

				Main.bufferCompartido[i % 10].cont++;
				if (Main.bufferCompartido[i % 10].num % 3 == 0) {
					buffer3.add(Main.bufferCompartido[i % 10].num);
				}
				if (Main.bufferCompartido[i % 10].cont == 3) {
					Main.basurero.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.mutex.release();

		}
		try {
			monitor3.imprimirMultIn(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Integer num : buffer3) {
			System.out.print(num + " ");
		}
		System.out.println();
		monitor3.imprimirMultOut();
		for (Integer num : buffer3) {
			try {
				monitor3.insertarMult3(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
