package ar.edu.unrn.so.transporte;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Galpón de carga y descarga de vehículos
 * @author Álvaro Bayón 
 */
public class Galpon {

	// Barrera para el ingreso grupal de camiones al galpón 
    private final CyclicBarrier barrier; 
    private final String nombre;
    // Recurso crítico para contar la cantidad de mercadería entregada
    // y así poder comparar mercadería entregada vs recibida en cada ciudad
    // Eso es para depurar. No forma parte del enunciado.
    private AtomicInteger mercaderiaEntregada = new AtomicInteger(0);
    private final boolean este;


    public Galpon(String nombre, int grupoSize, boolean este) {
    	this.nombre = nombre;
    	this.este = este;
    	
    	// Barrera para el ingreso de {n=grupoSize} camiones al galpón
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
    
    // Cada vehículo carga en el galpón donde se encuentra, una cantidad aleatoria de mercancias.
    public int cargarMercaderia() {
        Random rand = new Random();
        // Mercancía aleatoria entre 50 y 150
        int mercaderia = rand.nextInt(101) + 50;
        // Se actualiza atomicamente
        mercaderiaEntregada.addAndGet(mercaderia);
        return mercaderia;  
    }
    
    // Esto es para depurar. No forma parte del enunciado.
    public int mercaderiaEntregada() {
    	return mercaderiaEntregada.get();
    }

}
