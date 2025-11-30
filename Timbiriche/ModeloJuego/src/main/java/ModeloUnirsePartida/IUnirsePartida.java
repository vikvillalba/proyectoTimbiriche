/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;

/**
 * Interfaz que define las operaciones para unirse a una partida.
 *
 * @author Jack Murrieta
 */
public interface IUnirsePartida {

    /**
     * Crea una solicitud para unirse a la partida.
     *
     * @return SolicitudUnirse creada
     */
    SolicitudUnirse crearSolicitud(JugadorSolicitanteDTO jugadorSolicitanteDTO);

    /**
     * Obtiene el jugador host (quien creó la partida).
     *
     * @return JugadorConfigDTO del host
     */
    JugadorConfigDTO obtenerJugadorHost();

    /**
     * Valida si hay espacio disponible en la partida (máximo 4 jugadores).
     *
     * @return true si hay espacio, false si está llena
     */
    boolean validarEspacioJugador();

    /**
     * Obtiene el estado actual de la partida.
     *
     * @return String que representa el estado ("EN_ESPERA", "INICIADA", "FINALIZADA")
     */
    String obtenerEstadoPartida();

    /**
     * Cambia el estado de una solicitud (aceptar o rechazar).
     *
     * @param solicitud La solicitud a modificar
     * @param aceptada true para aceptar, false para rechazar
     */
    void cambiarEstadoSolicitud(SolicitudUnirse solicitud, boolean aceptada);

    /**
     * Establece el jugador host de la partida.
     *
     * @param jugadorHost El jugador que será el host
     */
    void setJugadorHost(JugadorConfigDTO jugadorHost);

    /**
     * Establece el número actual de jugadores en la partida.
     *
     * @param numeroJugadores Cantidad de jugadores actuales
     */
    void setNumeroJugadores(int numeroJugadores);

    /**
     * Establece el estado de la partida.
     *
     * @param estado Estado de la partida
     */
    void setEstadoPartida(String estado);

    //envia la solicitud por el Iemisor
    public void enviarSolicitudJugadorHost();

    //Recibe el estado de la solicitud por el IReceptor
    public void recibirEstadoSolicitud();
}
