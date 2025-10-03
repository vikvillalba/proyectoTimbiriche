package Entidades;

/**
 * Clase que representa una linea del tablero de juego.
 *
 * @author victoria
 */
public class Linea {

    private Punto origen;
    private Punto destino;
    private Jugador dueño;

    /**
     * Construye una nueva línea.
     * @param origen donde inicia la linea
     * @param destino donde termina la linea
     * @param dueño jugador al que pertenece la linea
     */
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

    public ColorEnum getColor() {
        return dueño != null ? dueño.getColor() : null;
    }

    public Jugador getDueño() {
        return dueño;
    }

    public void setDueño(Jugador dueño) {
        this.dueño = dueño;
    }
}
