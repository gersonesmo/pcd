/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package pasoDeMensajes;

/**
 * Estructura de datos que se utiliza para crear el buffer compartido.
 * Contiene dos enteros, uno es el valor del número insertado y el otro
 * es el contador que indica la cantidad de hilos de múltiplos han pasado
 * por cada posición del buffer.
 */
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
