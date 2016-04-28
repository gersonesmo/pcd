package semMon;

public class RecolectorBasura extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			try {
				Main.basurero.acquire();
				Main.mutex.acquire();
				Main.bufferCompartido[i % 10].num = 0;
				Main.bufferCompartido[i % 10].cont = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.mutex.release();
			Main.generador.release();
		}
	}
}
