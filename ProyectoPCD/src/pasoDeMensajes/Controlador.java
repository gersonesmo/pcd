package pasoDeMensajes;

import messagepassing.MailBox;
import messagepassing.Selector;

public class Controlador extends Thread {
    private MailBox generador, basurero, mult2S, mult2R, mult3S, mult3R,
            mult5S, mult5R;
    private Buffer[] buffer;
    private int frenteGen, frenteBas, frente2, frente3, frente5;
    private Selector s;
    private int cont2;
    private int cont3;
    private int cont5;
    private int nElemBuffer;

    public Controlador(MailBox generador, MailBox basurero, MailBox mult2S,
                       MailBox mult2R, MailBox mult3S, MailBox mult3R, MailBox mult5S,
                       MailBox mult5R) {
        this.generador = generador;
        this.basurero = basurero;
        this.mult2S = mult2S;
        this.mult2R = mult2R;
        this.mult3S = mult3S;
        this.mult3R = mult3R;
        this.mult5S = mult5S;
        this.mult5R = mult5R;

        cont2 = 0;
        cont3 = 0;
        cont5 = 0;
        nElemBuffer = 0;
        frenteGen = 0;
        frenteBas = 0;
        frente2 = 0;
        frente3 = 0;
        frente5 = 0;

        buffer = new Buffer[10];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = new Buffer();
        }

        s = new Selector();

        s.addSelectable(generador, false);
        s.addSelectable(basurero, false);
        s.addSelectable(mult2S, false);
        s.addSelectable(mult3S, false);
        s.addSelectable(mult5S, false);
    }

    public void run() {
        int elem;
        for (int i = 0; i < 50000; i++) {
            generador.setGuardValue(nElemBuffer < 10);
            basurero.setGuardValue(buffer[frenteBas].getContador() == 3);
            mult2S.setGuardValue(cont2 > 0);
            mult3S.setGuardValue(cont3 > 0);
            mult5S.setGuardValue(cont5 > 0);
            switch (s.selectOrBlock()) {
                case 1:
                    elem = (int) generador.receive();
                    buffer[frenteGen].setValor(elem);
                    frenteGen = (frenteGen+1) % 10;
                    nElemBuffer++;
                    cont2++;
                    cont3++;
                    cont5++;
                    break;
                case 2:
                    basurero.receive();
                    buffer[frenteBas].setContador(0);
                    buffer[frenteBas].setValor(0);
                    frenteBas = (frenteBas+1) % 10;
                    nElemBuffer--;
                    break;
                case 3:
                    mult2S.receive();
                    mult2R.send(buffer[frente2].getValor());
                    buffer[frente2].setContador(buffer[frente2].getContador()+1);
                    frente2 = (frente2+1) % 10;
                    cont2--;
                    break;
                case 4:
                    mult3S.receive();
                    mult3R.send(buffer[frente3].getValor());
                    buffer[frente3].setContador(buffer[frente3].getContador()+1);
                    frente3 = (frente3+1) % 10;
                    cont3--;
                    break;
                case 5:
                    mult5S.receive();
                    mult5R.send(buffer[frente5].getValor());
                    buffer[frente5].setContador(buffer[frente5].getContador()+1);
                    frente5 = (frente5+1) % 10;
                    cont5--;
                    break;
            }
        }
    }
}
