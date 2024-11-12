package ar.edu.unrn.so.transporte;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Galpon {

    private final CyclicBarrier barrier; // La barrera de sincronización
    private final String nombre;
    private AtomicInteger mercaderiaEntregada = new AtomicInteger(0);
    private final boolean este;


    public Galpon(String nombre, int grupoSize, boolean este) {
    	this.nombre = nombre;
    	this.este = este;
        this.barrier = new CyclicBarrier(grupoSize, new Runnable() {
            @Override
            public void run() {
                System.out.println("Un grupo de vehículos ha entrado al galpón.");
            }
        });
    }

    public String nombre() {
    	return this.nombre;
    }
    
    public boolean zonaEste() {
    	return this.este;
    }
    /*
		Para poder ingresar al galpón G1, debe de hacerse de a tres (3)
		vehículos juntos. Para el galpón G2, debe hacerse de a cinco (5) vehículos juntos. 
     */
    public void recibirVehiculo(Vehiculo vehiculo) {
        try {
            // El vehículo llega a la barrera
            System.out.println(vehiculo.nombre() + " está esperando para formar un grupo en " + this.nombre);
            // El vehículo espera a que los otros miembros del grupo lleguen (3 o 5 dependiendo del galpón)
            barrier.await();  
            System.out.println(vehiculo.nombre() + " ha llegado a " + this.nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int cargarMercaderia() {
        Random rand = new Random();
        // Mercancía aleatoria entre 50 y 150
        int mercaderia = rand.nextInt(101) + 50;
        mercaderiaEntregada.addAndGet(mercaderia);
        return mercaderia;  
    }
    
    public int mercaderiaEntregada() {
    	return mercaderiaEntregada.get();
    }

}
