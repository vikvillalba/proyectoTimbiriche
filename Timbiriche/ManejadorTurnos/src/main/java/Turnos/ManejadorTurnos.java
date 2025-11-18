package Turnos;

import IEmisorBus.IEmisorBus;
import InterfazServicio.IServicio;
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
public class ManejadorTurnos implements IReceptor, IServicio {

    private List<JugadorDTO> turnos = new ArrayList<>();
    private JugadorDTO jugadorEnTurno;
    private final IEmisor emisor;
    private int indiceActual; // en que turno va
    private final String host;
    private final int puerto;

    public ManejadorTurnos(IEmisor emisor, String host, int puerto) {
        this.emisor = emisor;
        this.host = host;
        this.puerto = puerto;
    }

    public List<JugadorDTO> getTurnos() {
        return this.turnos;
    }

    public void repartirTurnos(List<JugadorDTO> jugadores) {
        turnos = new ArrayList<>(jugadores);
        Collections.shuffle(turnos);
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
        if (emisor != null) {
            
            //Logica para cambiar de DTO a entidad
            
            PaqueteDTO paquete = new PaqueteDTO(jugadorEnTurno, "TURNO_ACTUALIZADO");
            emisor.enviarCambio(paquete);
        }
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {
        System.out.println("[ManejadorTurnos] evento recibido: " + paquete.getTipoEvento());
        switch (paquete.getTipoEvento()) {

            case "INICIO_PARTIDA":
                System.out.println(paquete.toString());
                
                //Lógica para cambiar de entidad a DTO
                
                List<JugadorDTO> jugadores = (List<JugadorDTO>) paquete.getContenido();              
                
                repartirTurnos(jugadores);
                indiceActual = -1;
                actualizarTurno();
                
                
            break;

            case "ACTUALIZAR_TURNO":
                actualizarTurno();
            break;
        }
    }

    @Override
    public int getPuerto() {
        return puerto;
    }

    @Override
    public String getHost() {
        return host;
    }
}
