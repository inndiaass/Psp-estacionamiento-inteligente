import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Estacionamiento estacionamiento = new Estacionamiento();
        ArrayList<Coche> coches = new ArrayList<>();


        for (int i = 1; i <= 15; i++) {
            boolean vip = i % 3 == 0;
            coches.add(new Coche("Coche " + i, vip, estacionamiento));
        }

        for (Coche c : coches) c.start();

        for (Coche c : coches) {
            try {
                c.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Todos los coches han intentado aparcar.");
    }
}
