package solucion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cmc.CmcImple;
import graficos.Punto;
import mapa.MapaInfo;

public class BFS {
	private MapaInfo mapa;
	private CmcImple cmc;
	
	public BFS(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		obtenerCamino();
	}
	
	private void obtenerCamino() {
		Punto a = null, b = null;
		Iterator<Punto> puntos = mapa.getPuntos().iterator();
		List<Punto> listaPuntos = null;
		int minimo = Integer.MAX_VALUE;
		
		if(puntos.hasNext()) {
			a = puntos.next();
			//while(puntos.hasNext()) {
				b = puntos.next();
				List<Punto> aux = null;
				aux = expandirPuntosContiguos(a, b);
				
				if(aux.size() < minimo) {
					minimo = aux.size();
					listaPuntos = aux;
				}
			//}
			cmc.dibujarCamino(listaPuntos, Color.red);
			mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
		}
	}

	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		List<Punto> listaPuntos = new ArrayList<Punto>();
		int x = a.x;
		int y = a.y; 

//		List<NodoPunto> nodos = new ArrayList<NodoPunto>();
//		NodoPunto aux = new NodoPunto(a); //Creamos el nodo auxiliar en A
		
		
		while((b.x != x || b.y != y) && x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {			
			if(x < b.x && cumple(x + 1,y,listaPuntos) && y < b.y && cumple(x,y + 1,listaPuntos)) {
				x++;
				y++;
			} else if (x < b.x && cumple(x + 1,y,listaPuntos) && y > b.y && cumple(x,y - 1,listaPuntos)) {
				x++;
				y--;
			} else if (x > b.x && x > b.x && cumple(x - 1,y,listaPuntos) && y < b.y && cumple(x,y + 1,listaPuntos)) {
				x--;
				y++;
			} else if (x > b.x && x > b.x && cumple(x - 1,y,listaPuntos) && y > b.y && cumple(x,y - 1,listaPuntos)) {
				x--;
				y--;
			} else {
				if(y < b.y) {
					if(cumple(x,y + 1,listaPuntos))y++;
					else {
						if(cumple(x + 1,y,listaPuntos)) x++;
						else if (cumple(x - 1,y,listaPuntos)) x--;
						else if (cumple(x, y - 1, listaPuntos)) y--;
						else {
							System.out.println("ERROR 3");
							break;
						}
					}
				} else if (y > b.y) {
					if(cumple(x,y - 1,listaPuntos))y--;
					else {
						if(cumple(x + 1,y,listaPuntos)) x++;
						else if (cumple(x - 1,y,listaPuntos)) x--;
						else if (cumple(x, y+1, listaPuntos)) y++;
						else {
							System.out.println("ERROR 4");
							break;
						}
					}
				}	else if(x < b.x) {
					if(cumple(x + 1,y,listaPuntos))x++;
					else if (y == b.y){
						if(cumple(x,y + 1,listaPuntos)) y++;
						else if (cumple(x,y -1,listaPuntos)) y--;
						else if (cumple(x-1, y, listaPuntos)) x--;
						else {
							System.out.println("ERROR 1");
							System.out.println("x: " + x + "y: " + y);
							break;
						}
					}
				} else if (x > b.x) {
					if(cumple(x - 1,y,listaPuntos)) x--;
					else if(y == b.y){
						if(cumple(x,y + 1,listaPuntos)) y++;
						else if (cumple(x,y-1,listaPuntos)) y--;
						else if (cumple(x+1, y, listaPuntos)) x++;
						else {
							System.out.println("ERROR 2");							
							break;
						}
					}
				} 
				else {
					System.out.println("x: ");
					break;
				}
			}
			
			listaPuntos.add(new Punto(x, y));
		}
		
		cmc.dibujarCamino(listaPuntos);
		return listaPuntos;
	}
	
	private boolean cumple(int x, int y, List<Punto> puntos) {
		if(x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {
			if(mapa.getDensidad(x, y) < 4 && !puntos.contains(new Punto(x, y))) return true;
			return false;
		}
		return false;
	}
}
