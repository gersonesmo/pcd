package semMon;

import java.util.LinkedList;

public class Multiplos3 extends Thread {

	private LinkedList<Integer> buffer3 = new LinkedList<Integer>();
	private MultBufferMonitor monitor3;

	Multiplos3(MultBufferMonitor monitor) {
		this.monitor3 = monitor;
	}

	@Override
	public void run() {
		int index = 0;
		for (int i = 0; i < 10000; i++) {
			try {
				Main.vacios3.acquire();
				Main.mutex.acquire();

				Main.bufferCompartido[i % 10].cont++;
				if (Main.bufferCompartido[i % 10].num % 3 == 0) {
					buffer3.add(Main.bufferCompartido[i % 10].num);
				}
				if (Main.bufferCompartido[i % 10].cont == 3) {
					Main.basurero.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.mutex.release();
			if (index < buffer3.size()) {
				try {
					monitor3.insertarMult3(buffer3.get(index));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				index++;
			}
		}
	}

}
