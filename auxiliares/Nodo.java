package auxiliares;

import graficos.Punto;

public class Nodo implements Comparable<Nodo>{
	private Punto id; //El punto en sí mismo del nodo
	private Punto predecesor; //El punto que lo precede en el camino
	private int costo_acumulado; //El costo que tiene llegar desde el inicial hasta el nodo
	
	public Nodo(Punto id) {
		this.id= id;
		this.predecesor=null;
		int costo_acumulado=Integer.MAX_VALUE; //Inicio el costo de llegada en infinito al crear el nodo
	}

	public Punto getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(Punto predecesor) {
		this.predecesor = predecesor;
	}
	
	public int getCosto_acumulado() {
		return costo_acumulado;
	}

	public void setCosto_acumulado(int costo_acumulado) {
		this.costo_acumulado = costo_acumulado;
	}

	public void setID(Punto id) {
		this.id=id;
	}
	public Punto getID() {
		return id;
	}

	@Override
	public int compareTo(Nodo o) {
		// TODO Auto-generated method stub
		if (this.getID()==o.getID()) {
			return 0;
		}
		else {
			return 1;
		}
	}

}
