import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Estacionamiento {

    private static final int capacidadMaxima = 5;
    private final Semaphore semaforo = new Semaphore(capacidadMaxima, true);
    private final ArrayList<Coche> cochesAparcados = new ArrayList<>();

    public boolean entrar(Coche coche) {
        try {
            if (coche.esVip()) {
                semaforo.acquire();
                synchronized (cochesAparcados) {
                    if (cochesAparcados.size() >= capacidadMaxima) {
                        desalojarCocheNormal(coche);
                    }
                    cochesAparcados.add(coche);
                    System.out.println(coche + " ha entrado.");
                }
                return true;
            } else {
                if (semaforo.tryAcquire(5, TimeUnit.SECONDS)) {
                    synchronized (cochesAparcados) {
                        cochesAparcados.add(coche);
                        System.out.println(coche + " ha entrado.");
                    }
                    return true;
                } else {
                    return false;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void salir(Coche coche) {
        synchronized (cochesAparcados) {
            if (cochesAparcados.remove(coche)) {
                semaforo.release();
                System.out.println(coche + " ha salido del estacionamiento.");
            }
        }
    }

    public void desalojarCocheNormal(Coche cocheVip) {
        synchronized (cochesAparcados) {
            for (int i = 0; i < cochesAparcados.size(); i++) {
                Coche c = cochesAparcados.get(i);
                if (!c.esVip()) {
                    cochesAparcados.remove(i);
                    semaforo.release();
                    System.out.println(c + " fue desalojado por " + cocheVip + ".");
                    return;
                }
            }
            System.out.println("No hay coches normales para desalojar por " + cocheVip + ".");
        }
    }
}
