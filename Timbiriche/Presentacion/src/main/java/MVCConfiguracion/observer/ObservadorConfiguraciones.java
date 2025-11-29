package MVCConfiguracion.observer;

import objetosPresentables.PartidaPresentable;

/**
 *
 * @author victoria
 */
public interface ObservadorConfiguraciones {
    void actualizar(PartidaPresentable configuraciones);
    void iniciarJuego();
    void mostrarVista();
}
