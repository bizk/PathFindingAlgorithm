package auxiliares;

import graficos.Punto;

public class Arista implements Comparable<Arista>{
	private Punto origen;
	private Punto destino;
	private int peso;
	
	public Arista(Punto origen, Punto destino, int peso) {
		this.origen=origen;
		this.destino = destino;
		this.peso=peso;
	}
	
	
	public Punto getOrigen() {
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
	
	
}
