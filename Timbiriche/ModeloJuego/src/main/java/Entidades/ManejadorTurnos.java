package Entidades;

import java.util.List;

/**
 *
 * @author victoria
 */
public class ManejadorTurnos {

    private List<Jugador> turnos;
    private Jugador jugadorEnTurno;

    public ManejadorTurnos(List<Jugador> jugadores) {
        this.turnos = jugadores;
        repartirTurnos();
    }

    private void repartirTurnos() {
        // asignar los turnos random
        jugadorEnTurno = turnos.get(0); // agarra el primer jugador 
    }
    
    public void actualizarTurno(){
        jugadorEnTurno = turnos.get(1);
    }
    
    public boolean isTurno(Jugador jugador){
        return jugador.equals(jugadorEnTurno);
    }

    public Jugador getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    public List<Jugador> getTurnos() {
        return this.turnos;
    }
    
    
    

}
