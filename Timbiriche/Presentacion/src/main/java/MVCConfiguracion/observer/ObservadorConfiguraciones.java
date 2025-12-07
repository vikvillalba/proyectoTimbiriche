package MVCConfiguracion.observer;

import objetosPresentables.PartidaPresentable;

/**
 *
 * @author victoria
 */
public interface ObservadorConfiguraciones {
    //CU_VIKI
    void actualiarPartidaPresentable(PartidaPresentable configuraciones);
    void iniciarJuego();
    void mostrarVista();
}
