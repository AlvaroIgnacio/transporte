package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Representación abstracta de un punto de ruta (puentes, ciudades)
 * @author Álvaro Bayón
 */
public abstract class PuntoRuta {

    private final String nombre;
    
    public PuntoRuta(String nombre) {
    	this.nombre = nombre;
    }
	
    public String nombre() {
    	return this.nombre;
    }
    
	public abstract void ingresar(Vehiculo vehiculo);

	protected abstract int mercaderiaRecibida();
	
}
