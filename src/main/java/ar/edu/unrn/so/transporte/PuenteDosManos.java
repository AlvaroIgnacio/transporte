package ar.edu.unrn.so.transporte;

public class PuenteDosManos extends PuntoRuta {
	private boolean cruzandoAlOeste = false;
	private boolean cruzandoAlEste = false;
	private boolean estaLimpio = true; // Estado de limpieza del puente
	private final Object lock = new Object(); // Objeto de sincronización para el acceso al puente

	public PuenteDosManos(String nombre) {
		super(nombre);
	}

	public void cruzarAlEste(Vehiculo vehiculo) throws InterruptedException {
		synchronized (lock) {
			while (!estaLimpio || cruzandoAlEste) {
				if (!estaLimpio) {
					System.out.println(vehiculo.nombre() + " está esperando, el puente " + this.nombre()
							+ " está siendo limpiado.");
				} else {
					System.out.println(
							vehiculo.nombre() + " esperando para cruzar en dirección Este el puente " + this.nombre());
				}
				lock.wait(); // Espera si el puente está en limpieza o si ya hay un vehículo cruzando en B
			}
			cruzandoAlEste = true;
			System.out.println(vehiculo.nombre() + " cruzando en dirección Este el puente " + this.nombre());
			cruzandoAlEste = false;
			// Avisa a los vehículos que esperan
			lock.notifyAll();
		}
	}

	public void cruzarAlOeste(Vehiculo vehiculo) throws InterruptedException {
		synchronized (lock) {
			while (!estaLimpio || cruzandoAlOeste) {
				if (!estaLimpio) {
					System.out.println(vehiculo.nombre() + " está esperando, el puente " + this.nombre()
							+ " está siendo limpiado.");
				} else {
					System.out.println(
							vehiculo.nombre() + " esperando para cruzar en dirección Oeste el puente " + this.nombre());
				}
				lock.wait(); // Espera si el puente está en limpieza o si ya hay un vehículo cruzando en A
			}
			cruzandoAlOeste = true;
			System.out.println(vehiculo.nombre() + " cruzando en dirección Oeste el puente " + this.nombre());
			cruzandoAlOeste = false;
			// Avisa a los vehículos que esperan
			lock.notifyAll();
		}
	}

	public void iniciarLimpieza() {
		synchronized (this) {
			estaLimpio = false; // El puente está siendo limpiado, los vehículos no pueden pasar
			System.out.println("El puente " + this.nombre() + " está siendo limpiado.");
			// lock.notifyAll(); // Notifica a los vehículos que deben esperar
		}
	}

	public void terminarLimpieza() {
		synchronized (lock) {
			estaLimpio = true; // El puente ha sido limpiado y está habilitado para los vehículos
			System.out.println("Terminada la limpieza del puente " + this.nombre());
			lock.notifyAll(); // Despierta a los vehículos que esperan
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
