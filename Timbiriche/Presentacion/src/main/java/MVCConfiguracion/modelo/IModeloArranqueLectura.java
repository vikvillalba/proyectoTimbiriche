package MVCConfiguracion.modelo;

import SolicitudEntity.SolicitudUnirse;
import objetosPresentables.PartidaPresentable;

/**
 *
 * @author victoria
 */
public interface IModeloArranqueLectura {

    public PartidaPresentable getConfiguracionesPartida();

    //obtener la solicitud creada en Configuraciones 
    public SolicitudUnirse obtenerSolicitud();

}
