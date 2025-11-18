/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dto;

/**
 *
 * @author Jack Murrieta
 */
public class EventoPaqueteDTO<T> {

    private T contenido;
    private String tipoEvento;
    private String tipoContenido;

    public EventoPaqueteDTO(T contenido, String tipoEvento, String tipoContenido) {
        this.contenido = contenido;
        this.tipoEvento = tipoEvento;
        this.tipoContenido = tipoContenido;
    }

    public T getContenido() {
        return contenido;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

}
