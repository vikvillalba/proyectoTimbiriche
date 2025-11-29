package objetosPresentables;

import java.util.List;

/**
 *
 * @author victoria
 */
public class PartidaPresentable {
    private List<JugadorConfig> jugadores;
    private TableroConfig tablero;

    public PartidaPresentable(List<JugadorConfig> jugadores, TableroConfig tablero) {
        this.jugadores = jugadores;
        this.tablero = tablero;
    }

    public List<JugadorConfig> getJugadores() {
        return jugadores;
    }

    public TableroConfig getTablero() {
        return tablero;
    }

    public void setJugadores(List<JugadorConfig> jugadores) {
        this.jugadores = jugadores;
    }

    public void setTablero(TableroConfig tablero) {
        this.tablero = tablero;
    }
    
    
}
