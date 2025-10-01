package objetosPresentables;

import java.util.List;

/**
 *
 * @author victoria
 */
public class TableroPresentable {
    private List<PuntoPresentable> puntos;
    private List<LineaPresentable> lineas;
    private int alto;
    private int ancho;

    public TableroPresentable(List<PuntoPresentable> puntos, List<LineaPresentable> lineas, int alto, int ancho) {
        this.puntos = puntos;
        this.lineas = lineas;
        this.alto = alto;
        this.ancho = ancho;
    }

    public List<PuntoPresentable> getPuntos() {
        return puntos;
    }

    public List<LineaPresentable> getLineas() {
        return lineas;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }


    
}
