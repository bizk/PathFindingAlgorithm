package cmc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

import auxiliares.Arista;
import auxiliares.MySet;
import auxiliares.Nodo;
import graficos.Punto;
import mapa.MapaInfo;

public class CmcIntento2Fede {
	private MapaInfo mapa;
	private CmcImple cmc;
	private LinkedHashSet<Nodo> visitados;
	public CmcIntento2Fede(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		
		obtenerCaminoUCS();
	}

	private void obtenerCaminoUCS() {//Recorre los puntos marcados y pide dibujar el camino
		Punto a = null, z = null;	
		Nodo expandir = null;
		Iterator<Punto> iter = mapa.getPuntos().iterator();
		PriorityQueue<Arista> adyacencia= new PriorityQueue<Arista>();
		visitados = new LinkedHashSet<Nodo>();
		ArrayList<Punto> solucion = null;
		Arista puente = null;
		int minimo = Integer.MAX_VALUE;// Este es el valor del camino mínimo obtenido. Arranca en infinito, pero será reemplazado por el de FCS
		
		
		if (iter.hasNext()) {//si hay puntos seleccionados
			a = iter.next();//Escoge al primero como inicio
		
			while(iter.hasNext()) {//mientras queden puntos "destino"
				System.out.println("Hay puntos destino");
				expandir = new Nodo(a); //Transformo a A en un nodo
				visitados.add(expandir); //Marco a A como visitado
				
				adyacencia.addAll(obtenerAdyacencia(expandir)); //Agrego toda la adyacencia de A a la lista
				System.out.println("La lista de adyacencia está iniciada correctamente");
				z = iter.next();//El siguiente va a ser el destino 
				while(!adyacencia.isEmpty()) {//mientras haya aristas para evaluar
					puente=adyacencia.peek();
					expandir.setID(puente.getDestino());
					expandir.setPredecesor(puente.getOrigen());
					expandir.setCosto_acumulado(expandir.getPredecesor().getCosto_acumulado()+puente.getPeso());
					//Estas tres líneas transforman el punto en nodo. 
					System.out.println("El nodo a expandir está creado");
					adyacencia.addAll(verificoRepetidas(adyacencia, obtenerAdyacencia(expandir))); //Agrego toda la adyacencia de A a la lista
					System.out.println("Las aristas del nodo a expandir están listas");
					int costoanterior=0;
						
					
					if(!visitados.contains(expandir)) {//si el nodo no está visitado
						//debo expandirlo sin importar el costo.
						visitados.add(expandir);
						System.out.println("Visité un nodo");
						if(expandir.getID()==z) {//si es el final debo crear la solucion
							System.out.println("!!!!!!!!!!!!!!!!!!!!!ENCONTRE EL NODO FINAL POR PRIMERA VEZ");
							solucion= obtenerSolucion(expandir);
							System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Encontré mi primer solucion");
						}
					}
					else {//Si el nodo está visitado				
						//debo verificar que el costo acumulado por el nuevo camino sea menor para expandirlo
						costoanterior=obtenerElemento(expandir, visitados).getCosto_acumulado();
						System.out.println("Calculé el costo de llegada por el camino anterior");
						if(expandir.getCosto_acumulado()<costoanterior) {
							visitados.remove(expandir);
							/*Perdon, esto es re confuso. El tema es que la unica identificación que importa en los nodos
							 * es el valor del nodo ID. Si yo paso "Expandir" como parametro al conjunto, va a  eliminar 
							 * el nodo viejo de visitados y después lo agrego con los datos nuevos*/
							visitados.add(expandir); //No lo hago directamente porque no sé si lo reemplaza.
							System.out.println("encontre un mejor camino al nodo");
							if(expandir.getID()==z) {//si es el final debo modificar la solucion
								solucion= obtenerSolucion(expandir);
								System.out.println("##########encontré una mejor solucion");
							}
						}
					}
					
					System.out.println("Me muevo a un nuevo nodo");
					}
					
					
					
				}
				
				
			}
			cmc.dibujarCamino(solucion,Color.red);
			mapa.enviarMensaje("Camino minimo: " + solucion.size() + " puntos, con un costo de ");
		}
	
