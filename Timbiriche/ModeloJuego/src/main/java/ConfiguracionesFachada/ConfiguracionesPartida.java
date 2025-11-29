package ConfiguracionesFachada;

import Entidades.Jugador;
import Observer.ObservableEventos;
import Observer.ObservadorEventos;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PartidaDTO;
import org.itson.dto.TableroDTO;

/**
 *
 * @author victoria
 */
public class ConfiguracionesPartida implements ConfiguracionesFachada, ObservableEventos {
    private IEmisor emisor;
    private String host;
    private int puerto;
    private ObservadorEventos observadorEventos;

    @Override
    public PartidaDTO obtenerConfiguraciones() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void iniciarConexion(List<Jugador> jugadores, TableroDTO tablero, Jugador sesion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void solicitarInicioJuego(Jugador jugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void confirmarIncioJuego(Jugador jugador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void notificarEventoRecibido(Object evento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarObservadorEventos(ObservadorEventos ob) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
