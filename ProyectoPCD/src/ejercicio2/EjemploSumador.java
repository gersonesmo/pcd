package ejercicio2;

//fichero EjemploSumador.java
import messagepassing.*;

class Hilo extends Thread {
	private MailBox buzon;

	public Hilo(MailBox buzon) {
		this.buzon = buzon;
	}

	public void run() {
		for (int i = 0; i < 50; i++)
			buzon.send("token");
	}
}

class Controlador extends Thread {
	private int total = 0;
	private MailBox bSumar, bRestar;
	private Selector s;

	public Controlador(MailBox bSumar, MailBox bRestar) {
		this.bSumar = bSumar;
		this.bRestar = bRestar;
		s = new Selector();
		s.addSelectable(bSumar, false);
		s.addSelectable(bRestar, false);
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			bSumar.setGuardValue(true);
			bRestar.setGuardValue(total > 0);
			switch (s.selectOrBlock()) {
			case 1:
				bSumar.receive();
				total++;
				break;
			case 2:
				bRestar.receive();
				total--;
				break;
			}
			System.out.println("Valor de la variable " + total);
		}
	}
}

public class EjemploSumador {
	public static void main(String[] args) {
		MailBox psumar = new MailBox();
		MailBox prestar = new MailBox();
		Thread h1 = new Hilo(psumar);
		Thread h2 = new Hilo(prestar);
		Thread control = new Controlador(psumar, prestar);
		h1.start();
		h2.start();
		control.start();
		try {
			h1.join();
			h2.join();
			control.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}