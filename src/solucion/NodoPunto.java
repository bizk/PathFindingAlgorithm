package solucion;

import java.util.List;

import graficos.Punto;

public class NodoPunto {
	private List<Punto> puntos;
	private NodoPunto predecesor;
	private Punto p;
	private int densidad;
	
	public NodoPunto(Punto x, int dens, List<Punto> lista, NodoPunto prede){
		this.p = x;
		this.densidad = dens;
		this.predecesor = prede;
		this.puntos = lista;
		this.puntos.add(x);
	}
	
	public NodoPunto getPredecesor() {
		return predecesor;
	}

	public List<Punto> getPuntos() {
		return puntos;
	}
	
	public Punto getP() {
		return p;
	}
	
	public int getDensidad() {
		return densidad;
	}
}
