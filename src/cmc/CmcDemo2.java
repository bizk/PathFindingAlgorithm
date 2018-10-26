package cmc;
import java.awt.Color;
/**
 * Obtiene la lista de los puntos marcados en la matriz (mapa)
 * Itera los mismos de la siguiente forma:
 * Obtiene los 2 primeros y expande los contiguos entre ambos.
 * Primero expande eje x, segundo expande el eje y.
 * Reitera la lista expandiendo el siguiente (siempre expandiendo de a pares)
 * El recorrido es secuencial (conforme al orden de marcado de los puntos en el mapa)
 * Invoca la método dibujar en cada iteración.
 * Al finalizar la iteración expande los contiguos entre el último y el primero de la lista.
 * Vuelve a Invocar la método dibujar para cerrar el ciclo.
 * No contempla las densidades definidas en la matriz (mapa)
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import graficos.Punto;
import mapa.MapaInfo;

public class CmcDemo2 {
	private MapaInfo mapa;
	private CmcImple cmc;
	
	public CmcDemo2(MapaInfo mapa, CmcImple cmc) {
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
			mapa.enviarMensaje("Camino minimo: " + listaPuntos.size() + " puntos");
		}
	}
	
	private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
		List<Punto> listaPuntos = new ArrayList<Punto>();
		if (a.x < b.x) {
			for(int x = a.x ; x < b.x; x++) {
				listaPuntos.add(new Punto(x, a.y));
			}
		} else {
			for(int x = a.x ; x > b.x; x--) {
				listaPuntos.add(new Punto(x, a.y));
			}
		}
		if (a.y < b.y) {
			for(int y = a.y ; y < b.y; y++) {
				listaPuntos.add(new Punto(b.x, y));
			}
		} else {
			for(int y = a.y ; y > b.y; y--) {
				listaPuntos.add(new Punto(b.x, y));
			}
		}
		cmc.dibujarCamino(listaPuntos);
		return listaPuntos;
	}
}
