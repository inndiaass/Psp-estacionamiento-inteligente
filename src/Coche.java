public class Coche extends Thread {
    private final String nombre;
    private final boolean vip;
    private final Estacionamiento estacionamiento;

    public Coche(String nombre, boolean vip, Estacionamiento estacionamiento) {
        this.nombre = nombre;
        this.vip = vip;
        this.estacionamiento = estacionamiento;
    }

    public boolean esVip() {
        return vip;
    }

    @Override
    public void run() {
        if (estacionamiento.entrar(this)) {
            try {
                int tiempo = (int) (Math.random() * 4000) + 2000; // 2 a 6 segundos
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                estacionamiento.salir(this);
            }
        } else {
            System.out.println(this + " no pudo aparcar.");
        }
    }

    @Override
    public String toString() {
        return (vip ? "[VIP] " : "[Normal] ") + nombre;
    }
}
