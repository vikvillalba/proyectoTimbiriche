/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloUnirsePartida.Fachada;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import Fachada.Partida;
import ModeloUnirsePartida.Emisor.EmisorUnirsePartida;
import ModeloUnirsePartida.UnirsePartida;
import SolicitudEntity.SolicitudUnirse;

/**
 *
 * @author Jack Murrieta
 */
public class UnirsePartidaFachada implements IUnirsePartidaFachada {

    private static UnirsePartidaFachada instancia;

    /**
     * Inicializa la instancia del Singleton con los parámetros necesarios. Este método debe ser llamado UNA sola vez desde el ensamblador.
     * @param emisor
     * @param unirsePartida
     * @return 
     */
    public static UnirsePartidaFachada getInstancia(
            EmisorUnirsePartida emisor,
            UnirsePartida unirsePartida
    ) {
        if (instancia == null) {
            instancia = new UnirsePartidaFachada(emisor, unirsePartida);
        }
        return instancia;
    }

    private final EmisorUnirsePartida emisor;
    private final UnirsePartida unirsePartida;

    private UnirsePartidaFachada(
            EmisorUnirsePartida emisor,
            UnirsePartida unirsePartida
    ) {
        this.emisor = emisor;
        this.unirsePartida = unirsePartida;
    }

    @Override
    public SolicitudUnirse crearSolicitud(JugadorSolicitanteDTO jugadorSolicitanteDTO) {
        return unirsePartida.crearSolicitud(jugadorSolicitanteDTO);
    }

    @Override
    public void cambiarEstadoSolicitud(SolicitudUnirse solicitud, boolean aceptada) {
        unirsePartida.cambiarEstadoSolicitud(solicitud, aceptada);
    }

    @Override
    public SolicitudUnirse getSolicitudActual() {
        return unirsePartida.getSolicitudActual();
    }

    @Override
    public void setJugadorSolicitante(JugadorSolicitanteDTO jugadorSolicitante) {
        unirsePartida.setJugadorSolicitante(jugadorSolicitante);
    }

    @Override
    public JugadorSolicitanteDTO getJugadorSolicitante() {
        return unirsePartida.getJugadorSolicitante();
    }

    @Override
    public JugadorConfigDTO getJugadorEnSala() {
        return unirsePartida.getJugadorEnSala();
    }

    @Override
    public void setPartida(Partida partida) {
        unirsePartida.setPartida(partida);
    }

    @Override
    public void enviarSolicitudSalaEspera(SolicitudUnirse solicitud) {
        emisor.enviarSolicitudSalaEspera(solicitud);
    }

    @Override
    public void enviarVotoSolicitud(SolicitudUnirse solicitud) {
        emisor.enviarVotoSolicitud(solicitud);
    }

    @Override
    public void SolicitarJugadorEnSala(JugadorSolicitanteDTO jugador) {
        emisor.SolicitarJugadorEnSala(jugador);
    }

    @Override
    public void suscribirseASalaEspera() {
        emisor.suscribirseASalaEspera();
    }
}
