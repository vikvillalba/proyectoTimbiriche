package MVCConfiguracion.controlador;

import MVCConfiguracion.modelo.IModeloArranqueEscritura;
import MVCConfiguracion.observer.ObservadorEventoInicio;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import java.util.List;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;
import org.itson.dto.JugadorDTO;

/**
 *
 * @author victoria
 */
public class ControladorArranque implements ObservadorEventoInicio{
    private ControladorPartida controladorPartida;
    private JugadorDTO sesion;
    private IModeloArranqueEscritura modelo;

    public ControladorArranque(ControladorPartida controladorPartida, JugadorDTO sesion, IModeloArranqueEscritura modelo) {
        this.controladorPartida = controladorPartida;
        this.sesion = sesion;
        this.modelo = modelo;
    }


    public void solicitarInicioJuego(JugadorConfig jugador){
        
    }
    
    public void iniciarConexion(List<JugadorConfig> jugadores, int altoTablero, int anchoTablero, JugadorConfig sesion){
    }
    
    public void confirmarInicioJuego(JugadorConfig jugador){
    }
    
    public void obtenerConfiguraciones(){
        modelo.solicitarConfiguraciones();
    }

    @Override
    public void iniciar(PartidaPresentable partida) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
