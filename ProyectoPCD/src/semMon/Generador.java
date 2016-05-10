package semMon;

/**
* Estructura de datos que contiene dos enteros,
* el número insertado por el generador y un
* contador para controlar que todos los hilos
* de múltiplos hayan leído una posición.
*/
class Buffer {
 int num;
 int cont;
	 Buffer(){
		 num = 0;
		 cont = 0;
	 }
 }
/**
* Genera los números del 1 al 10000 y los va insertando
* en el buffer compartido.
*/
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			frenteCompartido = (frenteCompartido + 1) % 10;
			Main.mutex.release();
			Main.vacios2.release();
			Main.vacios3.release();
			Main.vacios5.release();
	
		}
	}
}
