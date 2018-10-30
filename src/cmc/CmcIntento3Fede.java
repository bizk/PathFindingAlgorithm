package cmc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import auxiliares.Arista;
import auxiliares.Nodo;
import graficos.Punto;
import mapa.MapaInfo;


// **************************************************************************
// Esta versión intenta hacerlo todo sin nodos y sólo agregando las aristas 
// 						que sé que van a servir.
// **************************************************************************
public class CmcIntento3Fede {
	private MapaInfo mapa;
	private CmcImple cmc;
	private ArrayList<Punto> puntos_visitados;
	private PriorityQueue<Arista> aristas_encontradas;
	ArrayList<Arista> caminos_agregados;
	private Punto inicio;
	
	public CmcIntento3Fede(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		
		obtenerCaminoUCS();
	}

	private void obtenerCaminoUCS() {								//Recorre los puntos marcados y pide dibujar el camino
		Punto inicio=null, a = null, z = null;	
		Iterator<Punto> iter = mapa.getPuntos().iterator();
		
		
		int minimo = Integer.MAX_VALUE;								// Este es el valor del camino mínimo obtenido. Arranca en infinito, pero será reemplazado por el de FCS
		
		
		if (iter.hasNext()) {										//si hay puntos seleccionados
			inicio = iter.next();									//Escoge al primero como inicio
		
			while(iter.hasNext()){									//mientras queden puntos "destino"
				System.out.println("Hay puntos destino");
				z=iter.next();
				puntos_visitados=new ArrayList<Punto>();
				aristas_encontradas= new PriorityQueue<Arista>();
				caminos_agregados= new ArrayList<Arista>();
				puntos_visitados.add(inicio);
				aristas_encontradas.addAll(obtenerAristasAdyacentes(inicio,z));
				
				
				while (!aristas_encontradas.isEmpty()) {
					a=aristas_encontradas.peek().getDestino();
					caminos_agregados.add(aristas_encontradas.peek());
					puntos_visitados.add(a);
					aristas_encontradas.addAll(obtenerAristasAdyacentes(a,z));
					aristas_encontradas.remove();
					cmc.dibujarCamino(puntos_visitados, Color.YELLOW);
					
				}
			System.out.println("Listo");
				}
		}
}

	private PriorityQueue<Arista> obtenerAristasAdyacentes(Punto a, Punto z) {
		//TODO sólo calcula aristas horizontales y verticales. Agregar Diagonales.
		
		PriorityQueue<Arista> adyacentes =new PriorityQueue<Arista>();
		Punto b;
		
		if(a.x>0) {
			b= new Punto(a.x-1, a.y); 									//Punto a la izquierda
			if(mapa.getDensidad(b)<4) {									//Si no es infranqueable
				if(!puntos_visitados.contains(b)){						//Si no lo agregué nunca
					adyacentes.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
					System.out.println("Arista a la izquierda agregada de una");
				} else {
					if(elCostoEsMenor(a, b)){							//Si el costo de llegada a b es menor a través de "a",
						caminos_agregados.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
						System.out.println("Arista mejorada");			//reemplaza la arista y devuelve verdadero	
					}
					else
						System.out.println("No mejorada");
				} 														//Si no es menor, no hace nada.
			}	
		}
		if(a.x<599) {
			b= new Punto (a.x+1,a.y);									//Punto a la derecha
			if(mapa.getDensidad(b)<4) {									//Si no es infranqueable
				if(!puntos_visitados.contains(b)){						//Si no lo agregué nunca, agrego
					adyacentes.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
					System.out.println("Arista a la derecha agregada de una");
				} else {
					if(elCostoEsMenor(a, b)){							//Si el costo de llegada a b es menor, 
						caminos_agregados.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
						System.out.println("Arista mejorada");			//reemplaza la arista y devuelve verdadero	
					}
					else
						System.out.println("No mejorada");
				} 														//Si no es menor, no hace nada.
			}	
			
		}
		if(a.y>0) {
			b=new Punto(a.x,a.y-1);										//Punto arriba
			if(mapa.getDensidad(b)<4) {									//Si no es infranqueable
				if(!puntos_visitados.contains(b)){						//Si no lo agregué nunca
					adyacentes.add(new Arista(a,b,100*mapa.getDensidad(b)+1,calcularDistancia(b,z)));
					System.out.println("Arista arriba agregada de una");
				} else {
					if(elCostoEsMenor(a, b)){							//Si el costo de llegada a b es menor, 
						caminos_agregados.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
						System.out.println("Arista mejorada");			//reemplaza la arista y devuelve verdadero	
					}
					else
						System.out.println("No mejorada");
				} 														//Si no es menor, no hace nada.
			}	
		}
		if(a.y<799) {
			b=new Punto(a.x,a.y+1);
			if(mapa.getDensidad(b)<4) {									//Si no es infranqueable
				if(!puntos_visitados.contains(b)){						//Si no lo agregué nunca
					adyacentes.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
					System.out.println("Arista a abajo agregada de una");
				} else {
					if(elCostoEsMenor(a, b)){							//Si el costo de llegada a b es menor, 
						caminos_agregados.add(new Arista(a,b,100*(mapa.getDensidad(b)+1),calcularDistancia(b,z)));
						System.out.println("Arista mejorada");			//reemplaza la arista y devuelve verdadero	
					}
					else
						System.out.println("No mejorada");
				} 														//Si no es menor, no hace nada.
			}	
		}
		
		return adyacentes;
		
	}

	private boolean elCostoEsMenor(Punto a, Punto b) {
		int viejo=calcularCostoAcumulado(b);
		int nuevo= calcularCostoAcumulado(a)+mapa.getDensidad(b);
		if(nuevo<viejo) {
			if(caminos_agregados.remove(encontrarAristaConDestinoEn(b))){
				System.out.println("Arista vieja eliminada");
			}
			return true;
		}else {
			return false;
		}
	}

	private int calcularCostoAcumulado(Punto b) { 						
		//TODO costo de ejecución elevadísimo.												
																		// recorre la lista de aristas desde 
		Arista aux= encontrarAristaConDestinoEn(b);						// la que tiene a B como destino
		int costo=0;
		while(aux!=null&&!aux.getOrigen().equals(inicio)) {							// hasta el Inicio del camino 
			costo +=aux.getPeso();										// y suma el peso de cada arista
			aux= encontrarAristaConDestinoEn(aux.getOrigen());
		}
		return costo;
	}

	private Arista encontrarAristaConDestinoEn(Punto b) {
		//TODO Mejorar algoritmo de búsqueda para que no sea secuencial.
		for(Arista aux:caminos_agregados) {							//recorre la lista hasta encontrarlo
			if(aux.getDestino().equals(b)) {
				return aux;
			}	
		}
		return null;
	}

	private int calcularDistancia(Punto a, Punto z) {
		int distancia=0;												//calcula la distancia entre dos puntos por pitagoras
		distancia=(int)Math.sqrt((Math.pow(z.x-a.x,2)+Math.pow(z.y-a.y,2)));
		
		return distancia;
	}
	
}