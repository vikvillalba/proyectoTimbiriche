/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Turnos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author erika
 */
public class ManejadorTurnos {
    
    private List<JugadorDTO> turnos;
    private JugadorDTO jugadorEnTurno;
    private int indiceActual; // en que turno va

   // private ObservadorTurnos observadorTurnos;

    /**
     * Asigna los turnos para una partida.
     *
     * @param jugadores jugadores de la partida.
     *
     * mezcla a los jugadores de manera aleatoria y reparte los turnos de juego.
     */
    public ManejadorTurnos(List<JugadorDTO> jugadores) {
        this.turnos = new ArrayList<>(jugadores);
        this.indiceActual = -1; // inicia en -1 para que el primer actualizarTurno() ponga al jugador 0
        asignarTurnos();        // mezcla los jugadores de forma aleatoria
      //  actualizarTurno();      // setea el primer jugador en turno
    }

    private void asignarTurnos() {
        // revuelve lista de jugadores
        Collections.shuffle(turnos);
    }

//    public void agregarObservadorTurnos(ObservadorTurnos obs) {
//        this.observadorTurnos = obs;
//    }
//
//    public void actualizarTurno() {
//        if (jugadorEnTurno != null) {
//            jugadorEnTurno.setTurno(false);
//        }
//
//        indiceActual = (indiceActual + 1) % turnos.size();
//        jugadorEnTurno = turnos.get(indiceActual);
//        jugadorEnTurno.setTurno(true);
//
//        // Notificar al observador que cambió el turno
//        if (observadorTurnos != null) {
//            observadorTurnos.actualizar(turnos);
//        }
//    }

    /**
     * Determina si un jugador está en turno o no.
     * @param jugador específico del que se desea conocer si es su turno.
     * @return true si es su turno, false en caso contrario.
     */
    public boolean isTurno(JugadorDTO jugador) {
        return jugador != null && jugador.equals(jugadorEnTurno);
    }

    public JugadorDTO getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    public List<JugadorDTO> getTurnos() {
        return this.turnos;
    }
}
