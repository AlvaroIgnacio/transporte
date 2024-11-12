package ar.edu.unrn.so.transporte;

public abstract class Ciudad extends PuntoRuta{

    public Ciudad(String nombre) {
        super(nombre);
    }
    
    @Override
    public void ingresar(Vehiculo vehiculo) {
    	this.descargar(vehiculo);
    }

    public abstract void descargar(Vehiculo vehiculo);

    public abstract int mercaderiaRecibida(); 

}

