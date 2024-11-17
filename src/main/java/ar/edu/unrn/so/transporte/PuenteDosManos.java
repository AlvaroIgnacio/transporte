package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Puente de dos carriles
 * @author Álvaro Bayón
 */
public class PuenteDosManos extends Puente {
	private boolean cruzandoAlOeste = false;
	private boolean cruzandoAlEste = false;
	// Sincronización para el acceso al puente
	private final Object lockAlOeste = new Object(); 
	private final Object lockAlEste = new Object(); 

	public PuenteDosManos(String nombre) {
		super(nombre);
	}

	public void cruzar(Vehiculo vehiculo) throws InterruptedException {
		if (vehiculo.haciaEste()) {
			this.cruzarAlEste(vehiculo);
		} else {
			this.cruzarAlOeste(vehiculo);
		}
	}

	/*
	Tiene dos carriles, por lo que solo un vehículo puede cruzar en un
	sentido, en un momento dado.	 
	*/
	public void cruzarAlEste(Vehiculo vehiculo) throws InterruptedException {
		synchronized (lockAlEste) {
			while (!estaLimpio || cruzandoAlEste) {
				if (!estaLimpio) {
					System.out.println(vehiculo.nombre() + " está esperando, el puente " + this.nombre()
							+ " está siendo limpiado.");
				} else {
					System.out.println(
							vehiculo.nombre() + " esperando para cruzar en dirección Este el puente " + this.nombre());
				}
				// Espera si el puente está en limpieza o si ya hay un vehículo cruzando en B
				lockAlEste.wait(); 
			}
			cruzandoAlEste = true;
			System.out.println(vehiculo.nombre() + " cruzando en dirección Este el puente " + this.nombre());
			Thread.sleep(500);
			cruzandoAlEste = false;
			System.out.println(vehiculo.nombre() + " cruzó el puente " + this.nombre() + " hacia el Este.");
			// Avisa a los vehículos que esperan
			lockAlEste.notifyAll();
		}
	}

	public void cruzarAlOeste(Vehiculo vehiculo) throws InterruptedException {
		synchronized (lockAlOeste) {
			while (!estaLimpio || cruzandoAlOeste) {
				if (!estaLimpio) {
					System.out.println(vehiculo.nombre() + " está esperando, el puente " + this.nombre()
							+ " está siendo limpiado.");
				} else {
					System.out.println(
							vehiculo.nombre() + " esperando para cruzar en dirección Oeste el puente " + this.nombre());
				}
				lockAlOeste.wait(); // Espera si el puente está en limpieza o si ya hay un vehículo cruzando en A
			}
			cruzandoAlOeste = true;
			System.out.println(vehiculo.nombre() + " cruzando en dirección Oeste el puente " + this.nombre());
			Thread.sleep(500);
			cruzandoAlOeste = false;
			System.out.println(vehiculo.nombre() + " cruzó el puente " + this.nombre() + " hacia el Oeste.");
			// Avisa a los vehículos que esperan
			lockAlOeste.notifyAll();
		}
	}

	public void terminarLimpieza() {
		synchronized (this) {
			estaLimpio = true; 
			System.out.println("Terminada la limpieza del puente " + this.nombre());
		}
		synchronized (lockAlEste) {
			// Habilita el paso al este
			lockAlEste.notifyAll();
		}
		synchronized (lockAlOeste) {
			// Habilita el paso al Oeste
			lockAlOeste.notifyAll(); 
		}
	}

}
