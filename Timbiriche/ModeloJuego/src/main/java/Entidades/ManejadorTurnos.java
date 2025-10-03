package Entidades;

import Observer.ObservadorTurnos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author victoria
 */
public class ManejadorTurnos {
    
    private List<Jugador> turnos;
    private Jugador jugadorEnTurno;
    private int indiceActual;
    
    private ObservadorTurnos observadorTurnos;
    
    public ManejadorTurnos(List<Jugador> jugadores) {
        this.turnos = new ArrayList<>(jugadores);
        this.indiceActual = -1; // inicia en -1 para que el primer actualizarTurno() ponga al jugador 0
        asignarTurnos();        // mezcla los jugadores de forma aleatoria
        actualizarTurno();      // setea el primer jugador en turno
    }
    
   private void asignarTurnos() {
        // barajar la lista de jugadores
        Collections.shuffle(turnos);
    }

    public void agregarObservadorTurnos(ObservadorTurnos obs) {
        this.observadorTurnos = obs;
    }
    
    public void actualizarTurno() {
        if (jugadorEnTurno != null) {
            jugadorEnTurno.setTurno(false);
        }

        indiceActual = (indiceActual + 1) % turnos.size();
        jugadorEnTurno = turnos.get(indiceActual);
        jugadorEnTurno.setTurno(true);

        // Notificar al observador que cambió el turno
        if (observadorTurnos != null) {
            observadorTurnos.actualizar(turnos);
        }
    }
    
    public boolean isTurno(Jugador jugador) {
        return jugador != null && jugador.equals(jugadorEnTurno);
    }
    
    public Jugador getJugadorEnTurno() {
        return jugadorEnTurno;
    }
    
    public List<Jugador> getTurnos() {
        return this.turnos;
    }
    
}
