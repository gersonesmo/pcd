package pasoDeMensajes;

import messagepassing.Channel;
import messagepassing.Selector;

/**
 * Created by GersonEsquembri on 29/04/2016.
 */
public class ControladorImpresion extends Thread {
    private Channel mult2, mult3, mult5;
    private boolean imprimiendo;
    private Selector select;

    public ControladorImpresion(Channel mult2, Channel mult3, Channel mult5){
        this.mult2 = mult2;
        this.mult3 = mult3;
        this.mult5 = mult5;
        this.imprimiendo = false;
        this.select = new Selector();
        select.addSelectable(mult2, false);
        select.addSelectable(mult3, false);
        select.addSelectable(mult5, false);
    }

    public void run(){
        for (int i = 0; i < 3; i++) {
            mult2.setGuardValue(!imprimiendo);
            mult3.setGuardValue(!imprimiendo);
            mult5.setGuardValue(!imprimiendo);
            switch (select.selectOrBlock()){
                case 1:
                    mult2.receive();
                    mult2.send("Imprime");
                    imprimiendo = true;
                    mult2.receive();
                    imprimiendo = false;
                    break;
                case 2:
                    mult3.receive();
                    mult3.send("Imprime");
                    imprimiendo = true;
                    mult3.receive();
                    imprimiendo = false;
                    break;
                case 3:
                    mult5.receive();
                    mult5.send("Imprime");
                    imprimiendo = true;
                    mult5.receive();
                    imprimiendo = false;
                    break;
            }
        }
    }
}
