package solucion;


import graficos.Punto;


public class NodoPunto implements Comparable<NodoPunto>{
	private NodoPunto predecesor;
	private Punto p;
	private double densidad;
	private double distancia;
	
	public NodoPunto(Punto x, int dens) {
		this.p = x;
		this.densidad = dens;
		this.predecesor = null;
	}
	
	//public NodoPunto(Punto x, int dens, List<Punto> lista, NodoPunto prede){
	public NodoPunto(Punto x, double d, NodoPunto prede){
		this.p = x;
		this.densidad = d;
		this.predecesor = prede;
	}
	
	public NodoPunto getPredecesor() {
		return predecesor;
	}
	
	public Punto getP() {
		return this.p;
	}
	
	public double getDensidad() {
		return this.densidad;
	}
	
	public void setDistancia(Punto destino) {
		if(destino.x - p.x == 0 || destino.y - p.y == 0) this.distancia = (Math.abs(destino.x-p.x) + Math.abs(destino.y - p.y));
		else this.distancia = Math.sqrt((Math.pow(destino.x-p.x, 2) + Math.pow(destino.y - p.y,2)));		 		 
	}
	
	public double relacionDistanciaDensidad() {
			return this.distancia+this.densidad;
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
}
