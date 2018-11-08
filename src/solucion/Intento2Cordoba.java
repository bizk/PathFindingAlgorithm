package solucion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import cmc.CmcImple;
import graficos.Punto;
import mapa.MapaInfo;


public class Intento2Cordoba {
	private MapaInfo mapa;
	private CmcImple cmc;
	private Stopwatch cronometro;

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

		cronometro = new Stopwatch();

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
			mapa.enviarMensaje("Costo minimo: " + cronometro.elapsedTime() + "/segs");
		}
	}

	
	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		List<Punto> listaPuntos = new ArrayList<Punto>();
		ArrayList<NodoPunto> listaNodosSolucion = new ArrayList<NodoPunto>();
		
		//NUEVO
		ArrayList<NodoPunto> listaNodosRecorridos = new ArrayList<NodoPunto>();
		
		float densidad;
		
		NodoPunto aux = null;
		NodoPunto nodo = null;
		NodoPunto solucion = null;
		
		nodo = new NodoPunto(a, mapa.getDensidad(a), new ArrayList<Punto>(), null);
		listaNodosSolucion.add(nodo);
		listaNodosRecorridos.add(nodo);
		
		while(!listaNodosSolucion.isEmpty() && solucion == null) {
			if(nodo.getP().igual(b)) {
				solucion = nodo;
				break;
			} else {
				for(int i = nodo.getP().x - 1; i <= nodo.getP().x +1 ; i++) {
					for(int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
						if(i == b.x && j == b.y) {
							Punto punto = new Punto(i,j);
							if(esDiagonal(nodo.getP(), punto)) densidad = mapa.getDensidad(punto)*14 + nodo.getDensidad() + 14;
							else densidad = mapa.getDensidad(punto)*10 + nodo.getDensidad() + 10; 
							solucion = new NodoPunto(punto, densidad, nodo.getPuntos(), nodo);
						} else if (chequearPunto(i, j, nodo)) {
							Punto punto = new Punto(i,j);
							if(esDiagonal(nodo.getP(), punto)) densidad = mapa.getDensidad(punto)*14 + nodo.getDensidad() + 14;
							else densidad = mapa.getDensidad(punto)*10 + nodo.getDensidad() + 10; 
							
							aux = new NodoPunto(punto, densidad, nodo.getPuntos(), nodo);
						if(chequearNodos(listaNodosSolucion, aux)) {
							if(listaNodosSolucion.contains(aux)) {
								listaNodosSolucion.remove(listaNodosSolucion.indexOf(aux));
							}
							listaNodosSolucion.add(aux);
							cmc.dibujarCamino(aux.getPuntos());
						}
					}
				}
				
				//System.out.println("[" + nodo.getP().x + ":" + nodo.getP().y +"]");
			}
			listaNodosRecorridos.add(nodo);
			listaNodosSolucion.remove(nodo);
			
			nodo = elegirMejor(listaNodosSolucion, b);
		//	cmc.dibujarCamino(nodo.getPuntos());
		}
		}
		return obtenerCamino(solucion);
	}

	private boolean chequearNodos(ArrayList<NodoPunto> lista, NodoPunto nodo) {
		boolean esMejor = false;
		boolean iguales = false;
		Iterator<NodoPunto> tr = lista.iterator();
		NodoPunto aux = tr.next();
		while(!tr.hasNext()) {
			/*if((nodo.getP().y != aux.getP().y)  (nodo.getP().x != aux.getP().x)) {
				System.out.print("nodos diferentes");
				System.out.println("nodo A: " + nodo.getDensidad() + "b:" + aux.getDensidad());
				if(nodo.getDensidad() = aux.getDensidad()) {
					System.out.println("Nodo agrgado " + aux.getDensidad());
					esMejor = false;
				}
			}*/
			
			//System.out.print(aux.getP().x + ":" + aux.getP().y);
			//System.out.println(nodo.getP().x + ":" + nodo.getP().y);
			if(nodo.getP().igual(aux.getP())) {
				iguales = true;
				System.out.println("oa");
				if(nodo.getDensidad() <= aux.getDensidad()) {
					System.out.println("Funciona");
					esMejor = true;
				}
			}
			if(tr.hasNext()) aux = tr.next();
			else break;
		}
		if(iguales == false) {
			return true;
		}
		return esMejor;
	}
	
	private boolean esIgual(List<NodoPunto> lista,NodoPunto nodo) {
		boolean respuesta = false;
		for (NodoPunto aux : lista) {
			if(nodo.getP().igual(aux.getP()) && nodo.getPredecesor().getP().igual(aux.getPredecesor().getP())) {
				respuesta = true;
			}
		}
		return respuesta;
	}
	private void imprimirLista (List<NodoPunto> lista) {
		for(NodoPunto aux : lista) {
			System.out.print(aux.getP().x + ":" + aux.getP().y + " ");
		}
	}
	
	/*
	 * @Metodo: esDiagonal
	 * @Devuelve: true/false
	 * @Accion: Chequear si dado un punto es diagonal o no respecto a su predecesor.
	 * #Valores: Punto nodo y punto a chequear.
	 */
	private boolean esDiagonal(Punto nodo, Punto punto) { //Chequea las 4 posibles diagonales.
		if(punto.x == nodo.x -1 || punto.y == nodo.y - 1 || punto.x == nodo.x + 1 || punto.y == nodo.y + 1) return true;
		return false; //Si ninguna de las condiciones anteriores es diagonal entonces es falso
	}
	
	/*
	 * @Metodo: elegirMejor
	 * @Devuelve: NodoPunto
	 * @Accion: Elige el mejor punto de todos respecto a la distancia y la densidad acumulada.
	 * #Valores: lista de los nodos posibles y el punto destino.
	 */
	private NodoPunto elegirMejor(ArrayList<NodoPunto> listaNodos, Punto destino) {
		NodoPunto mejor = null; //Aca tomamos como mejor el primer valor
		float distanciaNodo, distanciaMejor;
		for(NodoPunto nodo : listaNodos) { //Recorremos todos los valores del nodo buscando el mejor y seleccionando aquel que tiene menor relacion costo/distancia
				if (mejor == null) mejor= nodo;
				if(esDiagonal(nodo.getPredecesor().getP(), nodo.getP())) 
					distanciaNodo = (Math.abs(nodo.getP().x - destino.x) + Math.abs(nodo.getP().y - destino.y))*14 + nodo.getDensidad();
				else 
					distanciaNodo = (Math.abs(nodo.getP().x - destino.x) + Math.abs(nodo.getP().y - destino.y))*10 + nodo.getDensidad();
				if(esDiagonal(mejor.getPredecesor().getP(), mejor.getP())) 
					distanciaMejor =  (Math.abs(mejor.getP().x - destino.x) + Math.abs(mejor.getP().y - destino.y))*14 + mejor.getDensidad();
				else 
					distanciaMejor = (Math.abs(mejor.getP().x - destino.x) + Math.abs(mejor.getP().y - destino.y))*10 + mejor.getDensidad();
			
				if (distanciaNodo < distanciaMejor) {
					mejor = nodo;
				} else if (distanciaNodo == distanciaMejor) {
					if(nodo.getDensidad() < mejor.getDensidad()) mejor = nodo;
				}
			}
		return mejor;
	}
	
	private List<Punto> obtenerCamino(NodoPunto solucion) {
		NodoPunto aux = solucion;
		List<Punto> caminito = new ArrayList<Punto>();
		while(aux.getPredecesor() != null) {
			caminito.add(aux.getP());
			aux = aux.getPredecesor();
		}
		return caminito;
	}
	
	private boolean chequearPunto(int x, int y, NodoPunto nodo) {
		if(x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {
			Punto pto = new Punto(x, y);
			if(mapa.getDensidad(pto) < 4 && !nodo.getPuntos().contains(pto)) {
				return true;
			}
		}
		return false;
	}
}
