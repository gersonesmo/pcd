/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package semMon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Controlador de toda la concurrencia que hay entre
 * los hilos de múltiplos y el mezclador.
 */

public class MultBufferMonitor {
	private ReentrantLock l;
	private Condition mezclador;
	private Condition multiplo2;
	private Condition multiplo3;
	private Condition multiplo5;
	private Condition imprimir2, imprimir3, imprimir5;
	private int[] bufferMezclador;
	private boolean printing;
	private boolean end;

	MultBufferMonitor() {
		l = new ReentrantLock();
		mezclador = l.newCondition();
		multiplo2 = l.newCondition();
		multiplo3 = l.newCondition();
		multiplo5 = l.newCondition();
		imprimir2 = l.newCondition();
		imprimir3 = l.newCondition();
		imprimir5 = l.newCondition();
		bufferMezclador = new int[3];
		printing = false;
		end = false;
	}

	/**
	 * Inserta un número en la segunda posición
	 * del buffer del mezclador.
	 * @param mult Número a insertar en el buffer.
	 * @throws InterruptedException
	 */
	public void insertarMult2(int mult) throws InterruptedException {
		l.lock();
		try {
			while (bufferMezclador[0] != 0)
				multiplo2.await();
			bufferMezclador[0] = mult;
			if (bufferMezclador[1] != 0 && bufferMezclador[2] != 0)
				mezclador.signal();
		} finally {
			l.unlock();
		}

	}

	/**
	 * Inserta un número en la segunda posición
	 * del buffer del mezclador.
	 * @param mult Número a insertar en el buffer.
	 * @throws InterruptedException
     */
	public void insertarMult3(int mult) throws InterruptedException {
		l.lock();
		try {
			while (bufferMezclador[1] != 0)
				multiplo3.await();
			bufferMezclador[1] = mult;
			if (bufferMezclador[0] != 0 && bufferMezclador[2] != 0)
				mezclador.signal();
		} finally {
			l.unlock();
		}

	}
	/**
	 * Inserta un número en la tercera posición
	 * del buffer del mezclador.
	 * @param mult Número a insertar en el buffer.
	 * @throws InterruptedException
	 */
	public void insertarMult5(int mult) throws InterruptedException {
		l.lock();
		try {
			while (bufferMezclador[2] != 0)
				multiplo5.await();
			bufferMezclador[2] = mult;
			if (bufferMezclador[0] != 0 && bufferMezclador[1] != 0)
				mezclador.signal();
		} finally {
			l.unlock();
		}

	}

	/**
	 * Protocolo de entrada para el mezclador.
	 * Esperará mientras no haya números distintos de 0 en cada
	 * una de las posiciones del buffer del mezclador.
	 * @param buffer Buffer que se copiará en el buffer del mezclador.
	 * @throws InterruptedException
     */
	public void entrarImprimirMezc(int[] buffer)
			throws InterruptedException {
		l.lock();
		try {
			if (!end) {
				while (bufferMezclador[0] == 0 || bufferMezclador[1] == 0
						|| bufferMezclador[2] == 0)
					mezclador.await();
				System.arraycopy(bufferMezclador, 0, buffer, 0, buffer.length);
			}
			else {
				bufferMezclador[1] = 1 << 25;
				if (bufferMezclador[0] == 0)
					bufferMezclador[0] = 1 << 25;
				System.arraycopy(bufferMezclador, 0, buffer, 0, buffer.length);
			}
		} finally {
			l.unlock();
		}
	}

	/**
	 * Protocolo de salida del mezclador, dará paso a
	 * aquel múltiplo cuya posición del buffer esté vacía.
	 * @param pos Posición que se pondrá a 0, para posteriormente
	 *            liberar el hilo que llamó al método.
     */
	public void outImprimirMezc(int pos) {
		l.lock();
		bufferMezclador[pos] = 0;
		if (bufferMezclador[0] == 0)
			multiplo2.signal();
		else if (bufferMezclador[1] == 0)
			multiplo3.signal();
		else
			multiplo5.signal();
		if (bufferMezclador[0] == 10000 && bufferMezclador[2] == 10000)
			end = true;

		l.unlock();
	}

	/**
	 * Protocolo de entrada de la impresión de los hilos
	 * de los múltiplos. Si hay alguien imprimiendo el hilo
	 * que quiere imprimir se esperará hasta que no haya nadie
	 * imprimiendo.
	 * @param mult Múltiplo que ha pedido tener acceso a la impresión.
	 * @throws InterruptedException
     */
	public void imprimirMultIn(int mult) throws InterruptedException{
		l.lock();
		if (mult==2){
			try {
				while (printing)
					imprimir2.await();
				printing = true;
			}finally {
				l.unlock();
			}
		}if (mult==3){
			try {
				while (printing)
					imprimir3.await();
				printing = true;
			}finally {
				l.unlock();
			}
		}if (mult==5){
			try {
				while (printing)
					imprimir5.await();
				printing = true;
			}finally {
				l.unlock();
			}
		}
	}

	/**
	 * Protocolo de salida de la impresión de los hilos de los
	 * múltiplos. Si en la cola de condición de alguno de ellos
	 * hay alguien esperando, se le dará paso a dicho hilo.
	 */
	public void imprimirMultOut(){
		l.lock();
		printing = false;
		if (l.hasWaiters(imprimir2))
			imprimir2.signal();
		else if (l.hasWaiters(imprimir3))
			imprimir3.signal();
		else if (l.hasWaiters(imprimir5))
			imprimir5.signal();
		l.unlock();
	}
}
