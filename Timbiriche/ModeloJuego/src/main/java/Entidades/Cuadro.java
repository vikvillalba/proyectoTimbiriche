package Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Representación lógica de un cuadro dentro del tablero. compuesto de aristas,
 * vértices, color y jugador al que corresponde.
 *
 * @author victoria
 */
public class Cuadro {

    private List<Punto> aristas;
    private List<Linea> vertices;
    private ColorEnum color;
    private Jugador dueno; // el cuadro sabe de quien es para poder pintarlo 
    private String nombreJugador;

    /**
     * Construye un nuevo cuadro vacío.
     * sus aristas y vértices inician como vacías.
     */
    public Cuadro() {
        aristas = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public List<Punto> getAristas() {
        return aristas;

    }

    public List<Linea> getVertices() {
        return vertices;

    }

    public void agregarArista(Punto arista) {
        aristas.add(arista);
    }

    public void agregarVertice(Linea vertice) {
        vertices.add(vertice);
    }

    public Jugador getDueno() {
        return dueno;
    }

    public void setDueno(Jugador dueño) {
        this.dueno = dueño;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

}
