package solucion;

import java.util.ArrayList;
import java.util.List;

import graficos.Punto;

public class NodoPunto {
	private List<Punto> puntos;
	private NodoPunto predecesor;
	private Punto p;
	private int densidad;
	private int distancia;
	
	public NodoPunto(Punto x, int dens) {
		this.p = x;
		this.densidad = dens;
		this.predecesor = null;
		this.puntos = new ArrayList<Punto>();
		this.puntos.add(x);
	}
	
	public NodoPunto(Punto x, int dens, List<Punto> lista, NodoPunto prede){
		this.p = x;
		this.densidad = dens;
		this.predecesor = prede;
		this.puntos = new ArrayList<Punto>();
		this.puntos.addAll(lista);
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
	
	public void setDistancia(Punto destino) {
		this.distancia = Math.abs(destino.x-p.x) + Math.abs(destino.y - p.y);
	}
	
	public int relacionDistanciaDensidad() {
		return distancia + densidad;
	}
	
	@Override
	public String toString() {
		return "["+this.getP().x + ":" + this.getP().y + "]";
	}
}
