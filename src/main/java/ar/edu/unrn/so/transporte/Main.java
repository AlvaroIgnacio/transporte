package ar.edu.unrn.so.transporte;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Sistemas Operativos 2024
 * Reentrega Trabajo entregable 3
 * Sistema de transporte (ejercicio 3.4)
 * @author Álvaro Bayón
 *
 */
public class Main {

	//TODO: Limpiador del Puente
	//TODO: atajar SIGNAL
	//TODO: readme
	
	
	//HECHOS
	// Vehiculos
	// Galpon con barrera
	// Puente de una vía
	// Sincro de Vehiculo y Ciudad con synchronized
	// mercadería de double a int
	// descarga de mercaderia
	// usar el Puente de una vía
	// Puntos de ruta, que incluya ruta1, puente y galpon
	// Agregar el Puente de dos vías
	// Ruta de vuelta
	// Otra ruta con el Puente de doble vía
	// Selector de ruta
	// Comentarios usando el enunciado del TP
	
	
	//Indica si se recibió un signal
	public static final AtomicBoolean signalReceived = new AtomicBoolean(false);		

	//TODO: cambiar el numero de vehículos
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
    
    /*
     * Para poder ingresar al galpón G1, debe de hacerse de a tres (3) vehículos juntos. 
     * Para el galpón G2, debe hacerse de a cinco (5) vehículos juntos.
     */
    private static final Galpon galpon1 = new Galpon("G1", 3, false);
    //TODO: CAMBIAR NUMERO A 5
    private static final Galpon galpon2 = new Galpon("G2", 3, true);
    
    // En la ciudad existen dos (2) puentes (P1 y P2), los mismos no son iguales.
    private static final Puente puente1 = new Puente("P1");
    private static final PuenteDosManos puente2 = new PuenteDosManos("P2");

    private static final Limpiador limpiador = new Limpiador(puente1, puente1);
    
    public static void main(String[] args) throws InterruptedException {

    	// El sistema de transporte, cuenta con dos (2) rutas (R1 y R2).
    	// Para que un vehículo recorra la R1, debe atravesar C1, C2, P1, C3 y C4;
        var ruta1 = Arrays.asList(ciudad1, ciudad2, puente1, ciudad3, ciudad4);
        // Para que un vehículo recorra la R2, debe atravezar C8, C7, P2, C6 y C5;
        var ruta2 = Arrays.asList(ciudad8, ciudad7, puente2, ciudad6, ciudad5);
        
        //TODO: mejora: lanzador de vehículos
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
        // Esperar que todos los vehículos terminen su recorrido
        for (int i = NUM_VEHICULOS; i < NUM_VEHICULOS * 2; i++) {
            try {
                vehiculos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        limpiador.makeStop();
        
        System.out.println("Todos llegaron");
        Thread.sleep(5000);
        
        //TODO: refactor
        for(PuntoRuta ciudad: ruta1) {
        	if (ciudad.mercaderiaRecibida()>0)
        		System.out.println(ciudad.nombre() + " recibió: " + ciudad.mercaderiaRecibida());
        }
        for(PuntoRuta ciudad: ruta2) {
        	if (ciudad.mercaderiaRecibida()>0)
        		System.out.println("La ciudad " + ciudad.nombre() + " recibió: " + ciudad.mercaderiaRecibida());
        }
        
        //TODO: galpon1 es quien entrega la mercadería. Modelar con cambios en Vehículo
        System.out.println("Entregada por G1: " + galpon1.mercaderiaEntregada());
        System.out.println("Entregada por G2: " + galpon2.mercaderiaEntregada());
        
    }	
}
