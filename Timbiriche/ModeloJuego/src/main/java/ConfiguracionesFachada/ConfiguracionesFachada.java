/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ConfiguracionesFachada;

import java.util.List;
import org.itson.dto.JugadorNuevoDTO;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Maryr
 */
public interface ConfiguracionesFachada {

    void registrarJugador(JugadorNuevoDTO jugador);

    void solicitarElementosUso();

    void recibirUsados(PaqueteDTO paquete);

    //solo para prueba
    void enviarElementos(List<String> usados);

    void recibirJugador(PaqueteDTO paquete);

}
