package auxiliares;

import graficos.Punto;

public class Arista implements Comparable<Arista>{
	private Nodo origen;
	private Punto destino;
	private int peso;
	private int distancia;
	
	public Arista(Nodo origen, Punto destino, int peso,int distancia) {
		this.origen=origen;
		this.destino = destino;
		this.peso=peso;
		this.distancia=distancia;
	}
	
	
	public int getDistancia() {
		return distancia;
	}


	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}


	public Nodo getOrigen() {
		return origen;
	}

	public Punto getDestino() {
		return destino;
	}

	public int getPeso() {
		return peso;
	}


	public int compareTo(Arista o) {
		if (this.peso>o.getPeso()&&this.distancia>o.getDistancia())//si tiene mayor peso y mayor distancia
			return 1;
		else if(this.peso<=o.getPeso()&&this.distancia<o.getDistancia()) //si tiene igual o menor peso y menor distancia
			return -1;
		else //si tiene igual peso y mayor o igual distancia
			return 0;
	}
	public boolean equals(Arista o) {
		 if(this.origen==o.origen&&this.destino==o.destino) {
				return true;
			}
			else
				return false;
	}
	
	
}
