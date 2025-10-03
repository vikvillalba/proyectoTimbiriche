package objetosPresentables;

import java.util.List;

/**
 * Representación presentable del tablero de juego.
 * @author victoria
 */
public class TableroPresentable {
    private List<PuntoPresentable> puntos;
    private List<LineaPresentable> lineas;
    private List <CuadroPresentable> cuadros; //tablero tiene los cuadro para poderlo llenar
    private int alto;
    private int ancho;

    public TableroPresentable(List<PuntoPresentable> puntos, List<LineaPresentable> lineas, List<CuadroPresentable> cuadros, int alto, int ancho) {
        this.puntos = puntos;
        this.lineas = lineas;
        this.cuadros = cuadros;
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

    public List<CuadroPresentable> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<CuadroPresentable> cuadros) {
        this.cuadros = cuadros;
    }
    


    
}
