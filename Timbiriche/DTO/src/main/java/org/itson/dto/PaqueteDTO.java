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
    public String host;
    //no se si se ocupa chat
    public int puerto;

    public PaqueteDTO(T contenido, String tipoEvento) {
        this.contenido = contenido;
        this.tipoEvento = tipoEvento;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public T getContenido() {
        return contenido;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }



}
