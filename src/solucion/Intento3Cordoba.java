package solucion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
		
		List<NodoPunto> nodosExpandibles = new ArrayList<NodoPunto>();
		nodosExpandibles.add(nodo);
		
		int densidad;
		
		Punto pto;
		while(nodosExpandibles != null && solucion == null) {
			for (int i = nodo.getP().x - 1; i <= nodo.getP().x + 1; i++) {
				for (int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
					 pto = new Punto(i,j);
					 //if(esDiagonal(nodo.getP(), pto)) densidad = nodo.getDensidad() + mapa.getDensidad(pto)*14 + 14;
					 //else densidad = nodo.getDensidad() + mapa.getDensidad(pto)*10+ 10;
					 //System.out.println(densidad);
					 if(pto.igual(b)) {
						 solucion = new NodoPunto(pto, nodo.getDensidad() + mapa.getDensidad(pto)*10+ 10, nodo.getPuntos(), nodo);
					 } else {
						 if(chequearPunto(pto, nodo)) {
							 aux = new NodoPunto(pto, nodo.getDensidad() + mapa.getDensidad(pto)*10+ 10, nodo.getPuntos(), nodo);
							 aux.setDistancia(b);
							 if(matriz[i][j] == null) {
								 matriz[i][j] = aux;
								 System.out.println( matriz[i][j].toString());
								 nodosExpandibles.add(matriz[i][j]);
							 } else {
								 if(aux.relacionDistanciaDensidad() < matriz[pto.x][pto.y].relacionDistanciaDensidad()) {
									 matriz[i][j] = aux;
								 }
							 }
						 }
					 }
				}
			}
			nodosExpandibles.remove(nodo);
			//System.out.println(nodosExpandibles.size());
			nodo = elegirMejorNodo(nodosExpandibles);
		}
		
		return solucion.getPuntos();
	}
	
	private NodoPunto elegirMejorNodo(List<NodoPunto> nodos) {
		NodoPunto mejor = null;
		for(NodoPunto aux: nodos) {
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
			if(mapa.getDensidad(pto) < 4 && !nodo.getPuntos().contains(pto)) {
				return true;
			}
		}
		return false;
	}
}	

	