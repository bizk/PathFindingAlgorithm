package solucion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cmc.CmcImple;
import graficos.Punto;
import mapa.MapaInfo;

public class Intento2Cordoba {
	private MapaInfo mapa;
	private CmcImple cmc;
	
	public Intento2Cordoba(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		demoObtenerCamino();
	}
	
	private void demoObtenerCamino() {
		Punto a = null, b = null;	
		Iterator<Punto> iter = mapa.getPuntos().iterator();
		List<Punto> listaPuntos = null;
		int minimo = Integer.MAX_VALUE;
		if (iter.hasNext()) {
			a = iter.next();
		
			//while(iter.hasNext()) {
				b = iter.next();
				List<Punto> aux = expandirPuntosContiguos(a, b);
				//if(aux.size() < minimo) {
					//minimo = aux.size();
					listaPuntos = aux;
				//}
			//}
			cmc.dibujarCamino(listaPuntos,Color.red);
			mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
		}
	}
	
	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		ArrayList<Punto> listaPuntos = new ArrayList<Punto>();
		ArrayList<NodoPunto> listaNodos = new ArrayList<NodoPunto>();
		ArrayList<NodoPunto> listaNodosSolucion = new ArrayList<NodoPunto>();
		
		NodoPunto aux = null;
		NodoPunto nodo = null;
		NodoPunto solucion = null;
		
		nodo = new NodoPunto(a, mapa.getDensidad(a), listaPuntos);
		listaNodos.add(nodo);
		listaNodosSolucion.add(nodo);
		ListIterator<NodoPunto> itr = listaNodos.listIterator();
		
	//	while(itr.hasNext() && (nodo.getP().x != b.x || nodo.getP().y != b.y) && solucion == null) {
			if(nodo.getP().x == b.x && nodo.getP().y == b.y) {
				solucion = nodo;
			} else {
				for(int i = nodo.getP().x - 1; i <= nodo.getP().x +1 ; i++) {
					for(int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
						if(i == b.x && j == b.y) {
							System.out.println("Encontrado");
							solucion = nodo;
							break;
						}
						if(chequearPunto(i, j, nodo) && !(i == nodo.getP().x && j == nodo.getP().y)) {
							aux = new NodoPunto(new Punto(i,j), mapa.getDensidad(i, j) + nodo.getDensidad() + 1, nodo.getPuntos());
							if(chequearNodos(listaNodosSolucion, aux) == true) {
								listaNodosSolucion.add(aux);
								//System.out.println("" + i + ":" + j);
								itr.add(aux);
					
								//itr.set(aux);
								itr.previous();
								cmc.dibujarCamino(aux.getListaPuntos(), Color.blue);

							}
						}
					}
				}	
			}
			listaNodosSolucion.remove(nodo);
			nodo = itr.next();
		//}
		
		if(solucion != null) listaPuntos = solucion.getPuntos();
		cmc.dibujarCamino(solucion.getListaPuntos());
		return listaPuntos;
	}
	
	private boolean chequearPunto(int x, int y, NodoPunto nodo) {
		if(x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {
			if(mapa.getDensidad(x, y) < 4 && !nodo.getPuntos().contains(new Punto(x,y))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean chequearNodos(ArrayList<NodoPunto> lista, NodoPunto nodo) {
		boolean esMejor = false;
		boolean iguales = false;
		for (NodoPunto aux : lista) {
			/*if((nodo.getP().y != aux.getP().y)  (nodo.getP().x != aux.getP().x)) {
				System.out.print("nodos diferentes");
				System.out.println("nodo A: " + nodo.getDensidad() + "b:" + aux.getDensidad());
				if(nodo.getDensidad() = aux.getDensidad()) {
					System.out.println("Nodo agrgado " + aux.getDensidad());
					esMejor = false;
				}
			}*/
			
			System.out.print(aux.getP().x + ":" + aux.getP().y);
			System.out.println(nodo.getP().x + ":" + nodo.getP().y);
			if((nodo.getP().y == aux.getP().y) && (nodo.getP().x == aux.getP().x)) {
				iguales = true;
				System.out.println("oa");
				if(nodo.getDensidad() <= aux.getDensidad()) {
					System.out.println("Funciona");
					esMejor = true;
				}
			}
		}
		if(iguales == false) {
			return true;
		}
		return esMejor;
	}
}
