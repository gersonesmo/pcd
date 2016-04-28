package pasoDeMensajes;

import messagepassing.CommunicationScheme;

public class Mult3 extends Thread {
	private CommunicationScheme buzonSend;
	private CommunicationScheme buzonReceive;
	private int[] buffer;
	private int frenteBuffer;
	
	public Mult3(CommunicationScheme buzonSend, CommunicationScheme buzonReceive){
		this.buzonSend = buzonSend;
		this.buzonReceive = buzonReceive;
		this.buffer = new int[5000];
		this.frenteBuffer = 0;
	}
	
	public void run(){
		for (int i = 0; i < 10000; i++) {
			buzonSend.send("Pedir");
			int aux = (int)buzonReceive.receive();
			if (aux%3 == 0){
				buffer[frenteBuffer] = aux;
				frenteBuffer++;
			}
		}
	}
}
