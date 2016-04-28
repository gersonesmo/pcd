package pasoDeMensajes;

import messagepassing.CommunicationScheme;

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
