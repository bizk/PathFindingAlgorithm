package cmc;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

import auxiliares.Arista;
import auxiliares.Nodo;
import graficos.Punto;
import mapa.MapaInfo;

public class CmciIntento2Fede {
	private MapaInfo mapa;
	private CmcImple cmc;
	
	public void CmcIntento2Fede(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		
		obtenerCaminoUCS();
	}

	private void obtenerCaminoUCS() {//Recorre los puntos marcados y pide dibujar el camino
		Punto a = null, z = null;	
		Nodo expandir = null;
		Iterator<Punto> iter = mapa.getPuntos().iterator();
		PriorityQueue<Arista> adyacencia=null;
		TreeSet<Nodo> visitados = null;
		List<Punto> solucion = null;
		Arista puente = null;
		int minimo = Integer.MAX_VALUE;// Este es el valor del camino mínimo obtenido. Arranca en infinito, pero será reemplazado por el de FCS
		
		
		if (iter.hasNext()) {//si hay puntos seleccionados
			a = iter.next();//Escoge al primero como inicio
		
			while(iter.hasNext()) {//mientras queden puntos "destino"
				adyacencia.addAll(obtenerAdyacencia(a)); //Agrego toda la adyacencia de A a la lista
				expandir = new Nodo(a); //Transformo a A en un nodo
				visitados.add(expandir); //Marco a A como visitado
				z = iter.next();//El siguiente va a ser el destino 
				while(!adyacencia.isEmpty()) {//mientras haya aristas para evaluar
					puente=adyacencia.peek();
					expandir.setID(puente.getDestino());
					expandir.setPredecesor(puente.getOrigen());
					
					//Estas dos líneas transforman el punto en nodo. El costo de llegada se define si se expande 
					
					
					if(!visitados.contains(expandir)&&expandir.getID()!=z) {//si el nodo no está visitado y no es el nodo final
						//debo expandirlo sin importar el costo.
						
						
					}
					else if(visitados.contains(expandir)&&expandir.getID()!=z) {//Si el nodo está visitado y no es el final
						//debo verificar que el costo acumulado por el nuevo camino sea menor para expandirlo
						
					}
					else {//Si el nodo es el nodo final
						//debo verificar que el camino tenga un costo mínimo y, si es asi, devolver la solución parcial.
					}
					
					adyacencia.remove();//quito el tope de la lista de adyacencia
					
				}
				
				
			}
			cmc.dibujarCamino(camino,Color.red);
			mapa.enviarMensaje("Camino minimo: tiene " + listaPuntos.size() + " puntos, con un costo de ");
		}
	}
	
	private PriorityQueue<Arista> obtenerAdyacencia(Punto a){//este método recibe un punto y devuelve todas las aristas del punto para transformarlo en nodo.
		PriorityQueue<Arista> listaux = new PriorityQueue<Arista>();
		Arista aux;
		Punto b = null;

				if(a.x>0) {//Si A no está en la primer columna
					b.x=a.x-1;
					b.y=a.y;//Evalua Punto a la derecha
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
				}
				if(a.x>0&&a.y>0) {//Si A no está en la primer fila, primer columna
					b.x=a.x-1;
					b.y=a.y+1;//Evalua Punto a la derecha, arriba
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD && (mapa.getDensidad(a.x-1,a.y)!=mapa.MAX_DENSIDAD&&mapa.getDensidad(a.x,a.y+1)!=mapa.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de derecha y arriba tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
				}
				if(a.x>0&&a.y<mapa.ALTO-1) //Si A no está en la ultima fila, primer columna
					b.x=a.x-1;
					b.y=a.y-1;//Evalua Punto a la derecha, abajo
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD && (mapa.getDensidad(a.x-1,a.y)!=mapa.MAX_DENSIDAD && mapa.getDensidad(a.x,a.y-1)!=mapa.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de derecha y abajo tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
				
				if(a.x<mapa.LARGO-1) {//Si A no está en la última columna
					b.x=a.x+1;
					b.y=a.y;//Evalua Punto izquierda
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
				}
				if(a.x<mapa.LARGO-1&&a.y>0) {//Si A no está en la primer fila, ultima columna
					b.x=a.x+1;
					b.y=a.y+1;//Evalua Punto izquierda, arriba
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD&& (mapa.getDensidad(a.x+1,a.y)!=mapa.MAX_DENSIDAD && mapa.getDensidad(a.x,a.y+1)!=mapa.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de izquierda y arriba tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
				}
				if(a.x<mapa.LARGO-1&&a.y<mapa.ALTO) {//Si A no está en la ultima fila, ultima columna
					b.x=a.x+1;
					b.y=a.y-1;//Evalua Punto izquierda, abajo
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD&& (mapa.getDensidad(a.x+1,a.y)!=mapa.MAX_DENSIDAD && mapa.getDensidad(a.x,a.y-1)!=mapa.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de izquierda y abajo tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
				}
				if(a.y>0) { //Si A no está en la primer fila
					b.x=a.x;
					b.y=a.y+1;//Evalua Punto arriba
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}	
				}
				if(a.y<mapa.ALTO-1) { //Si A no está en la ultima fila
					b.x=a.x;
					b.y=a.y-1;//Evalua Punto abajo
					if(mapa.getDensidad(b)!=mapa.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
			
		}
		
		return listaux;
	}

}
