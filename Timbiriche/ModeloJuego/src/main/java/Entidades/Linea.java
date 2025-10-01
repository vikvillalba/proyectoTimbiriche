package Entidades;

import java.awt.Color;

/**
 *
 * @author victoria
 */
public class Linea {

    private Punto origen;
    private Punto destino;
    private ColorEnum color;
    private Jugador dueño;

    public Linea(Punto origen, Punto destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public Punto getOrigen() {
        return origen;
    }

    public void setOrigen(Punto origen) {
        this.origen = origen;
    }

    public Punto getDestino() {
        return destino;
    }

    public void setDestino(Punto destino) {
        this.destino = destino;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = dueño.getColor();
    }
}
