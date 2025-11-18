/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Jack Murrieta
 */
public class PaqueteSuscripcionDTO {

    private String comando;       // REGISTRAR | DESREGISTRR
    private String evento;        // El evento al que se quiere suscribir
    private String host;          // host del emisor
    private int puerto;           // Puerto para recibir notificaciones

    public PaqueteSuscripcionDTO(String comando, String evento, String host, int puerto) {
        this.comando = comando;
        this.evento = evento;
        this.host = host;
        this.puerto = puerto;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

}
