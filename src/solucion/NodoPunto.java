package solucion;

import java.util.ArrayList;

import graficos.Punto;

public class NodoPunto {
	private ArrayList<Punto> puntos;
	private Punto p;
	private int densidad;
	
	public NodoPunto(Punto x, int dens, ArrayList<Punto> lista){
		this.p = x;
		this.densidad = dens;
		this.puntos = lista;
		puntos.add(x);
	}

	public ArrayList<Punto> getPuntos() {
		return puntos;
	}
	
	public Punto getP() {
		return p;
	}
	
	public ArrayList<Punto> getListaPuntos() {
		return puntos;
	}
	
	public int getDensidad() {
		return densidad;
	}
}
