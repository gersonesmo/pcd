package semMon;

import java.util.LinkedList;

public class Multiplos2 extends Thread {
	
	private LinkedList<Integer> buffer2 = new LinkedList<>();
	private MultBufferMonitor monitor2;

	Multiplos2(MultBufferMonitor monitor) {
		monitor2 = monitor;
	}

	@Override
	public void run() {
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

		}
		try {
			monitor2.imprimirMultIn(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Integer num : buffer2) {
			System.out.print(num + " ");
		}
		System.out.println();
		monitor2.imprimirMultOut();
		for (Integer num : buffer2) {
			try {
				monitor2.insertarMult2(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
