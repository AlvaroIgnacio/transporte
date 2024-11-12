package ar.edu.unrn.so.transporte;

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
