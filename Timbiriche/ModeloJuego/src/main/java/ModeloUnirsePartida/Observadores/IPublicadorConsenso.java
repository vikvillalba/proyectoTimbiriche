package ModeloUnirsePartida.Observadores;

/**
 * Interfaz para publicar notificaciones cuando el proceso de consenso ha finalizado. Implementada por clases que gestionan el proceso de consenso y necesitan notificar a observadores.
 *
 * @author Jack Murrieta
 */
public interface IPublicadorConsenso {

    /**
     * Agrega un notificador que será notificado cuando el consenso finalice.
     *
     * @param notificador El notificador a agregar
     */
    void agregarNotificadorConsenso(INotificadorConsenso notificador);

    /**
     * Notifica a todos los observadores que el proceso de consenso ha finalizado.
     *
     * @param aceptado true si el consenso fue aceptado, false si fue rechazado
     * @param tipoRechazo El tipo de rechazo si aplica, null si fue aceptado
     */
    void notificarConsensoFinalizado(boolean aceptado, String tipoRechazo);

}
