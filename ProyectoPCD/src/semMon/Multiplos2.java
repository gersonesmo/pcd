package semMon;

import java.util.LinkedList;

public class Multiplos2 extends Thread {
	
	private LinkedList<Integer> buffer2 = new LinkedList<Integer>();
	private MultBufferMonitor monitor2;

	Multiplos2(MultBufferMonitor monitor) {
		monitor2 = monitor;
	}

	@Override
	public void run() {
		int index = 0;
		for (int i = 0; i < 10000; i++) {
			try {
				Main.vacios2.acquire();
				Main.mutex.acquire();
				Main.bufferCompartido[i % 10].cont++;
				if (Main.bufferCompartido[i % 10].num % 2 == 0) {
					buffer2.add(Main.bufferCompartido[i % 10].num);
				}
				if (Main.bufferCompartido[i % 10].cont == 3) {
					Main.basurero.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.mutex.release();
			if (index < buffer2.size()) {
				try {
					monitor2.insertarMult2(buffer2.get(index));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				index++;
			}
		}

	}

}
