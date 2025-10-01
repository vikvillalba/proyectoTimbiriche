package Entidades;

import java.util.Objects;

/**
 *
 * @author victoria
 */
public class Punto {
    // coordenadas

    private int x;
    private int y;

    // referencia a sus adyacentes
    private Punto izquierda;
    private Punto derecha;
    private Punto arriba;
    private Punto abajo;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;

        // inicializar adyacentes en null
        this.izquierda = null;
        this.derecha = null;
        this.arriba = null;
        this.abajo = null;
    }

    public Punto getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Punto izquierda) {
        this.izquierda = izquierda;
    }

    public Punto getDerecha() {
        return derecha;
    }

    public void setDerecha(Punto derecha) {
        this.derecha = derecha;
    }

    public Punto getArriba() {
        return arriba;
    }

    public void setArriba(Punto arriba) {
        this.arriba = arriba;
    }

    public Punto getAbajo() {
        return abajo;
    }

    public void setAbajo(Punto abajo) {
        this.abajo = abajo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Punto otro = (Punto) obj;
        return this.x == otro.x && this.y == otro.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
