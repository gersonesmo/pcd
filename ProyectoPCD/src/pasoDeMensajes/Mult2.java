/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package pasoDeMensajes;

import messagepassing.Channel;
import messagepassing.CommunicationScheme;

/**
 * Hilo encargado de coger aquellos números del buffer compartido
 * que sean múltiplos de 2, para luego imprimirlos. También los
 * mandará al hilo mezclador, para que este los imprima luego.
 */
public class Mult2 extends Thread {
	private CommunicationScheme buzonSend;
	private CommunicationScheme buzonReceive;
	private int[] buffer;
	private int frenteBuffer;
	private Channel controlador, mezclador;
	public Mult2(CommunicationScheme buzonSend, CommunicationScheme buzonReceive, Channel controlador, Channel mezclador){
		this.buzonSend = buzonSend;
		this.buzonReceive = buzonReceive;
		this.buffer = new int[5000];
		this.frenteBuffer = 0;
		this.controlador = controlador;
		this.mezclador = mezclador;
	}
	
	public void run(){
		for (int i = 0; i < 10000; i++) {
			buzonSend.send("Pedir");
			int aux = (int)buzonReceive.receive();
			if (aux%2 == 0){
				buffer[frenteBuffer] = aux;
				frenteBuffer++;
			}
		}
		controlador.send("Quiero imprimir");
		controlador.receive();
		for (int aBuffer : buffer) {
			System.out.print(aBuffer + " ");
		}
		System.out.println();
		controlador.send("He acabado");

		for (int aBuffer : buffer) {
			mezclador.send(aBuffer);
		}
	}
}
