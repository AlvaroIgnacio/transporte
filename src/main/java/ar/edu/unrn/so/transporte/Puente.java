package ar.edu.unrn.so.transporte;

/**
 * El puente que se encuentra en el norte de la ciudad (P1), tiene un solo carril, 
 * por lo que solo un vehículo puede cruzar por el en un momento dado, sin importar el sentido.
 * @author Álvaro Bayón
 */
public class Puente extends PuntoRuta {
    private boolean estaLimpio = true;
    private final Object monitor = new Object();
    
    public Puente(String nombre) {
    	super(nombre);
    }

    public void cruzar(Vehiculo vehiculo) throws InterruptedException {
        synchronized (monitor) {
            // Si el puente está siendo limpiado, los vehículos esperan
            while (!estaLimpio) {
                System.out.println(Thread.currentThread().getName() + " esperando, el puente está siendo limpiado.");
                monitor.wait();  // Los vehículos esperan hasta que el puente esté limpio
            }

            // El vehículo puede cruzar el puente
            System.out.println(vehiculo.nombre() + " está cruzando el puente " + this.nombre());
            // Tiempo en que tarda cruzar el puente
            Thread.sleep(2000);  
            System.out.println(vehiculo.nombre() + " cruzó el puente "+ this.nombre());
        }
    }

    public void iniciarLimpieza() {
        synchronized (monitor) {
            // Bloqueamos el acceso a los vehículos
            estaLimpio = false;
            System.out.println("El puente " + this.nombre() + " está siendo limpiado.");
        }
    }

    public void limpiezaFinalizada() {
        synchronized (monitor) {
            estaLimpio = true;  
            System.out.println("Terminada la limpieza del puente " + this.nombre());
            // Avisamos a los vehículos que pueden cruzar el puente
            monitor.notifyAll();  
        }
    }

	@Override
	public void ingresar(Vehiculo vehiculo) {
		try {
			this.cruzar(vehiculo);
		} catch (InterruptedException e) {
			System.out.println("Error: no es posible cruzar el puente.");
			e.printStackTrace();
		}
	}

	@Override
	protected int mercaderiaRecibida() {
		return 0;
	}
}
