/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloUnirsePartida.Emisor;

import DTO.JugadorSolicitanteDTO;
import ModeloUnirsePartida.UnirsePartida;
import SolicitudEntity.SolicitudUnirse;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

/**
 * Emisor Singleton para enviar solicitudes de unirse a partida. Implementa solo IUnirsePartidaEnvio
 *
 * @author Jack Murrieta
 *
 */
public class EmisorUnirsePartida implements IUnirsePartidaEnvio {

    private static EmisorUnirsePartida instancia;

    private final IEmisor emisor;
    private final int puertoOrigen;
    private final int puertoDestino;
    private final UnirsePartida unirsePartida;

    private EmisorUnirsePartida(IEmisor emisor, int puertoOrigen, int puertoDestino, UnirsePartida unirsePartida) {
        this.emisor = emisor;
        this.puertoOrigen = puertoOrigen;
        this.puertoDestino = puertoDestino;
        this.unirsePartida = unirsePartida;
    }

    public static void inicializar(IEmisor emisor, int puertoOrigen, int puertoDestino, UnirsePartida unirsePartida) {
        if (instancia == null) {
            instancia = new EmisorUnirsePartida(emisor, puertoOrigen, puertoDestino, unirsePartida);
        }
    }

    public static EmisorUnirsePartida getInstancia() {
        if (instancia == null) {
            throw new IllegalStateException("EmisorUnirsePartida no ha sido inicializado");
        }
        return instancia;
    }

    @Override
    public void enviarSolicitudSalaEspera(SolicitudUnirse solicitud) {
        // Enviar evento SOLICITAR_UNIRSE al EventBus
        // Este evento será recibido por TODOS los jugadores en sala de espera
        PaqueteDTO paquete = new PaqueteDTO(solicitud, "SOLICITAR_UNIRSE");

        paquete.setHost(solicitud.getJugadorSolicitante().getIp());
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(this.puertoDestino);

        System.out.println("[UnirsePartida] Enviando SOLICITAR_UNIRSE a todos los jugadores en sala");
        emisor.enviarCambio(paquete);
    }

    @Override
    public void enviarVotoSolicitud(SolicitudUnirse solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        String estadoVoto = solicitud.isSolicitudEstado() ? "ACEPTADO" : "RECHAZADO";
        System.out.println("[UnirsePartida] Enviando voto: " + estadoVoto);

        // Enviar evento VOTAR_SOLICITUD al EventBus
        PaqueteDTO paquete = new PaqueteDTO(solicitud, "VOTAR_SOLICITUD");

        paquete.setHost(unirsePartida.getJugadorEnSala() != null ? unirsePartida.getJugadorEnSala().getIp() : "localhost");
        paquete.setPuertoOrigen(this.puertoOrigen);
        paquete.setPuertoDestino(this.puertoDestino);

        emisor.enviarCambio(paquete);

    }

    @Override
    public void SolicitarJugadorEnSala(JugadorSolicitanteDTO jugador) {
        unirsePartida.setJugadorSolicitante(jugador);

        PaqueteDTO paquete = new PaqueteDTO();
        paquete.setContenido("SOLICITUD_JUGADOR_EN_SALA");
        paquete.setTipoEvento("OBTENER_JUGADOR_SALA");
        paquete.setHost(jugador.getIp());
        paquete.setPuertoOrigen(jugador.getPuerto());
        paquete.setPuertoDestino(puertoDestino);

        emisor.enviarCambio(paquete);
    }

    @Override
    public void suscribirseASalaEspera() {
        List<String> eventosNuevos = Arrays.asList(
                "EN_SALA_ESPERA",
                "SOLICITAR_UNIRSE",
                "CONSENSO_FINALIZADO"
        );

        PaqueteDTO registroEventBus = new PaqueteDTO(eventosNuevos, "INICIAR_CONEXION");
        registroEventBus.setHost(unirsePartida.getJugadorSolicitante() != null
                ? unirsePartida.getJugadorSolicitante().getIp()
                : "localhost");
        registroEventBus.setPuertoOrigen(this.puertoOrigen);
        registroEventBus.setPuertoDestino(this.puertoDestino);

        System.out.println("[UnirsePartida] Suscribiéndose a eventos de sala de espera: " + eventosNuevos);
        emisor.enviarCambio(registroEventBus);
    }

}
