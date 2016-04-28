package ejercicio1;

import messagepassing.MailBox;

public class Main {
	public static void main(String[] args) {
		MailBox bHola = new MailBox();
		MailBox bAmigos = new MailBox();
		MailBox bDel = new MailBox();
		MailBox bMundo = new MailBox();
		
		bHola.send("token"); // String implementa Serializable
		Thread hola = new Thread(new Hilo(bHola, bAmigos, "Hola "));
		Thread amigos = new Thread(new Hilo(bAmigos, bDel, "amigos "));
		Thread del = new Thread(new Hilo(bDel, bMundo, "del "));
		Thread mundo = new Thread(new Hilo(bMundo, bHola, "mundo\n"));
		hola.start();
		amigos.start();
		del.start();
		mundo.start();
		try {
			hola.join();
			amigos.join();
			del.join();
			mundo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}