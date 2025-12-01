package org.itson.dto;

import java.util.UUID;

/**
 *
 * @author erika
 */
public class PaqueteDTO<T> {

    private T contenido;
    private String id;
    private String tipoEvento;
    private String host;
    private int puertoOrigen;
    private int puertoDestino;

    public PaqueteDTO(T contenido, String tipoEvento) {
        this.contenido = contenido;
        this.tipoEvento = tipoEvento;

    }

    public PaqueteDTO(T contenido, String tipoEvento, String host, int puertoOrigen, int puertoDestino) {
        this.contenido = contenido;
        this.tipoEvento = tipoEvento;
        this.host = host;
        this.puertoOrigen = puertoOrigen;
        this.puertoDestino = puertoDestino;
        this.id = UUID.randomUUID().toString();  //crea identificador para que el paquete no se duplique
    }

    public T getContenido() {
        return contenido;
    }

    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuertoOrigen() {
        return puertoOrigen;
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public int getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public String getId() {
        return id;
    }

    
}
