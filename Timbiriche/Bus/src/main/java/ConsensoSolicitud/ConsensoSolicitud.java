/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConsensoSolicitud;

/**
 * Clase que representa el consenso de una respuesta enviada por un jugador en sala a la solicitudUnirse. Almacena el contenido de la solicitud y los votos recibidos.
 *
 * @author Jack Murrieta
 */
public class ConsensoSolicitud {

    private Object solicitudContenido;
    private int jugadoresEnSala;
    private int votosAceptados;
    private int votosRechazados;

    public ConsensoSolicitud(Object solicitudContenido, int jugadoresEnSala, int votosAceptados, int votosRechazados) {
        this.solicitudContenido = solicitudContenido;
        this.jugadoresEnSala = jugadoresEnSala;
        this.votosAceptados = votosAceptados;
        this.votosRechazados = votosRechazados;
    }

    public ConsensoSolicitud() {
    }

    public Object getSolicitudContenido() {
        return solicitudContenido;
    }

    public void setSolicitudContenido(Object solicitudContenido) {
        this.solicitudContenido = solicitudContenido;
    }

    public int getJugadoresEnSala() {
        return jugadoresEnSala;
    }

    public void setJugadoresEnSala(int jugadoresEnSala) {
        this.jugadoresEnSala = jugadoresEnSala;
    }

    public int getVotosAceptados() {
        return votosAceptados;
    }

    public void setVotosAceptados(int votosAceptados) {
        this.votosAceptados = votosAceptados;
    }

    public int getVotosRechazados() {
        return votosRechazados;
    }

    public void setVotosRechazados(int votosRechazados) {
        this.votosRechazados = votosRechazados;
    }

}
