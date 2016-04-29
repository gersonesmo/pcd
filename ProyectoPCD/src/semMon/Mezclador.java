package semMon;

public class Mezclador extends Thread {
	static MultBufferMonitor monitorMezclador = new MultBufferMonitor();
	static int[] bufferMezclador;

	Mezclador(MultBufferMonitor monitor) {
		bufferMezclador = new int[3];
		monitorMezclador = monitor;
	}

	public int minimo() {
		int minimo = 1 << 20;
		int pos = 0;
		for (int i = 0; i < bufferMezclador.length; i++) {
			if (bufferMezclador[i] < minimo) {
				minimo = bufferMezclador[i];
				pos = i;
			}
		}
		return pos;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10333; i++) {
			try {
				monitorMezclador.entrarImprimir(bufferMezclador);
				System.out.print(bufferMezclador[minimo()] + " ");
				monitorMezclador.outImprimir(minimo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
