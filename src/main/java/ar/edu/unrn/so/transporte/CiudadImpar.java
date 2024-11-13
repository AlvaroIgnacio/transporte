package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Ciudad con descarga de acceso exclusivo
 * @author Álvaro Bayón
 */
class CiudadImpar extends Ciudad {

    private int mercaderiaRecibida = 0;
	
	public CiudadImpar(String nombre) {
		super(nombre);
	}

	/*
		Para poder descargar la mercancia en una ciudad con número impar, el vehículo debe hacerlo
		de forma exclusiva. O sea, solo un vehículo puede descargar la mercancia en un momento
		dado.
	 */
	@Override
    public synchronized void descargar(Vehiculo vehiculo) {
        int cantidadDescargada = vehiculo.descargar();
        mercaderiaRecibida += cantidadDescargada;
        System.out.println("El vehículo " + vehiculo.nombre() + " descargó " + cantidadDescargada);
    }
	
    public int mercaderiaRecibida() {
    	return mercaderiaRecibida;
    }
	
}
