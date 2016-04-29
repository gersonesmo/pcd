package semMon;

import java.util.LinkedList;


public class Multiplos5 extends Thread {
	private LinkedList<Integer> buffer5 = new LinkedList<Integer>();
	private MultBufferMonitor monitor5;


	Multiplos5(MultBufferMonitor monitor) {
		this.monitor5 = monitor;
	}
	
	@Override
	public void run(){
		int index = 0;
		for (int i = 0; i < 10000; i++) {
			try {
				Main.vacios5.acquire();
				Main.mutex.acquire();
				Main.bufferCompartido[i%10].cont++;
				if (Main.bufferCompartido[i%10].num % 5 == 0){
					buffer5.add(Main.bufferCompartido[i%10].num);
				}
				if (Main.bufferCompartido[i%10].cont == 3){
					Main.basurero.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Main.mutex.release();
		}
		try {
			monitor5.imprimirMultIn(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Integer num : buffer5) {
			System.out.print(num + " ");
		}
		System.out.println();
		monitor5.imprimirMultOut();
		for (Integer num : buffer5) {
			try {
				monitor5.insertarMult5(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} 
	
}
