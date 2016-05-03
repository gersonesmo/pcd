package EjercicioChannel;

import messagepassing.Channel;

import java.util.Random;

/**
 * Created by GersonEsquembri on 29/04/2016.
 */
public class Ejecutor extends Thread{
    private Channel canal;
    public Ejecutor(Channel c){
        this.canal = c;
    }
    public void run(){
        int a, b;
        char operation;
        char[] ops = {'+', '-', '*', '/'};
        Random randOp = new Random();
        for (int i = 0; i < 100; i++) {
            a = randOp.nextInt(50) + 1;
            b = randOp.nextInt(50) + 1;
            operation = ops[randOp.nextInt(ops.length)];
            Operacion op = new Operacion(a, b, operation);
            System.out.println("Operación solicitada: " + op.a + " " + op.op + " " + op.b);
            canal.send(op);
            System.out.println("Resultado operación " + i + ": " + canal.receive());
        }
    }
}
