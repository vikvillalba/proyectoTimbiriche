package Entidades;

import java.util.ArrayList;
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
    
    public ManejadorTurnos(List<Jugador> jugadores) {
        this.turnos = jugadores;
        this.indiceActual = 0;
        asignarTurnos();
    }
    
    private void asignarTurnos() {
        // asignar los turnos random
        List<Jugador> copia = new ArrayList<>(turnos);
        List<Jugador> ordenAleatorio = new ArrayList<>();
        Random random = new Random();
        
        while (!copia.isEmpty()) {
            int indice = random.nextInt(copia.size());
            Jugador elegido = copia.remove(indice);
            ordenAleatorio.add(elegido);
        }
        this.turnos = ordenAleatorio;
    }
    
    public void actualizarTurno() {
        if (jugadorEnTurno != null) {
            jugadorEnTurno.setTurno(false);
        }
        
        indiceActual = (indiceActual + 1) % turnos.size();
        jugadorEnTurno = turnos.get(indiceActual);
        jugadorEnTurno.setTurno(true);
    }
    
    public boolean isTurno(Jugador jugador) {
        return jugador.equals(jugadorEnTurno);
    }
    
    public Jugador getJugadorEnTurno() {
        return jugadorEnTurno;
    }
    
    public List<Jugador> getTurnos() {
        return this.turnos;
    }
    
}
