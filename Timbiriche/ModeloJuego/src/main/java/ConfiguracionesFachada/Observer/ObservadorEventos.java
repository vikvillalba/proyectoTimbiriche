package ConfiguracionesFachada.Observer;

import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public interface ObservadorEventos<T> {
    void actualizar(T cambio);
    void iniciarJuego(PartidaDTO partida);
}
