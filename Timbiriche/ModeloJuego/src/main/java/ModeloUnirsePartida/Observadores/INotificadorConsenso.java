package ModeloUnirsePartida.Observadores;

/**
 * Interfaz para notificar cuando el proceso de consenso ha finalizado. Implementada por las vistas que necesitan ser notificadas del resultado del consenso.
 *
 * @author Jack Murrieta
 */
public interface INotificadorConsenso {

    /**
     * Actualiza la vista cuando el proceso de consenso ha finalizado. Este método es llamado cuando: - Un jugador en sala rechaza la solicitud (rechazo inmediato) - Todos los jugadores votan y se alcanza consenso (aceptado o rechazado)
     *
     * @param aceptado true si el consenso fue aceptado, false si fue rechazado
     * @param tipoRechazo El tipo de rechazo si aplica, null si fue aceptado
     */
    void actualizarConsensoFinalizado(boolean aceptado, String tipoRechazo);

}
