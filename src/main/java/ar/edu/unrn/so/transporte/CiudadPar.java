package ar.edu.unrn.so.transporte;

import java.util.concurrent.atomic.AtomicInteger;

class CiudadPar extends Ciudad {
	private AtomicInteger mercaderiaRecibida = new AtomicInteger(0);

    public CiudadPar(String nombre) {
		super(nombre);
	}

    /*
     * En las ciudades con numeración par, pueden descargar todos los vehículos que lo requieran de forma simultánea.
     */
    @Override
    public void descargar(Vehiculo vehiculo) {
        int cantidadDescargada = vehiculo.descargar();
        // Operación atómica
        mercaderiaRecibida.addAndGet(cantidadDescargada);  
        System.out.println("El vehículo " + vehiculo.nombre() + " descargó " + cantidadDescargada + " unidades en " + nombre());
    }
    
    public int mercaderiaRecibida() {
    	return mercaderiaRecibida.get();
    }
    
}
