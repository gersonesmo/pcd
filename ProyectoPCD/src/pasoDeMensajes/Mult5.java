package pasoDeMensajes;

import messagepassing.Channel;
import messagepassing.CommunicationScheme;

public class Mult5 extends Thread {
	private CommunicationScheme buzonSend;
	private CommunicationScheme buzonReceive;
	private int[] buffer;
	private int frenteBuffer;
	private Channel controlador, mezclador;

	public Mult5(CommunicationScheme buzonSend, CommunicationScheme buzonReceive, Channel controlador, Channel mezclador){
		this.buzonSend = buzonSend;
		this.buzonReceive = buzonReceive;
		this.buffer = new int[2000];
		this.frenteBuffer = 0;
		this.controlador = controlador;
		this.mezclador = mezclador;
	}
	
	public void run(){
		for (int i = 0; i < 10000; i++) {
			buzonSend.send("Pedir");
			int aux = (int)buzonReceive.receive();
			if (aux%5 == 0){
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
