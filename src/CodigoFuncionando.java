import java.util.ArrayList;
import java.util.List;

import graficos.Punto;

public class CodigoFuncionando {

}

private List<Punto> expandirPuntosContiguos(Punto a, Punto b) {
	List<Punto> listaPuntos = new ArrayList<Punto>();
	int x = a.x;
	int y = a.y; 

//	List<NodoPunto> nodos = new ArrayList<NodoPunto>();
//	NodoPunto aux = new NodoPunto(a); //Creamos el nodo auxiliar en A
	
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
				}
			} else if (x > b.x && (mapa.getDensidad(x - 1, y) >= 4 || !listaPuntos.contains(new Punto(x - 1, y)))) {
				if(mapa.getDensidad(x, y + 1) < 4  && !listaPuntos.contains(new Punto(x, y + 1))) {
					y++;
				} else if (mapa.getDensidad(x, y - 1) < 4  && !listaPuntos.contains(new Punto(x, y - 1))) {
					y--;
				}
			} else if (y < b.y && (mapa.getDensidad(x, y + 1) >= 4 || listaPuntos.contains(new Punto(x, y + 1)))) {
				if(mapa.getDensidad(x + 1, y) < 4 && !listaPuntos.contains(new Punto(x + 1, y))) {
					x++;
				} else if (mapa.getDensidad(x - 1, y) < 4 && !listaPuntos.contains(new Punto(x - 1, y))) {
					x--;
				}
			} else if (y > b.y && (mapa.getDensidad(x, y - 1) >= 4 || !listaPuntos.contains(new Punto(x, y - 1)))) {
				if(mapa.getDensidad(x + 1, y) < 4 && !listaPuntos.contains(new Punto(x + 1, y))) {
					x++;
				} else if (mapa.getDensidad(x - 1, y) < 4 && !listaPuntos.contains(new Punto(x - 1, y))) {
					x--;
				} 
			} 
		}
		listaPuntos.add(new Punto(x, y));
		auxX = x;
		auxY = y;
	}
	
	
	
	/*		while((b.x != x || b.y != y) && x >= 0 && x < mapa.LARGO && y >= 0 && y < mapa.ALTO) {			
			if(x < b.x && cumple(x + 1,y,listaPuntos) && y < b.y && cumple(x,y + 1,listaPuntos)) {
				x++;
				y++;
			} else if (x < b.x && cumple(x + 1,y,listaPuntos) && y > b.y && cumple(x,y - 1,listaPuntos)) {
				x++;
				y--;
			} else if (x > b.x && x > b.x && cumple(x - 1,y,listaPuntos) && y < b.y && cumple(x,y + 1,listaPuntos)) {
				x--;
				y++;
			} else if (x > b.x && x > b.x && cumple(x - 1,y,listaPuntos) && y > b.y && cumple(x,y - 1,listaPuntos)) {
				x--;
				y--;
			} else {
				if(x < b.x) {
					if(cumple(x + 1,y,listaPuntos))x++;
					else {
						
					}
				}
				else if (x > b.x && cumple(x - 1,y,listaPuntos)) x--;
				else if(y < b.y && cumple(x,y + 1,listaPuntos)) y++;
				else if (y > b.y && cumple(x,y - 1,listaPuntos))y--;
				else {
					if (x < b.x && !cumple(x + 1, y, listaPuntos)) {
						if(cumple(x,y + 1,listaPuntos)) y++;
						else if (cumple(x,y -1,listaPuntos)) y--;
						else if (cumple(x-1, y, listaPuntos)) x--;
						else break;
					} else if (x > b.x && !cumple(x - 1, y, listaPuntos)) {
						if(cumple(x,y + 1,listaPuntos)) y++;
						else if (cumple(x,y-1,listaPuntos)) y--;
						else if (cumple(x+1, y, listaPuntos)) x++;
						else break;
					} else if (y > b.y && !cumple(x, y - 1, listaPuntos)) {
						if(cumple(x + 1,y,listaPuntos)) x++;
						else if (cumple(x - 1,y,listaPuntos)) x--;
						else if (cumple(x, y+1, listaPuntos)) y++;
						else break;
					} else if (y < b.y && !cumple(x, y + 1, listaPuntos)) {
						if(cumple(x + 1,y,listaPuntos)) x++;
						else if (cumple(x - 1,y,listaPuntos)) x--;
						else if (cumple(x, y - 1, listaPuntos)) y--;
						else break;
					}
				}
			}
			
			listaPuntos.add(new Punto(x, y));
		} */