/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosPresentables;

import java.awt.Color;
import java.util.List;

/**
 * representa visualmente los cuadros en el tablero para poderlos llenar del color de cada jugador 
 * @author erika
 */
public class CuadroPresentable {
    private List<PuntoPresentable> vertices;
    private Color color;
    private String dueno;

    public CuadroPresentable(List<PuntoPresentable> vertices, Color color) {
        this.vertices = vertices;
        this.color = color;
    }

    public List<PuntoPresentable> getVertices() {
        return vertices;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    
    
    
    
    
    
}
