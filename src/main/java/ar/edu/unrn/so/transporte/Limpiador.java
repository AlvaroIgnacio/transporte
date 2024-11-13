package ar.edu.unrn.so.transporte;

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
            	int tiempoEspera = (int) (Math.random() * 1000) + 500;
                Thread.sleep(tiempoEspera);

                // Alternamos entre los puentes para limpiarlos
                if (limpiarPuente1) {
                	System.out.println("El limpiador ha comenzado a limpiar el puente "+puente1.nombre()+". NO SE PUEDE PASAR!");
                    puente1.iniciarLimpieza();
                    
                    long tiempoLimpieza = (long) (Math.random() * 2000) + 1000;
                    Thread.sleep(tiempoLimpieza);  
                    
                    System.out.println("El limpiador TERMINÓ de limpiar el puente " + puente1.nombre());
                    puente1.terminarLimpieza();
                } else {
                	System.out.println("El limpiador ha comenzado a limpiar el puente "+puente2.nombre()+". NO SE PUEDE PASAR!");
                    puente2.iniciarLimpieza();
                    
                    long tiempoLimpieza = (long) (Math.random() * 2000) + 1000;
                    Thread.sleep(tiempoLimpieza);

                    System.out.println("El limpiador TERMINÓ de limpiar el puente " + puente2.nombre());
                    puente2.terminarLimpieza();
                }

                // Cambiar el puente a limpiar para el próximo ciclo
                limpiarPuente1 = !limpiarPuente1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	/*
    private final Puente puente;
    private boolean stop = false;

    public Limpiador(Puente puente) {
        this.puente = puente;
    }

    @Override
    public void run() {
        try {
            while (!stop) {
            	int tiempoEspera = (int) (Math.random() * 1000) + 500;
                Thread.sleep(tiempoEspera);

                // Comienza el proceso de limpieza
            	System.out.println("El limpiador ha comenzado a limpiar el puente "+puente.nombre()+". NO SE PUEDE PASAR!");
                this.puente.iniciarLimpieza();

                int tiempoLimpieza = (int) (Math.random() * 3000) + 2000;  
                Thread.sleep(tiempoLimpieza);

                System.out.println("El limpiador TERMINÓ de limpiar el puente " + puente.nombre());
                this.puente.terminarLimpieza();  
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    
    public void makeStop() {
    	this.stop = true;
    }

}
