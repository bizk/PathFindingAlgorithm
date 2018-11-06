package solucion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;

import cmc.CmcImple;
import graficos.Punto;
import mapa.MapaInfo;

public class Intento3Cordoba {
	private MapaInfo mapa;
	private CmcImple cmc;
	private NodoPunto[][] matriz;
	
	
	public Intento3Cordoba(MapaInfo mapa, CmcImple cmc) {
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
		matriz = new NodoPunto[mapa.LARGO][mapa.ALTO];
		for (int i = 0; i < mapa.LARGO; i++) {
			for (int j = 0; j < mapa.ALTO; j++) {
				matriz[i][j] = null;
			}
		}
		
		NodoPunto nodo = new NodoPunto(a, mapa.getDensidad(a));
		matriz[a.x][a.y] = nodo;
		
		NodoPunto solucion = matriz[b.x][b.y];
		
		NodoPunto aux = null;
		
		PriorityQueue<NodoPunto> nodosExpandibles = new PriorityQueue<NodoPunto>();
		nodosExpandibles.add(nodo);
		
		int limite=-1;
		
		Punto pto;
		while(!nodosExpandibles.isEmpty()) {
			if(limite==-1 || nodo.getDensidad()<limite) {
			for (int i = nodo.getP().x - 1; i <= nodo.getP().x + 1; i++) {
				for (int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
					 pto = new Punto(i,j);
					 //if(esDiagonal(nodo.getP(), pto)) densidad = nodo.getDensidad() + mapa.getDensidad(pto)*14 + 14;
					 //else densidad = nodo.getDensidad() + mapa.getDensidad(pto)*10+ 10;
					 //System.out.println(densidad);
					 if(pto.igual(b)) {
						 //solucion = new NodoPunto(pto, nodo.getDensidad() + (mapa.getDensidad(pto)+1)*(esDiagonal(nodo.getP(),nodo.getPredecesor().getP())? 14:10), nodo.getPuntos(), nodo);
						 solucion = new NodoPunto(pto, nodo.getDensidad() + (mapa.getDensidad(pto)+1)*(esDiagonal(nodo.getP(),nodo.getPredecesor().getP())? 14:10), nodo);
						 limite= solucion.getDensidad();
					 } else {
						 if(chequearPunto(pto, nodo)) {
							// aux = new NodoPunto(pto, nodo.getDensidad() + (mapa.getDensidad(pto)+1)*(esDiagonal(nodo.getP(),pto)? 14:10), nodo.getPuntos(), nodo);
							 aux = new NodoPunto(pto, nodo.getDensidad() + (mapa.getDensidad(pto)+1)*(esDiagonal(nodo.getP(),pto)? 14:10), nodo);
							 aux.setDistancia(b);
							 if(matriz[i][j] == null) {
								 matriz[i][j] = aux;
								 System.out.println( matriz[i][j].toString());
								 nodosExpandibles.add(matriz[i][j]);
							 } else {
								 if(aux.relacionDistanciaDensidad() < matriz[pto.x][pto.y].relacionDistanciaDensidad()) {
									 matriz[i][j]=aux;
								 }
							 }
						 }
					 }
				}
			}
			}
			//System.out.println(nodosExpandibles.size());
			nodo = nodosExpandibles.poll();
			cmc.dibujarCamino(getCamino(nodo));
		}
		
		return getCamino(solucion);
	}
	
	private List<Punto> getCamino(NodoPunto solucion) {
		List<Punto> camino = new ArrayList<Punto>();
		NodoPunto aux = solucion;
		while (aux!=null) {
			camino.add(aux.getP());
			aux=aux.getPredecesor();
		}
		
		return camino;
	}

	private NodoPunto elegirMejorNodo(PriorityQueue<NodoPunto> nodosExpandibles) {
		NodoPunto mejor = null;
		for(NodoPunto aux: nodosExpandibles) {
			if(mejor == null) mejor = aux;
			if(aux.relacionDistanciaDensidad() < mejor.relacionDistanciaDensidad()) {
				mejor = aux;
			}else if(aux.relacionDistanciaDensidad() == mejor.relacionDistanciaDensidad()) {
				if(aux.getDensidad() < mejor.getDensidad()) mejor = aux;
			}
		}
		return mejor;
	}
	
	private NodoPunto elegirNodo(List<NodoPunto> nodos, Punto destino) {
		NodoPunto mejor = null;
		int costoAux, costoMejor;
		for(NodoPunto aux : nodos) {
			if(mejor == null) {
				mejor = aux;
				continue;
			}
			costoAux = (Math.abs(aux.getP().x - destino.x) + Math.abs(aux.getP().y - destino.y));
			costoMejor = (Math.abs(mejor.getP().x - destino.x) + Math.abs(mejor.getP().y - destino.y));
			if(costoAux < costoMejor) mejor = aux;
			else if (costoAux == costoMejor) {
				if(aux.getDensidad() < mejor.getDensidad()) mejor = aux;
			}
		}
		return mejor;
		
	}
	
	private boolean esDiagonal(Punto nodo, Punto punto) { //Chequea las 4 posibles diagonales.
		if(punto.x == nodo.x -1 || punto.y == nodo.y - 1 || punto.x == nodo.x + 1 || punto.y == nodo.y + 1) return true;
		return false; //Si ninguna de las condiciones anteriores es diagonal entonces es falso
	}
	
	private boolean chequearPunto(Punto pto, NodoPunto nodo) {
		int x = pto.x, y = pto.y;
		if(x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {
			if(mapa.getDensidad(pto) < 4) {
				return true;
			}
		}
		return false;
	}
}	

	