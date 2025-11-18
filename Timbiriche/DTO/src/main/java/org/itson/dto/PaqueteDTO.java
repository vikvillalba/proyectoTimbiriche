/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dto;

/**
 *
 * @author erika
 */
public class PaqueteDTO<T> {

    public T contenido;
    public String tipoEvento;
    public String tipoContenido;

    public PaqueteDTO(T contenido, String tipoEvento) {
        this.contenido = contenido;
        this.tipoEvento = tipoEvento;
    }

    public PaqueteDTO(T contenido, String tipoEvento, String tipoContenido) {
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

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

}
