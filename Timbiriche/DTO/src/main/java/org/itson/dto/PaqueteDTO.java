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
    private T contenido;
    private String tipoEvento;
    
    public PaqueteDTO(T contenido, String tipoEvento) {
        this.contenido = contenido;
        this.tipoEvento = tipoEvento;
    }

    public T getContenido() {
        return contenido;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }
}
