package cmc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;

import auxiliares.Arista;
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
				List<Punto> listavisitados= new ArrayList<Punto>();
				
				z = iter.next();//El siguiente va a ser el destino
				adyacencia.addAll(obtenerAdyacencia(expandir,z)); //Agrego toda la adyacencia de A a la lista
				System.out.println("La lista de adyacencia está iniciada correctamente");
				 
				while(!adyacencia.isEmpty()) {//mientras haya aristas para evaluar
					puente=adyacencia.peek();
					expandir= new Nodo(puente.getDestino());
					expandir.setPredecesor(puente.getOrigen());
					expandir.setCosto_acumulado(expandir.getPredecesor().getCosto_acumulado()+puente.getPeso());
					//Estas tres líneas transforman el punto en nodo. 
					System.out.println("El nodo a expandir está creado");
			/*		if(!expandir.isVisitado()) {//esto en realidad dice si ya agregué las aristas del nodo.
						adyacencia.addAll( obtenerAdyacencia(expandir)); //Agrego toda la adyacencia de A a la lista
						
					}*/
					
					int costoanterior=0;
						
					
					if(!visitados.contains(expandir)) {//si el nodo no está visitado
						//debo expandirlo sin importar el costo.
						adyacencia.addAll(filtrarLista(adyacencia, obtenerAdyacencia(expandir,z))); //Agrego toda la adyacencia de A a la lista
						System.out.println("Las aristas del nodo a expandir están listas");
						visitados.add(expandir);
						listavisitados.add(expandir.getID());
						System.out.println("Visité un nodo");
						cmc.dibujarCamino(listavisitados);
						if(expandir.getID().x==z.x&&expandir.getID().y==z.y) {//si es el final debo crear la solucion
							System.out.println("!!!!!!!!!!!!!!!!!!!!!ENCONTRE EL NODO FINAL POR PRIMERA VEZ");
							solucion= obtenerSolucion(expandir);
							System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Encontré mi primer solucion");
						}
					}
					else {//Si el nodo está visitado				
						//debo verificar que el costo acumulado por el nuevo camino sea menor para expandirlo
						Nodo aux=obtenerElemento(expandir.getID(), visitados);
						costoanterior=aux.getCosto_acumulado();
						System.out.println("Calculé el costo de llegada por el camino anterior: "+ costoanterior +"ahora es: " +expandir.getCosto_acumulado());
						if(expandir.getCosto_acumulado()<costoanterior) {
							visitados.remove(expandir);
							/*Perdon, esto es re confuso. El tema es que la unica identificación que importa en los nodos
							 * es el valor del nodo ID. Si yo paso "Expandir" como parametro al conjunto, va a  eliminar 
							 * el nodo viejo de visitados y después lo agrego con los datos nuevos*/
							visitados.add(expandir); //No lo hago directamente porque no sé si lo reemplaza.
							System.out.println("encontre un mejor camino al nodo");
							if(expandir.getID().x==z.x&&expandir.getID().y==z.y) {//si es el final debo modificar la solucion
								solucion= obtenerSolucion(expandir);
								System.out.println("##########encontré una mejor solucion");
							}
						}
					}
					adyacencia.remove();
					System.out.println("Me muevo a un nuevo nodo");
					cmc.dibujarCamino(listavisitados, Color.YELLOW);
					}
					
					
					
				}
				
				
			}
			cmc.dibujarCamino(solucion,Color.red);
			mapa.enviarMensaje("Camino minimo: " + solucion.size() + " puntos, con un costo de ");
		}
	
	private Collection<? extends Arista> filtrarLista(PriorityQueue<Arista> total,
			PriorityQueue<Arista> agregar) {
			
			for(Arista aux: agregar) {
				if(total.contains(aux)) {
					agregar.remove(aux);
				}
			}
		// TODO Auto-generated method stub
		return agregar;
	}

	private Nodo obtenerElemento(Punto id, LinkedHashSet<Nodo> visitados2) {
		
		for(Nodo aux :visitados2 ) {
			if(aux.getID()==id)
				return aux;
		}
		return null;
	}

	



	private ArrayList<Punto> obtenerSolucion(Nodo fin) {
		ArrayList<Punto> solucion = new ArrayList<Punto>();
		Nodo aux=fin;
		while(aux!=null) {
			solucion.add(aux.getID());
			aux=aux.getPredecesor();
		}
		
		return solucion;
	}

	private PriorityQueue<Arista> obtenerAdyacencia(Nodo a, Punto z){//este método recibe un punto y devuelve todas las aristas del punto para transformarlo en nodo.
		PriorityQueue<Arista> listaux = new PriorityQueue<Arista>();
		Arista aux;
		Punto b = null;

				if(a.getID().x<600) {//Si A no está en la ultima columna
					//Evalua Punto a la derecha
					b = new Punto(a.getID().x+1, a.getID().y);
					if(mapa.getDensidad(b)!=4) {
						aux=new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista derecha. Costo: "+100*(mapa.getDensidad(b)+1));
					}
					
				}
				if(a.getID().x<600&a.getID().y>0) {//Si A no está en la primer fila, ultima columna
					//Evalua Punto a la derecha, arriba
					b = new Punto(a.getID().x+1, a.getID().y-1);
					if(mapa.getDensidad(b)!=4 && (mapa.getDensidad(a.getID().x+1,a.getID().y)!=4&&mapa.getDensidad(a.getID().x,a.getID().y-1)!=4)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de derecha y arriba tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista derecha, arriba: "+141*(mapa.getDensidad(b)+1));
					}
					
				}
				if(a.getID().x<600-1&&a.getID().y<800-1) { //Si A no está en la ultima fila, ultima columna
					//Evalua Punto a la derecha, abajo
					b = new Punto(a.getID().x+1, a.getID().y+1);
					if(mapa.getDensidad(b)!=4 && (mapa.getDensidad(a.getID().x+1,a.getID().y)!=4 && mapa.getDensidad(a.getID().x,a.getID().y+1)!=4)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de derecha y abajo tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista derecha, abajo:" +141*(mapa.getDensidad(b)+1));
					}
					
				}
				if(a.getID().x>0) {//Si A no está en la primer columna
					//Evalua Punto izquierda
					b = new Punto(a.getID().x-1, a.getID().y);
					if(mapa.getDensidad(b)!=4) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista izquierda:" +100*(mapa.getDensidad(b)+1));
					}
					
				}
				if(a.getID().x>0&&a.getID().y>0) {//Si A no está en la primer fila, primer columna
					//Evalua Punto izquierda, arriba
					b = new Punto(a.getID().x-1, a.getID().y-1);
					if(mapa.getDensidad(b)!=4&& (mapa.getDensidad(a.getID().x-1,a.getID().y)!=4 && mapa.getDensidad(a.getID().x,a.getID().y-1)!=4)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de izquierda y arriba tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista izquierda, arriba "+141*(mapa.getDensidad(b)+1));
					}
					
				}
				if(a.getID().x>0&&a.getID().y<800-1) {//Si A no está en la ultima fila, primer columna
				//Evalua Punto izquierda, abajo
					b = new Punto(a.getID().x-1, a.getID().y+1);
					if(mapa.getDensidad(b)!=4&& (mapa.getDensidad(a.getID().x-1,a.getID().y)!=4 && mapa.getDensidad(a.getID().x,a.getID().y+1)!=4)) {
						//Solo si la densidad del punto B es menor a la máxima y el bloque de izquierda y abajo tambien tienen menos de la densidad mínima
						aux=new Arista(a, b,141*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista izquierda,abajo "+141*(mapa.getDensidad(b)+1));
					}
					
				}
				if(a.getID().y>0) { //Si A no está en la primer fila
					b = new Punto(a.getID().x, a.getID().y-1);
					if(mapa.getDensidad(b)!=4) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista arriba" +100*(mapa.getDensidad(b)+1));
					}	
					
				}
				if(a.getID().y<800-1) { //Si A no está en la ultima fila
					//Evalua Punto abajo
					b = new Punto(a.getID().x, a.getID().y+1);
					if(mapa.getDensidad(b)!=4) {
						aux=new Arista(a, b,100*(mapa.getDensidad(b)+1),calcularDistancia(a.getID(),z));
						listaux.add(aux);
						System.out.println("Agregada arista abajo "+100*(mapa.getDensidad(b)+1));
					}
					
			
		}
		
		return listaux;
	}

	private int calcularDistancia(Punto id, Punto z) {
		// TODO Auto-generated method stub
		int distancia=0;//calcula la distancia entre dos puntos por pitagoras
		distancia=(int)Math.sqrt((Math.pow(z.x-id.x,2)+Math.pow(z.y-id.y,2)));
		
		return distancia;
	}

}
