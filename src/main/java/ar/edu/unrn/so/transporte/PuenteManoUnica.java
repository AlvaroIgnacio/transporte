package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Puente de una vía
 * @author Álvaro Bayón
 */
public class PuenteManoUnica extends Puente {
	private final Object monitor = new Object();

	public PuenteManoUnica(String nombre) {
		super(nombre);
	}

	/*
	 * El puente que se encuentra en el norte de la ciudad (P1), tiene un solo
	 * carril, por lo que solo un vehículo puede cruzar por el en un momento dado,
	 * sin importar el sentido.
	 */
	public void cruzar(Vehiculo vehiculo) throws InterruptedException {
		synchronized (monitor) {
			// Si el puente está siendo limpiado, los vehículos esperan
			while (!estaLimpio) {
				System.out.println(
						vehiculo.nombre() + " está esperando, el puente " + this.nombre() + " está siendo limpiado.");
				monitor.wait();
			}

			// El vehículo puede cruzar el puente
			System.out.println(vehiculo.nombre() + " está cruzando el puente " + this.nombre());
			// Tiempo en que tarda cruzar el puente
			Thread.sleep(500);
			System.out.println(vehiculo.nombre() + " cruzó el puente " + this.nombre());
		}
	}


	public void terminarLimpieza() {
		synchronized (this) {
			estaLimpio = true;			
			System.out.println("Terminada la limpieza del puente " + this.nombre());
		}
		synchronized (monitor) {
			// Avisamos a los vehículos que pueden cruzar el puente
			monitor.notifyAll();
		}
	}



}