	private Collection<? extends Arista> verificoRepetidas(PriorityQueue<Arista> total,
			PriorityQueue<Arista> parcial) {
		// TODO Auto-generated method stub
			return null;
	}

	private Nodo obtenerElemento(Nodo expandir, LinkedHashSet<Nodo> visitados2) {
		// TODO Auto-generated method stub
		return visitados2.stream().filter(expandir::equals).findAny().orElse(null);
	}

	private ArrayList<Punto> obtenerSolucion(Nodo fin) {
		ArrayList<Punto> predecesores = new ArrayList<Punto>();
		Nodo aux=fin;
		while(aux!=null) {
			predecesores.add(aux.getID());
			aux=aux.getPredecesor();
		}
		
		return null;
	}

	private PriorityQueue<Arista> obtenerAdyacencia(Nodo a){//este método recibe un punto y devuelve todas las aristas del punto para transformarlo en nodo.
		PriorityQueue<Arista> listaux = new PriorityQueue<Arista>();
		Arista aux;
		Punto b = null;

				if(a.getID().x>0) {//Si A no está en la primer columna
					//Evalua Punto a la derecha
					b = new Punto(a.getID().x-1, a.getID().y);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD) {
						aux=new Arista(a,b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista derecha");
				}
				if(a.getID().x>0&&a.getID().y>0) {//Si A no está en la primer fila, primer columna
					//Evalua Punto a la derecha, arriba
					b = new Punto(a.getID().x-1, a.getID().y+1);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD && (mapa.getDensidad(a.getID().x-1,a.getID().y)!=MapaInfo.MAX_DENSIDAD&&mapa.getDensidad(a.getID().x,a.getID().y+1)!=MapaInfo.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de derecha y arriba tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista derecha, arriba");
				}
				if(a.getID().x>0&&a.getID().y<MapaInfo.ALTO-1) { //Si A no está en la ultima fila, primer columna
					//Evalua Punto a la derecha, abajo
					b = new Punto(a.getID().x-1, a.getID().y-1);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD && (mapa.getDensidad(a.getID().x-1,a.getID().y)!=MapaInfo.MAX_DENSIDAD && mapa.getDensidad(a.getID().x,a.getID().y-1)!=MapaInfo.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de derecha y abajo tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista derecha, abajo");
				}
				if(a.getID().x<MapaInfo.LARGO-1) {//Si A no está en la última columna
					//Evalua Punto izquierda
					b = new Punto(a.getID().x+1, a.getID().y);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista izquierda");
				}
				if(a.getID().x<MapaInfo.LARGO-1&&a.getID().y>0) {//Si A no está en la primer fila, ultima columna
					;//Evalua Punto izquierda, arriba
					b = new Punto(a.getID().x+1, a.getID().y+1);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD&& (mapa.getDensidad(a.getID().x+1,a.getID().y)!=MapaInfo.MAX_DENSIDAD && mapa.getDensidad(a.getID().x,a.getID().y+1)!=MapaInfo.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de izquierda y arriba tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista izquierda, arriba");
				}
				if(a.getID().x<MapaInfo.LARGO-1&&a.getID().y<MapaInfo.ALTO) {//Si A no está en la ultima fila, ultima columna
				//Evalua Punto izquierda, abajo
					b = new Punto(a.getID().x+1, a.getID().y-1);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD&& (mapa.getDensidad(a.getID().x+1,a.getID().y)!=MapaInfo.MAX_DENSIDAD && mapa.getDensidad(a.getID().x,a.getID().y-1)!=MapaInfo.MAX_DENSIDAD)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de izquierda y abajo tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista izquierda,abajo");
				}
				if(a.getID().y>0) { //Si A no está en la primer fila
					b = new Punto(a.getID().x, a.getID().y+1);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}	
					System.out.println("Agregada arista arriba");
				}
				if(a.getID().y<MapaInfo.ALTO-1) { //Si A no está en la ultima fila
					//Evalua Punto abajo
					b = new Punto(a.getID().x, a.getID().y-1);
					if(mapa.getDensidad(b)!=MapaInfo.MAX_DENSIDAD) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1));
						listaux.add(aux);
					}
					System.out.println("Agregada arista abajo");
			
		}
		
		return listaux;
	}

}
