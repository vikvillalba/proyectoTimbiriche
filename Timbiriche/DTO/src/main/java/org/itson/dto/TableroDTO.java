
package org.itson.dto;

/**
 *
 * @author victoria
 */
public class TableroDTO {
    private int alto;
    private int ancho;

    public TableroDTO(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public TableroDTO() {
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    
    
}
