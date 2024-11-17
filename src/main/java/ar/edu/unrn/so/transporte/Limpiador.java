package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Proceso de limpieza automática de puentes
 * @author Álvaro Bayón
 */
public class Limpiador extends Thread {
	
    private final Puente puente1;
    private final Puente puente2;
    private boolean limpiarPuente1 = true; 
    private boolean stop = false;

    public Limpiador(Puente puente1, Puente puente2) {
        this.puente1 = puente1;
        this.puente2 = puente2;
    }

    @Override
    public void run() {
        try {
            while (!stop) {
            	//El proceso esta detenido un tiempo aleatorio.
            	int tiempoEspera = (int) (Math.random() * 100) + 300;
                Thread.sleep(tiempoEspera);

                // Alternamos entre los puentes para limpiarlos
                if (limpiarPuente1) {
                	this.limpiar(puente1);
                } else {
                	this.limpiar(puente2);
                }

                // Alternamos entre los puentes para limpiarlos
                limpiarPuente1 = !limpiarPuente1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	    
    public void detener() {
    	this.stop = true;
    }

    private void limpiar(Puente puente) throws InterruptedException {
    	System.out.println("El limpiador ha comenzado a limpiar el puente "+puente.nombre()+". NO SE PUEDE PASAR!");
        puente.iniciarLimpieza();
        
        long tiempoLimpieza = (long) (Math.random() * 2000) + 1000;
        Thread.sleep(tiempoLimpieza);  
        
        // Se habilita el paso nuevamente
        System.out.println("El limpiador TERMINÓ de limpiar el puente " + puente.nombre());
        puente.terminarLimpieza();
    	
    }
    
}
