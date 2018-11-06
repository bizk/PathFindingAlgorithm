package solucion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cmc.CmcImple;
import graficos.Punto;
import mapa.MapaInfo;


public class Intento2Cordoba {
	private MapaInfo mapa;
	private CmcImple cmc;
	private Stopwatch cronometro; //Cronometro
	
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
		
		//Inicialisamos el cronometro
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
		List<Punto> listaPuntos = new ArrayList<Punto>(); //Aca guardamos los puntos recorridos
		ArrayList<NodoPunto> listaNodosSolucion = new ArrayList<NodoPunto>(); //Aca obtenemos los nodos expandibles
		int costo; //Aca guardamos la densidad
		
		NodoPunto aux = null;
		NodoPunto nodo = null;
		NodoPunto solucion = null;
		
		nodo = new NodoPunto(a, mapa.getDensidad(a), new ArrayList<Punto>(), null); //Creamos un nodo en el origen
		listaNodosSolucion.add(nodo); //Agregamos a los expandibles el nodo origen 
		
		//Mientras nuestro nodo Solucion no este vacio y no lleguemos al destino.
		while(!listaNodosSolucion.isEmpty() && (nodo.getP().x != b.x || nodo.getP().y != b.y) && solucion == null) {
			if(nodo.getP().x == b.x && nodo.getP().y == b.y) { //Si encuentra el punto ya tenemos la solucion
				solucion = nodo;
			} else { //Si no obtenemos todos los puntos recorridos por el nodo en cuestion
				listaPuntos = nodo.getPuntos();
				//Expandimos a todos sus costados
				for(int i = nodo.getP().x - 1; i <= nodo.getP().x +1 ; i++) {
					for(int j = nodo.getP().y - 1; j <= nodo.getP().y + 1; j++) {
						if(i == b.x && j == b.y) { //Si lo encontramos encontramos la solucion
							System.out.println("Encontrado");
							solucion = nodo;
							break;
							//Si no, chequeamos que el punto sea accesible (Este dentro del mapa y no sea infranqueable)
						} else if (chequearPunto(i, j, nodo) && !(i == nodo.getP().x && j == nodo.getP().y)) {
							Punto punto = new Punto(i,j); //Creamos el punto en ese lugar.
							//Chequeamos que sea diagonal y le damos el respectivo costo
							if(esDiagonal(nodo.getP(), punto)) costo = mapa.getDensidad(i, j)*14 + nodo.getDensidad() + 14;
							else costo = mapa.getDensidad(i, j)*10 + nodo.getDensidad() + 10; 
							//Creamos el nodo
							aux = new NodoPunto(punto, costo, listaPuntos, nodo);
							
							//AGREGAMOS EL NODO A LOS POSIBLES RECORRIDOS
							listaNodosSolucion.add(aux);
						}
					}
				}
				
				//Como ya recorrimso el nodo lo sacamos de la lista expandible
				listaNodosSolucion.remove(listaNodosSolucion.indexOf(nodo));
				//Elegimos el mejor nodo posible
				nodo = elegirMejor(listaNodosSolucion, b);
			}
			cmc.dibujarCamino(nodo.getPuntos());
		}
		//Del nodo solucion volvemos para atras y agarramos la mejor solucion posible
		return obtenerCamino(solucion);
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
	 * 
	 * ACLARACION: Si multiplicamos las distancias obtenemos mejor rendimiento pero no el camino mas rapido, si no multiplicamos obtenemos backtracking
	 */
	private NodoPunto elegirMejor(ArrayList<NodoPunto> listaNodos, Punto destino) {
		NodoPunto mejor = listaNodos.get(0); //Aca tomamos como mejor el primer valor
		float distanciaNodo, distanciaMejor;
		for(NodoPunto nodo : listaNodos) { //Recorremos todos los valores del nodo buscando el mejor y seleccionando aquel que tiene menor relacion costo/distancia
			//Valores absolutos nos da la cantidad de casillas restantes para el nodo destino
			distanciaNodo = (Math.abs(nodo.getP().x - destino.x) + Math.abs(nodo.getP().y - destino.y))*10; 
			distanciaMejor = (Math.abs(mejor.getP().x - destino.x) + Math.abs(mejor.getP().y - destino.y))*10;
			if (distanciaNodo + nodo.getDensidad() < distanciaMejor + mejor.getDensidad()) {
				mejor = nodo;
			} else if (distanciaNodo + nodo.getDensidad() == distanciaMejor + mejor.getDensidad()) {
				if(nodo.getDensidad() < mejor.getDensidad()) mejor = nodo;
			}
		}
		return mejor;
	}
	
	/*
	 * @Metodo: obtenerCamino
	 * @Devuelve: List<Punto>
	 * @Accion: Devuelve una lissta de todos los puntos recorridos por un camino.
	 * #Valores: necesita un nodoPunto
	 */
	private List<Punto> obtenerCamino(NodoPunto solucion) {
		NodoPunto aux = solucion;
		List<Punto> caminito = new ArrayList<Punto>();
		//Mientras no llegamos al nodo origen obtenemos una lista de los puntos recorridos
		while(aux.getPredecesor() != null) {
			System.out.print("[" + aux.getP().x + ":" + aux.getP().y + "]");
			caminito.add(aux.getP());
			aux = aux.getPredecesor();
		}
		return caminito;
	}
	
	/*
	 * @Metodo: chequearPunto
	 * @Devuelve: Verdadero/Falso
	 * @Accion: Chequea que el punto este dentro del mapa, no sea infranqueable y no haya sido recorido antes.
	 * #Valores: punto x, y, y el nodo.
	 */
	private boolean chequearPunto(int x, int y, NodoPunto nodo) {
		if(x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) { //Chequeamos que el punto este en el mapa
			Punto pto = new Punto(x, y);
			if(mapa.getDensidad(pto) < 4 && !nodo.getPuntos().contains(pto)) { //Chequeamos que ese punto no haya sido recorrido ya ni que sea infranqueable
				return true;
			}
		}
		return false;
	}
}
