package Entidades;

import java.awt.Color;

/**
 *
 * @author victoria
 */
public class Linea {

    private Punto origen;
    private Punto destino;
    //private ColorEnum color; estoy cocinando trust me 
    private Jugador dueño;

    public Linea(Punto origen, Punto destino, Jugador dueño) {
        this.origen = origen;
        this.destino = destino;
        this.dueño = dueño;
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
    
    // regreas el color del dueño 
    public ColorEnum getColor() {
            return dueño != null ? dueño.getColor() : null;
        }
//
//    public void setColor(ColorEnum color) {
//        this.color = dueño.getColor();
//    }

    public Jugador getDueño() {
        return dueño;
    }

    public void setDueño(Jugador dueño) {
        this.dueño = dueño;
    }
}
