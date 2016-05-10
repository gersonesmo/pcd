/*
 * Copyright (c) 2016. Archive created by Gerson Esquembri Moreno.
 */

package pasoDeMensajes;

import messagepassing.Channel;
import messagepassing.Selector;

/**
 * Hilo encargado de gestionar la impresión de los números del mezclador,
 * es decir, todos aquellos que sean múltiplos de 2, 3 ó 5, en el
 * orden correcto.
 */

public class Mezclador extends Thread {
    private Channel mult2, mult3, mult5;
    private int[] buffer;
    private Selector select;

    public Mezclador(Channel mult2, Channel mult3, Channel mult5){
        this.mult2 = mult2;
        this.mult3 = mult3;
        this.mult5 = mult5;
        buffer = new int[3];
        select = new Selector();
        select.addSelectable(mult2, false);
        select.addSelectable(mult3, false);
        select.addSelectable(mult5, false);
    }

    public int minimo() {
        int minimo = 1 << 20;
        int pos = 0;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] < minimo) {
                minimo = buffer[i];
                pos = i;
            }
        }
        return pos;
    }
    
    public void run(){
        int mult;
        for (int i = 0; i < 10333; i++) {
            mult2.setGuardValue(buffer[0] == 0);
            mult3.setGuardValue(buffer[1] == 0);
            mult5.setGuardValue(buffer[2] == 0);
            switch (select.selectOrBlock()){
                case 1:
                    mult = (int)mult2.receive();
                    buffer[0] = mult;
                    if (buffer[1] != 0 && buffer[2] != 0){
                        System.out.print(buffer[minimo()] + " ");
                        buffer[minimo()] = 0;
                        if (buffer[0] == 10000 && buffer[2] == 10000)
                            System.out.println(buffer[0] + " " + buffer[2]);
                    }
                    break;
                case 2:
                    mult = (int)mult3.receive();
                    buffer[1] = mult;
                    if (buffer[0] != 0 && buffer[2] != 0){
                        System.out.print(buffer[minimo()] + " ");
                        buffer[minimo()] = 0;
                    }
                    break;
                case 3:
                    mult = (int)mult5.receive();
                    buffer[2] = mult;
                    if (buffer[0] != 0 && buffer[1] != 0) {
                        System.out.print(buffer[minimo()] + " ");
                        buffer[minimo()] = 0;
                        if (buffer[0] == 10000 && buffer[2] == 10000)
                            System.out.println(buffer[0] + " " + buffer[2]);
                    }
                    break;
            }

        }
    }
}
