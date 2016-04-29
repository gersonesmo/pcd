package EjercicioChannel;

import messagepassing.*;

/**
 * Created by GersonEsquembri on 29/04/2016.
 */


public class Servidor extends Thread{
    private Channel canal;
    private Selector s;

    public Servidor(Channel a){
        this.canal = a;
        s = new Selector();
        s.addSelectable(canal, false);
    }

    public void run(){
        int resultado = 0;
        for (int i = 0; i < 100; i++) {
            switch (s.selectOrBlock()) {
                case 1:
                    Operacion op = (Operacion) canal.receive();
                    switch (op.op) {
                        case '+':
                            resultado = op.a + op.b;
                            break;
                        case '-':
                            resultado = op.a - op.b;
                            break;
                        case '*':
                            resultado = op.a * op.b;
                            break;
                        case '/':
                            resultado = op.a / op.b;
                            break;

                    }
                    canal.send(resultado);
                    break;
            }
        }
    }
}
