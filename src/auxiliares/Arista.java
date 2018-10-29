package auxiliares;

import graficos.Punto;

public class Arista implements Comparable<Arista>{
	private Nodo origen;
	private Punto destino;
	private int peso;
	
	public Arista(Nodo origen, Punto destino, int peso) {
		this.origen=origen;
		this.destino = destino;
		this.peso=peso;
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
		if (this.peso>o.getPeso())
			return 1;
		else
			return-1;
	}
	public boolean equals(Arista o) {
		 if(this.origen==o.origen&&this.destino==o.destino) {
				return true;
			}
			else
				return false;
	}
	
	
}
