package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Puente de dos carriles
 * @author Álvaro Bayón
 */
public class PuenteDosManos extends PuntoRuta {
	private boolean cruzandoAlOeste = false;
	private boolean cruzandoAlEste = false;
	// Sincronización para el acceso al puente
	private final Object lockAlOeste = new Object(); 
	private final Object lockAlEste = new Object(); 
	// Estado de limpieza del puente
	private boolean estaLimpio = true; 

	public PuenteDosManos(String nombre) {
		super(nombre);
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
				lockAlEste.wait(); // Espera si el puente está en limpieza o si ya hay un vehículo cruzando en B
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

	public void iniciarLimpieza() {
		// De esta forma evito race conditions entre el limpiador y los vehículos		
		synchronized (this) {
			estaLimpio = false; 
			System.out.println("El puente " + this.nombre() + " está siendo limpiado.");
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

	@Override
	public void ingresar(Vehiculo vehiculo) {
		this.cruzar(vehiculo);
	}

	public void cruzar(Vehiculo vehiculo) {
		try {
			if (vehiculo.haciaEste()) {
				this.cruzarAlEste(vehiculo);
			} else {
				this.cruzarAlOeste(vehiculo);
			}
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
