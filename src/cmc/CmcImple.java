package cmc;
/**
 * Subclase de CmcSC que implementa a CmcTDA
 * El m�todo run es invocado por el bot�n iniciar.
 * Hereda de CmcSC:
 * 		Objeto MapaInfo
 * 		M�todo dibujarCamino(List<Punto> camino)
 * Debe implementar:
 * 		M�todo run(MapaInfo mapa)
 */

import mapa.MapaInfo;
import solucion.Intento2Cordoba;
import tda.CmcSC;
public class CmcImple extends CmcSC {
	public void run(MapaInfo mapa) {
		this.mapa = mapa;
		new Intento2Cordoba(mapa, this);
	}
}
