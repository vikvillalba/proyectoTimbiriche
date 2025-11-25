package Turnos;

import com.google.gson.internal.LinkedTreeMap;
import org.itson.dto.JugadorDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author erika
 */
public class ManejadorTurnos implements IReceptor {

    private List<JugadorDTO> turnos = new ArrayList<>();
    ;
    private JugadorDTO jugadorEnTurno;
    private IEmisor emisor;
    private int indiceActual = 0; // en que turno va

    private String host;
    private int puertoOrigen;
    private int puertoDestino;

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    public List<JugadorDTO> getTurnos() {
        return this.turnos;
    }

    public void repartirTurnos(List<JugadorDTO> jugadores) {
        turnos = new ArrayList<>();

        for (JugadorDTO j : jugadores) {
            JugadorDTO copia = new JugadorDTO(j.getId());
            copia.setTurno(false);
            turnos.add(copia);
        }

        Collections.shuffle(turnos);

        jugadorEnTurno = turnos.get(indiceActual);
        jugadorEnTurno.setTurno(true);

        PaqueteDTO paqueteInicial = new PaqueteDTO(turnos, TipoEvento.INICIO_PARTIDA.toString());
        paqueteInicial.setHost(host);
        paqueteInicial.setPuertoOrigen(puertoOrigen);
        paqueteInicial.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(paqueteInicial);

        System.out.println("turnos repartidos");

    }

    public boolean isTurno(JugadorDTO jugador) {
        return jugador != null && jugador.equals(jugadorEnTurno);
    }

    public JugadorDTO getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    private void actualizarTurno() {
        for (JugadorDTO j : turnos) {
            j.setTurno(false);
        }

        indiceActual = (indiceActual + 1) % turnos.size();
        jugadorEnTurno = turnos.get(indiceActual);
        jugadorEnTurno.setTurno(true);

        PaqueteDTO paquete = new PaqueteDTO(jugadorEnTurno, "TURNO_ACTUALIZADO");
        paquete.setHost(host);
        paquete.setPuertoOrigen(puertoOrigen);
        paquete.setPuertoDestino(puertoDestino);

        emisor.enviarCambio(paquete);
    }

    public void SolicitarTurnos(PaqueteDTO paquete) {
        Object contenido = paquete.getContenido();
        List<?> lista = (List<?>) contenido;
        List<JugadorDTO> jugadores = new ArrayList<>();

        for (Object o : lista) {
            LinkedTreeMap<?, ?> map = (LinkedTreeMap<?, ?>) o;
            JugadorDTO j = new JugadorDTO((String) map.get("id"));
            Object turno = map.get("turno");
            if (turno != null) {
                j.setTurno((Boolean) turno);
            }
            jugadores.add(j);
        }
        repartirTurnos(jugadores);
        System.out.println("Se repartieron los jugadores");
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        System.out.println("[ManejadorTurnos] evento recibido: " + paquete.getTipoEvento());
        TipoEvento tipo;

        try {
            tipo = TipoEvento.valueOf(paquete.getTipoEvento());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }
        switch (tipo) {

            case SOLICITAR_TURNOS:
                if (paquete.getTipoEvento().equals("SOLICITAR_TURNOS")) {
                    SolicitarTurnos(paquete);
                    break;
                }

            case ACTUALIZAR_TURNO:
                System.out.println("[ManejadorTurnos] Jugador en turno antes: " + jugadorEnTurno.getId());
                actualizarTurno();
                System.out.println("[ManejadorTurnos] Jugador en turno ahora: " + jugadorEnTurno.getId());
                break;
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

}
