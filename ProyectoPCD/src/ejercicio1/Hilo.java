package ejercicio1;

import messagepassing.CommunicationScheme;

public class Hilo implements Runnable {
	private CommunicationScheme cs;
	private CommunicationScheme sig;
	private String mensaje;

	public Hilo(CommunicationScheme cs, CommunicationScheme sig, String mensaje) {
		this.cs = cs;
		this.sig = sig;
		this.mensaje = mensaje;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Object token = cs.receive();
			System.out.print(mensaje);
			sig.send(token);
		}
	}
}
