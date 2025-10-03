package objetosPresentables;

import java.awt.Color;

/**
 * representación presentable de una línea del tablero.
 * @author victoria
 */
public class LineaPresentable {
    private PuntoPresentable origen;
    private PuntoPresentable destino;
    private Color color;

    public LineaPresentable(PuntoPresentable origen, PuntoPresentable destino, Color color) {
        this.origen = origen;
        this.destino = destino;
        this.color = color;
    }

    public PuntoPresentable getOrigen() {
        return origen;
    }

    public PuntoPresentable getDestino() {
        return destino;
    }

    public Color getColor() {
        return color;
    }
    
    
}
