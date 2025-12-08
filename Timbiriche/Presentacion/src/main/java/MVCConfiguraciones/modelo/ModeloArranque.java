package MVCConfiguraciones.modelo;

import Fachada.ConfiguracionesFachada;
import MVCConfiguraciones.observer.Observable;
import MVCConfiguraciones.observer.Observador;
import configuracionesPartida.ConfiguracionesPartida;
import org.itson.dto.ConfiguracionesDTO;
import org.itson.observadores.ObservableConfiguracion;
import org.itson.observadores.ObservadorConfiguracion;

/**
 *Clase que representa el Modelo de Arranque del sistema.
 * Es responsable de gestionar y persistir la configuración inicial (IModeloArranque_Escritura),
 * leerla (IModeloArranque_Lectura), y actuar como el Sujeto Observable
 * para notificar al Modelo del Juego cuando la configuración está lista.
 * @author victoria
 */
public class ModeloArranque implements IModeloArranqueExcritura, IModeloArranqueLectura, ObservableConfiguracion{
    
    // El observador es de tipo ObservadorConfiguracion
    private ObservadorConfiguracion observadorConfiguraciones;
    
    // Referencia a la fachada para la gestión interna o persistencia de la configuración.
    private ConfiguracionesFachada configuracion;
    
    // Atributo interno para almacenar la última configuración guardada o leída.
    private ConfiguracionesDTO ultimaConfiguracionGuardada;
    
    // Constructor 
    public ModeloArranque(ConfiguracionesFachada configuracion) {
        this.configuracion = configuracion;
        this.ultimaConfiguracionGuardada = null; // Inicialmente nulo
    }
    
    /**
     * Guarda la configuración en la fachada del modelo de juego
     * * @param configuracion El DTO que contiene los datos de la configuración a guardar.
     */
    @Override
    public void guardarConfiguracion(ConfiguracionesDTO cofiguracion) {
        this.ultimaConfiguracionGuardada = cofiguracion; // Almacena para lectura futura
 
        configuracion.setConfiguraciones(cofiguracion);
    }
    
    
    /**
     * Este método es llamado por el ControladorArranque para finalizar el proceso 
     * de configuración y notificar a los suscriptores (ModeloJuego).
     * * @param configuracion El DTO a publicar.
     */
    public void publicarConfiguracion(ConfiguracionesDTO configuracion) {
        this.notificar(configuracion);
    }
    
    
    /**
     * Registra un objeto ObservadorConfiguracion (normalmente el ModeloJuego).
     * Nota: En el diagrama, la relación es uno a uno, simplificando la lista de observadores.
     * * @param observador El observador a registrar.
     */
    @Override
    public void agregar(ObservadorConfiguracion observadorConfiguracion) {
        this.observadorConfiguraciones = observadorConfiguracion;
    }

    /**
     * Elimina un observador.
     * @param observadorConfiguracion El observador a eliminar.
     */
    @Override
    public void eliminar(ObservadorConfiguracion observadorConfiguracion) {
        if (this.observadorConfiguraciones == observadorConfiguracion) {
            this.observadorConfiguraciones = null;
        }
    }

    /**
     * Notifica a los observadores registrados sobre el cambio de configuración.
     * * @param configuracion El DTO con la configuración final.
     */
    @Override
    public void notificar(ConfiguracionesDTO configuracion) {
        if (observadorConfiguraciones != null) {
            observadorConfiguraciones.cambioConfiguracion(configuracion);
        }
    }
    
    /**
     * Recupera la configuración que fue guardada previamente.
     * @return El ConfiguracionesDTO guardado.
     */
    @Override
    public ConfiguracionesDTO leerConfiguracion() {
        return this.ultimaConfiguracionGuardada;
    }

  
    
}
