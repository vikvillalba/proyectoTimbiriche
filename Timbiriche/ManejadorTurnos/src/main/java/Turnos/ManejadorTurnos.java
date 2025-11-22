package Turnos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private JugadorDTO jugadorEnTurno;
    private IEmisor emisor;
    private int indiceActual; // en que turno va

    public ManejadorTurnos() {
        this.indiceActual = 0;
        this.jugadorEnTurno = null;
    }

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    public List<JugadorDTO> getTurnos() {
        return this.turnos;
    }

    public void repartirTurnos(List<JugadorDTO> jugadores) {
        turnos = jugadores;
        Collections.shuffle(jugadores);

        jugadorEnTurno = turnos.get(indiceActual);
        jugadorEnTurno.setTurno(true);

        PaqueteDTO paqueteInicial = new PaqueteDTO(turnos, TipoEvento.INICIO_PARTIDA.toString());
        emisor.enviarCambio(paqueteInicial);
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
        notificarTurnoActualizado();
    }

    private void notificarTurnoActualizado() {
        if (emisor == null) {
        } else {
            PaqueteDTO paquete = new PaqueteDTO(jugadorEnTurno, TipoEvento.TURNO_ACTUALIZADO.toString());
            emisor.enviarCambio(paquete);
        }
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
                Gson gson = new Gson();
                List<JugadorDTO> jugadores = gson.fromJson(gson.toJson(paquete.getContenido()), new TypeToken<List<JugadorDTO>>() {}.getType());

                repartirTurnos(jugadores);
                System.out.println("Se repartieron los jugadores");
                break;

            case ACTUALIZAR_TURNO:
                System.out.println("[ManejadorTurnos] Jugador en turno antes: " + jugadorEnTurno.getId());
                actualizarTurno();
                System.out.println("[ManejadorTurnos] Jugador en turno ahora: " + jugadorEnTurno.getId());
                break;
        }
    }
}
