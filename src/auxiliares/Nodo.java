package auxiliares;

import java.util.Objects;

import graficos.Punto;

public class Nodo implements Comparable<Nodo>{
	private Punto id; //El punto en sí mismo del nodo
	private Nodo predecesor; //El nodo que lo precede en el camino
	private int costo_acumulado; //El costo que tiene llegar desde el inicial hasta el nodo
	private boolean visitado; 
	
	public Nodo(Punto id) {
		this.id= id;
		this.predecesor=null;
		int costo_acumulado=Integer.MAX_VALUE; //Inicio el costo de llegada en infinito al crear el nodo
		this.visitado=false;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean expandido) {
		this.visitado = expandido;
	}

	public Nodo getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(Nodo predecesor) {
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
		if (this.getID().x==o.getID().x&&this.getID().y==o.getID().y) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public boolean equals(Nodo n) {
		if(this.getID().x==n.getID().x&&this.getID().y==n.getID().y) {
			return true;
		}
		else
			return false;
	}
	public boolean equals(Punto n) {
		if(this.getID().x==n.x&&this.getID().y==n.y) {
			return true;
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		int result = 11;
        result = 13*result + id.hashCode();
        return result;
	}

}
