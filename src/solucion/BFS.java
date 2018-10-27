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
		
		Punto p = a;
		int auxX = x, auxY = y;
		int densidadMin;
		
		while(b.x != x || b.y != y) {
			densidadMin = 4;			
			if(x < b.x && mapa.getDensidad(x + 1, y) < 4 && !listaPuntos.contains(new Punto(x + 1, y))) {
				x++;
			} else if (x > b.x && mapa.getDensidad(x - 1, y) < 4 && !listaPuntos.contains(new Punto(x - 1, y))) {
				x--;
			} else if(y < b.y && mapa.getDensidad(x, y + 1) < 4 && !listaPuntos.contains(new Punto(x, y + 1))) {
				y++;
			} else if (y > b.y && mapa.getDensidad(x, y - 1) < 4 && !listaPuntos.contains(new Punto(x, y - 1))) {
				y--;
			} else {
				if (x < b.x && (mapa.getDensidad(x + 1, y) >= 4 || listaPuntos.contains(new Punto(x + 1, y)))) {
					if(mapa.getDensidad(x, y + 1) < 4 && !listaPuntos.contains(new Punto(x, y + 1))) {
						y++;
					} else if (mapa.getDensidad(x, y - 1) < 4 && !listaPuntos.contains(new Punto(x, y - 1))) {
						y--;
					} else {
						x = b.x;
						y = b.y;
					}
				} else if (x > b.x && (mapa.getDensidad(x - 1, y) >= 4 || !listaPuntos.contains(new Punto(x - 1, y)))) {
					if(mapa.getDensidad(x, y + 1) < 4) {
						y++;
					} else if (mapa.getDensidad(x, y - 1) < 4) {
						y--;
					} else {
						x = b.x;
						y = b.y;
					}
				} 
				else if (mapa.getDensidad(x, y - 1) == 4 || mapa.getDensidad(x, y +1) == 4) {
					if (mapa.getDensidad(x - 1, y) < mapa.getDensidad(x + 1, y)) {
						x--;
					} else {
						x++;
					}
				}
			}
			listaPuntos.add(new Punto(x, y));
			auxX = x;
			auxY = y;
		}
		
		
		/*
		while(x != b.x) {
			
			if(x < b.x) {
				/*if (mapa.getDensidad(x + 1, a.y) < 4) {
					x++;
					listaPuntos.add(new Punto(x, y));
				} else {
					while((mapa.getDensidad(x + 1, y) >= 4)) {
						y--;
					}
					x--;
					listaPuntos.add(new Punto(x, y));
				}
			} else {
				listaPuntos.add(new Punto(x, a.y));
			}
		}
		
		while(y != b.y) {
			if (y < b.y) {
				y++;
				listaPuntos.add(new Punto(x, y));
			} else {
				y--;
				listaPuntos.add(new Punto(x, y));
			}
		}*/
		
		cmc.dibujarCamino(listaPuntos);
		return listaPuntos;
	}
	
	private Punto elegirMejor(Punto a, Punto b) {
		if(mapa.getDensidad(a) <= mapa.getDensidad(b)) {
			
		}
		return a;
	}
}
