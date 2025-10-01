package Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victoria
 */
public class Cuadro {
    private List<Punto> aristas;
    private List<Linea> vertices;

   //  private Color color; hacer enum para colores
    private String nombreJugador;

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

    public void agregarArista(Punto arista){
        aristas.add(arista);
    }
    
    public void agregarVertice(Linea vertice){
        vertices.add(vertice);
    }

}
