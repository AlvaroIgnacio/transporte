package ar.edu.unrn.so.transporte;
/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Puente abstracto
 * @author Álvaro Bayón
 */
public abstract class Puente extends PuntoRuta {

	boolean estaLimpio = true;
	
	public Puente(String nombre) {
		super(nombre);
	}

	public abstract void cruzar(Vehiculo vehiculo) throws InterruptedException;

	// Inicia el proceso de limpieza indicando que el puente no está limpio
	// para bloquear el acceso a los vehículos
	public void iniciarLimpieza() {
		// Sincronizo sobre this para evitar problemas con los vehículos
		// El limpiador no debe esperar en la misma cola que los vehículos.
		synchronized (this) {
			// Bloqueamos el acceso a los vehículos
			estaLimpio = false;
			System.out.println("El puente " + this.nombre() + " está siendo limpiado.");
		}
	}

	public abstract void terminarLimpieza();

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
