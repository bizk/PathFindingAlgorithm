package solucion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

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
		
			while(iter.hasNext()) {
				b = iter.next();
				List<Punto> aux = expandirPuntosContiguos(a, b);
				if(aux.size() < minimo) {
					minimo = aux.size();
					listaPuntos = aux;
				}
			}
			cmc.dibujarCamino(listaPuntos,Color.red);
			mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos. ");
		}
	}

	
	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		List<Punto> listaPuntos = new ArrayList<Punto>();
		ArrayList<NodoPunto> listaNodosSolucion = new ArrayList<NodoPunto>();
		int densidad;
		
		NodoPunto aux = null;
		NodoPunto nodo = null;
		NodoPunto solucion = null;
		
		nodo = new NodoPunto(a, mapa.getDensidad(a), new ArrayList<Punto>(), null);
		listaNodosSolucion.add(nodo);
		
		while(!listaNodosSolucion.isEmpty() && (nodo.getP().x != b.x || nodo.getP().y != b.y) && solucion == null) {
			if(nodo.getP().x == b.x && nodo.getP().y == b.y) {
				solucion = nodo;
			} else {
				listaPuntos = nodo.getPuntos();
				for(int i = nodo.getP().x - 1; i <= nodo.getP().x +1 ; i++) {
					for(int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
						if(i == b.x && j == b.y) {
							System.out.println("Encontrado");
							solucion = nodo;
							break;
						} else if (chequearPunto(i, j, nodo) && !(i == nodo.getP().x && j == nodo.getP().y)) {
							Punto punto = new Punto(i,j);
							if(esDiagonal(nodo.getP(), punto)) densidad = mapa.getDensidad(i, j)*14 + nodo.getDensidad() + 14;
							else densidad = mapa.getDensidad(i, j)*10 + nodo.getDensidad() + 10; 
							aux = new NodoPunto(punto, densidad, listaPuntos, nodo);
							if(listaNodosSolucion.contains(aux)) {
								listaNodosSolucion.remove(listaNodosSolucion.indexOf(aux));
							}
							listaNodosSolucion.add(aux);
						}
					}
				}
				listaNodosSolucion.remove(listaNodosSolucion.indexOf(nodo));
				nodo = elegirMejor(listaNodosSolucion, b);
			}
			//cmc.dibujarCamino(nodo.getPuntos());
		}
		return obtenerCamino(solucion);
	}
	
	/*
	 * @Metodo: esDiagonal
	 * @Devuelve: true/false
	 * @Accion: Chequear si dado un punto es diagonal o no respecto a su predecesor.
	 * #Valores: Punto nodo y punto a chequear.
	 */
	private boolean esDiagonal(Punto nodo, Punto punto) { //Chequea las 4 posibles diagonales.
		if(punto.x == nodo.x -1 && punto.y == nodo.y - 1 ) return true;
		else if (punto.x == nodo.x - 1 && punto.y == nodo.y + 1) return true;
		else if (punto.x == nodo.x + 1 && punto.y == nodo.y + 1) return true;
		else if (punto.x == nodo.x + 1 && punto.y == nodo.y - 1) return true;
		return false; //Si ninguna de las condiciones anteriores es diagonal entonces es falso
	}
	
	/*
	 * @Metodo: elegirMejor
	 * @Devuelve: NodoPunto
	 * @Accion: Elige el mejor punto de todos respecto a la distancia y la densidad acumulada.
	 * #Valores: lista de los nodos posibles y el punto destino.
	 */
	private NodoPunto elegirMejor(ArrayList<NodoPunto> listaNodos, Punto destino) {
		NodoPunto mejor = listaNodos.get(0); //Aca tomamos como mejor el primer valor
		for(NodoPunto nodo : listaNodos) { //Recorremos todos los valores del nodo buscando el mejor y seleccionando aquel que tiene menor relacion costo/distancia
			if ((Math.abs(nodo.getP().x - destino.x) + Math.abs(nodo.getP().y - destino.y))*10 + nodo.getDensidad() < (Math.abs(mejor.getP().x - destino.x) + Math.abs(mejor.getP().y - destino.y))*10 + mejor.getDensidad()) {
				mejor = nodo;
			} else if ((Math.abs(nodo.getP().x - destino.x) + Math.abs(nodo.getP().y - destino.y))*10 + nodo.getDensidad() == (Math.abs(mejor.getP().x - destino.x) + Math.abs(mejor.getP().y - destino.y))*10 + mejor.getDensidad()) {
				if(nodo.getDensidad() < mejor.getDensidad()) mejor = nodo;
			}
		}
		return mejor;
	}
	
	private List<Punto> obtenerCamino(NodoPunto solucion) {
		NodoPunto aux = solucion;
		List<Punto> caminito = new ArrayList<Punto>();
		while(aux.getPredecesor() != null) {
			System.out.print("[" + aux.getP().x + ":" + aux.getP().y + "]");
			caminito.add(aux.getP());
			aux = aux.getPredecesor();
		}
		return caminito;
	}
	
	private boolean chequearPunto(int x, int y, NodoPunto nodo) {
		if(x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {
			Punto pto = new Punto(x, y);
			if(mapa.getDensidad(x, y) < 4 && !nodo.getPuntos().contains(pto)) {
				return true;
			}
		}
		return false;
	}
}
