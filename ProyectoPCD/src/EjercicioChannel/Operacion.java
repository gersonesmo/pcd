package EjercicioChannel;

import java.io.Serializable;

/**
 * Created by GersonEsquembri on 29/04/2016.
 */
public class Operacion implements Serializable{
    public int a, b;
    public char op;

    public Operacion(int a, int b, char op){
        this.a = a;
        this.b = b;
        this.op = op;
    }
}
