package pasoDeMensajes;

public class Buffer {
	private int valor;
	private int contador;
	
	public Buffer(){
		this.valor = 0;
		this.contador = 0;
	}
	
	public int getContador() {
		return contador;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
}
