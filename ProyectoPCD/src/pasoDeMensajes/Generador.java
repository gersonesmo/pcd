package pasoDeMensajes;

import messagepassing.CommunicationScheme;

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
