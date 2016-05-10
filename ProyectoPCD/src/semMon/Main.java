/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package semMon;
import java.util.concurrent.Semaphore;

/**
* Clase principal, en la que se crearán los distintos semáforos,
* el monitor, y se iniciarán los hilos del programa.
*/
public class Main {

	public static volatile Buffer[] bufferCompartido = new Buffer[10];
	public static Semaphore vacios2 = new Semaphore(0);
	public static Semaphore vacios3 = new Semaphore(0);
	public static Semaphore vacios5 = new Semaphore(0);
	public static Semaphore generador = new Semaphore(10);
	public static Semaphore mutex = new Semaphore(1);
	public static Semaphore basurero = new Semaphore(0);
	public static MultBufferMonitor monitor = new MultBufferMonitor();

	public static void main(String[] args) {
		for (int i = 0; i < bufferCompartido.length; i++) {
			bufferCompartido[i] = new Buffer();
		}
		
		Generador generador = new Generador();
		Thread mult2 = new Multiplos2(monitor);
		Thread mult3 = new Multiplos3(monitor);
		Thread mult5 = new Multiplos5(monitor);
		Thread mezclador = new Mezclador(monitor);
		RecolectorBasura recolector = new RecolectorBasura();
		
		generador.start();
		mult2.start();
		mult3.start();
		mult5.start();
		mezclador.start();
		recolector.start();
		
		try {
			generador.join();
			mult2.join();
			mult3.join();
			mult5.join();
			mezclador.join();
			recolector.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
