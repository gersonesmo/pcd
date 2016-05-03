package Channel;

import java.util.ArrayList;
import java.util.List;
import messagepassing.*;
public class ProductorConsumidorBuffer {
    public static void main(String[] args) {
        Channel pb = new Channel();
        Channel bc = new Channel();
        Buffer b = new Buffer(pb, bc, 10);
        Productor p = new Productor(pb);
        Consumidor c = new Consumidor(bc);
        Thread h1 = new Thread(p);
        Thread h2 = new Thread(c);
        Thread h3 = new Thread(b);
        h1.start(); h2.start(); h3.start();
        try {
            h1.join(); h2.join(); h3.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
class Buffer implements Runnable {
    private Channel pb;
    private Channel bc;
    private int capacidad = 0;
    private List<Object> buffer = null;
    public Buffer(Channel pb, Channel bc, int capacidad) {
        this.pb = pb;
        this.bc = bc;
        buffer = new ArrayList<>();
        this.capacidad = capacidad;
    }
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
        Selector s = new Selector();
        s.addSelectable(pb,false);
        s.addSelectable(bc,true); // El channel bc se usará para enviar
        if (capacidad == 0) {
            while (true) {
                Object o = pb.receive();
                bc.send(o);
            }
        } else {
            while (true) {
                pb.setGuardValue(buffer.size() < capacidad);
                bc.setGuardValue(buffer.size() > 0);
                switch (s.selectOrBlock()) {
                    case 1:
                        Object o = pb.receive();
                        buffer.add(o);
                        break;
                    case 2:
                        Object o2 = buffer.remove(0);
                        bc.send(o2);
                        break;
                }
            }
        }
    }
}