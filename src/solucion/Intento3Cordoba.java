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
		Stopwatch reloj = new Stopwatch();
		Punto a = null, b = null;	
		Iterator<Punto> iter = mapa.getPuntos().iterator();
		List<Punto> listaPuntos = null;
		int minimo = Integer.MAX_VALUE;
		if (iter.hasNext()) {
			a = iter.next();
		
			while(iter.hasNext()) {
				b = iter.next();
				List<Punto> aux = expandirPuntosContiguos(a, b);
				if(aux.size() < minimo) {
					minimo = aux.size();
					listaPuntos = aux;
				}
			}
			cmc.dibujarCamino(listaPuntos,Color.red);
			mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos" + reloj.elapsedTime() + " seg");
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
		NodoPunto aux, inicio = null;
		PriorityQueue<NodoPunto> nodosExpandibles = new PriorityQueue<NodoPunto>();
		nodosExpandibles.add(nodo);
		double limite=-1;
		Punto pto;
		
		while(!nodosExpandibles.isEmpty() && limite == -1) {
			if(limite==-1 || nodo.getDensidad()<limite) {
				for (int i = nodo.getP().x - 1; i <= nodo.getP().x + 1; i++) {
					for (int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
						 pto = new Punto(i,j);
						 if(pto.igual(b)) {
							 solucion = new NodoPunto(pto, nodo.getDensidad() + (mapa.getDensidad(pto)+1)+(esDiagonal(nodo.getP(),nodo.getPredecesor().getP())? Math.sqrt(2):1), nodo);
							 limite= solucion.getDensidad();
						 } else {
							 if(chequearPunto(pto, nodo)) {
								 aux = new NodoPunto(pto, nodo.getDensidad() + (mapa.getDensidad(pto)+1)+(esDiagonal(nodo.getP(),pto)? Math.sqrt(2):1), nodo);
								 aux.setDistancia(b); 
								 //cmc.dibujarCamino(getCamino(aux));
								 if(matriz[i][j] == null) {
									 matriz[i][j] = aux;
									 nodosExpandibles.add(aux);
								 } else {
									 if(aux.relacionDistanciaDensidad() < matriz[pto.x][pto.y].relacionDistanciaDensidad()) {
										 nodosExpandibles.remove(matriz[pto.x][pto.y]);
										 matriz[pto.x][pto.y] = aux;
										 nodosExpandibles.add(matriz[i][j]);
									 }
								 }
							 }
						 }
					}
				}
			}
			if(nodo.getP().x == a.x && nodo.getP().y == a.y) inicio = nodo;
			aux = nodo;
			nodo = nodosExpandibles.poll();
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
	
	private boolean esDiagonal(Punto nodo, Punto punto) { //Chequea las 4 posibles diagonales.
		if((punto.x == nodo.x -1 || punto.y == nodo.y - 1) && (punto.x == nodo.x + 1 || punto.y == nodo.y + 1)) return true;
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

	