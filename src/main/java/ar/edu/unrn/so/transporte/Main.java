package ar.edu.unrn.so.transporte;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import sun.misc.Signal;

/**
 * Sistemas Operativos 2024
 * Reentrega Trabajo entregable 3
 * Sistema de transporte (ejercicio 3.4)
 * @author Álvaro Bayón
 *
 */
public class Main {

	//Indica si se recibió un signal
	public static final AtomicBoolean signalReceived = new AtomicBoolean(false);		

    private static final int NUM_VEHICULOS = 15;
    
    private static final Vehiculo[] vehiculos = new Vehiculo[NUM_VEHICULOS*2];
  
    private static final CiudadImpar ciudad1 = new CiudadImpar("C1");
    private static final CiudadPar ciudad2 = new CiudadPar("C2");
    private static final CiudadImpar ciudad3 = new CiudadImpar("C3");
    private static final CiudadPar ciudad4 = new CiudadPar("C4");
    private static final CiudadImpar ciudad5 = new CiudadImpar("C5");
    private static final CiudadPar ciudad6 = new CiudadPar("C6");
    private static final CiudadImpar ciudad7 = new CiudadImpar("C7");
    private static final CiudadPar ciudad8 = new CiudadPar("C8");
    
    // Para poder ingresar al galpón G1, debe de hacerse de a tres (3) vehículos juntos. 
    private static final Galpon galpon1 = new Galpon("G1", 3, false);
    // Para el galpón G2, debe hacerse de a cinco (5) vehículos juntos.
    private static final Galpon galpon2 = new Galpon("G2", 5, true);
    
    // En la ciudad existen dos (2) puentes (P1 y P2), los mismos no son iguales.
    private static final Puente puente1 = new Puente("P1");
    private static final PuenteDosManos puente2 = new PuenteDosManos("P2");

    private static final Limpiador limpiador = new Limpiador(puente1, puente2);
    
    public static void main(String[] args) throws InterruptedException {

    	// Manejador del signal TERM
    	Signal.handle(new Signal("TERM"), new Handler());    	
    	
    	// El sistema de transporte, cuenta con dos (2) rutas (R1 y R2).
    	// Para que un vehículo recorra la R1, debe atravesar C1, C2, P1, C3 y C4;
        var ruta1 = Arrays.asList(ciudad1, ciudad2, puente1, ciudad3, ciudad4);
        // Para que un vehículo recorra la R2, debe atravezar C8, C7, P2, C6 y C5;
        var ruta2 = Arrays.asList(ciudad8, ciudad7, puente2, ciudad6, ciudad5);
        
        for (int i = 0; i < NUM_VEHICULOS; i++) {
            vehiculos[i] = new Vehiculo(i + 1, ruta1, ruta2, galpon1, galpon2, true);
            // Iniciar el hilo (se debe modelar a los vehículos como hilos)
            vehiculos[i].start();  
        }

        for (int i = NUM_VEHICULOS; i < NUM_VEHICULOS * 2; i++) {
        	vehiculos[i] = new Vehiculo(i + 1, ruta1, ruta2, galpon2, galpon1, false);
            // Iniciar el hilo (se debe modelar a los vehículos como hilos)
            vehiculos[i].start();  
        }
        
        limpiador.start();
        
        // Esperar que todos los vehículos terminen su recorrido
        for (int i = 0; i < NUM_VEHICULOS; i++) {
            try {
                vehiculos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = NUM_VEHICULOS; i < NUM_VEHICULOS * 2; i++) {
            try {
                vehiculos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Todos llegaron al galpón de destino.");
        
        // Al recibir la señal TERM, debe terminar. Para terminar, debe esperar a que
        // todos los vehículos lleguen e ingresen al galpón de destino.
        if(signalReceived.get()) {
        	System.exit(0);
        }            
        
        // Detener al limpiador de puentes
        limpiador.detener();

        Thread.sleep(5000);
        
        for(PuntoRuta ciudad: ruta1) {
        	if (ciudad.mercaderiaRecibida()>0)
        		System.out.println(ciudad.nombre() + " recibió: " + ciudad.mercaderiaRecibida());
        }
        for(PuntoRuta ciudad: ruta2) {
        	if (ciudad.mercaderiaRecibida()>0)
        		System.out.println("La ciudad " + ciudad.nombre() + " recibió: " + ciudad.mercaderiaRecibida());
        }
        
        System.out.println("Entregada por G1: " + galpon1.mercaderiaEntregada());
        System.out.println("Entregada por G2: " + galpon2.mercaderiaEntregada());
        
    }	
}
