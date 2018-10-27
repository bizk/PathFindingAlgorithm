package solucion;

import graficos.Punto;

public class NodoPunto {
	private Punto sig;
	private Punto p;
	
	NodoPunto(Punto x){
		this.sig = null;
		this.p = x;
	}
	
	public void setSig(Punto aux) {
		this.sig = aux;
	}

	public Punto getP() {
		return p;
	}

	public Punto getSig() {
		return sig;
	}
}
