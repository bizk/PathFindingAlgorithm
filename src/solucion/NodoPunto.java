package solucion;

import java.util.ArrayList;
import java.util.List;

import graficos.Punto;


public class NodoPunto implements Comparable<NodoPunto>{
	//private List<Punto> puntos;
	private NodoPunto predecesor;
	private Punto p;
	private int densidad;
	private int distancia;
	
	public NodoPunto(Punto x, int dens) {
		this.p = x;
		this.densidad = dens;
		this.predecesor = null;
		//this.puntos = new ArrayList<Punto>();
		//this.puntos.add(x);
	}
	
	//public NodoPunto(Punto x, int dens, List<Punto> lista, NodoPunto prede){
	public NodoPunto(Punto x, int dens, NodoPunto prede){
		this.p = x;
		this.densidad = dens;
		this.predecesor = prede;
	//	this.puntos = new ArrayList<Punto>();
	//	this.puntos.addAll(lista);
	//	this.puntos.add(x);
	}
	
	public NodoPunto getPredecesor() {
		return predecesor;
	}

	/*public List<Punto> getPuntos() {
		return puntos;
	}*/
	
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
			return distancia-densidad;
	}
	
	@Override
	public String toString() {
		return "["+this.getP().x + ":" + this.getP().y + "]";
	}
	
	@Override
	public int compareTo(NodoPunto n){
		if(n.relacionDistanciaDensidad() < this.relacionDistanciaDensidad()){
			return 1;
		}else if(n.relacionDistanciaDensidad() == this.relacionDistanciaDensidad()){
			return 0;
		}else{
			return -1;
		}
	}

	public void setPredecesor(NodoPunto nodo) {
		this.predecesor=nodo;
	}

	public void setDensidad(int i) {
		// TODO Auto-generated method stub
		this.densidad=i;
		
	}

	/*public void setPuntos(List<Punto> puntos2) {
		// TODO Auto-generated method stub
		this.puntos=puntos2;
		
	}*/
}
