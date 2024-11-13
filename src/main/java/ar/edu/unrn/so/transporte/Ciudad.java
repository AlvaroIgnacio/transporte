package ar.edu.unrn.so.transporte;

/**
 * Sistemas Operativos 2024
 * Sistema de transporte (ejercicio 3.4)
 * Representación abstracta de ciudades  
 * @author Álvaro Bayón
 */
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

