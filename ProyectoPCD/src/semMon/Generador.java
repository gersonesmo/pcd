package semMon;

import semMon.Main;

class Buffer {
 int num;
 int cont;
	 Buffer(){
		 num = 0;
		 cont = 0;
	 }
 }

public class Generador extends Thread {

	static int frenteCompartido;

	Generador() {
		frenteCompartido = 0;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10000; i++) {
			try {
				Main.generador.acquire();
				Main.mutex.acquire();
				Main.bufferCompartido[frenteCompartido].num = i;
				Main.bufferCompartido[frenteCompartido].cont = 0;
			} catch (InterruptedException e) {
			}

			frenteCompartido = (frenteCompartido + 1) % 10;
			Main.mutex.release();
			Main.vacios2.release();
			Main.vacios3.release();
			Main.vacios5.release();
	
		}
	}
}
