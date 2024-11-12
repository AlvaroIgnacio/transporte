package ar.edu.unrn.so.transporte;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Vehiculo extends Thread {
    private String nombre;
    private List<PuntoRuta> recorrido1;
    private List<PuntoRuta> recorrido2;
    private Galpon origen;
    private Galpon destino;
    private int mercaderia = 0;
    private int aDescargar = 0;
    private final boolean sentidoEste;

    public Vehiculo(int numero, List<PuntoRuta> recorrido1, List<PuntoRuta> recorrido2, Galpon origen, Galpon destino, boolean este) {
        this.nombre = "V" + numero;
        this.recorrido1 = recorrido1;
        this.recorrido2 = recorrido2;
        this.origen = origen;
        this.destino = destino;
        //this.este = este;
        this.sentidoEste = !origen.zonaEste();
    }

    public String nombre() {
        return nombre;
    }

    public boolean haciaEste() {
    	return this.sentidoEste;
    }
    
	//El vehículo, en su recorrido, va descargando el 25% de las mercancias, por cada ciudad que pasa en su recorrido.
    public int descargar() {
    	int cantidadDescargada = mercaderia;

    	if (mercaderia >= aDescargar*2) {
    		mercaderia -= aDescargar;
    		return aDescargar;
    	} else {
        	// Para el caso donde la cantidad de mercaderia no es múltiplo de 4
    		mercaderia = 0;
    	}
    	return cantidadDescargada;
    }

    private void cargarMercaderia() {
    	this.mercaderia = origen.cargarMercaderia();
    	// Determino la cantidad de mercadería a descargar en cada punto
    	this.aDescargar = (int) (mercaderia * 0.25);
    }
    
    // El vehículo elige la ruta a tomar al galpón del otro lado de la ciudad, también de forma aleatoria.
    private List<PuntoRuta> elegirRuta() {
        Random rand = new Random();
        // Genera un número aleatorio entre 1 y 2
        int opcion = rand.nextInt(2) + 1;
        if (opcion ==1) {
        	return this.recorrido1;
        }
        return this.recorrido2;
    }
    
    @Override
    public void run() {
    	// Cuando inicia un día, cada vehículo carga en el galpón donde se encuentra, una cantidad aleatoria de mercancias
        this.cargarMercaderia();
        System.out.println("El vehículo " + this.nombre + " cargó " + this.mercaderia + " en el galpón " + origen.nombre());

        // El vehículo elige la ruta a tomar al galpón del otro lado de la ciudad, también de forma aleatoria.
        var ruta = this.elegirRuta();
        
        if (this.sentidoEste) {
            for (PuntoRuta puntoRuta: ruta) {
                puntoRuta.ingresar(this);  
            }        	        	        	
        } else {
            // Si el sentido de circulación es oeste, recorre la ruta al revés
        	// ListIterator en reversa
        	ListIterator<PuntoRuta> iterator = ruta.listIterator(ruta.size());
        	while (iterator.hasPrevious()) {
        	    iterator.previous().ingresar(this);
        	}        	
        }
        
        /*
         	Una vez terminado el recorrido, el vehículo debe ingresar al galpón contrario a cargar
			más mercancias. Pero para poder ingresar al galpón G1, debe de hacerse de a tres (3)
			vehículos juntos. Para el galpón G2, debe hacerse de a cinco (5) vehículos juntos.
         */
        destino.recibirVehiculo(this);
    }}
