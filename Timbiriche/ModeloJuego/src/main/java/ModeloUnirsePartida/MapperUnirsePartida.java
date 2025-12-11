package ModeloUnirsePartida;

import DTO.JugadorConfigDTO;
import DTO.JugadorSolicitanteDTO;
import SolicitudEntity.SolicitudUnirse;
import java.util.Map;

/**
 * Clase utilitaria para mapear objetos recibidos desde el EventBus a DTOs específicos. Centraliza la lógica de conversión para evitar duplicación en los receptores.
 *
 * @author Jack Murrieta
 */
public class MapperUnirsePartida {

    private MapperUnirsePartida() {
    }

    /**
     * Mapea el contenido de un paquete a un objeto SolicitudUnirse. Maneja tanto objetos SolicitudUnirse directos como Maps con la estructura esperada.
     *
     * @param contenido El contenido a mapear (puede ser SolicitudUnirse o Map)
     * @return SolicitudUnirse mapeada o null si el contenido es inválido
     */
    public static SolicitudUnirse mapearSolicitud(Object contenido) {
        if (contenido == null) {
            System.err.println("[MapperUnirsePartida] mapearSolicitud: contenido NULO");
            return null;
        }

        // Si ya es un objeto SolicitudUnirse, devolverlo directamente
        if (contenido instanceof SolicitudUnirse) {
            return (SolicitudUnirse) contenido;
        }

        // Si no es un Map, no se puede mapear
        if (!(contenido instanceof Map)) {
            System.err.println("[MapperUnirsePartida] mapearSolicitud: contenido NO es Map ni SolicitudUnirse");
            return null;
        }

        Map<String, Object> map = (Map<String, Object>) contenido;
        SolicitudUnirse solicitud = new SolicitudUnirse();

        // Mapear jugadorSolicitante
        Object solicitanteObj = map.get("jugadorSolicitante");
        if (solicitanteObj instanceof Map) {
            JugadorSolicitanteDTO solicitante = mapearJugadorSolicitante((Map<String, Object>) solicitanteObj);
            solicitud.setJugadorSolicitante(solicitante);
        }

        // Mapear estado de solicitud
        Object estadoObj = map.get("solicitudEstado");
        if (estadoObj instanceof Boolean) {
            solicitud.setSolicitudEstado((Boolean) estadoObj);
        }

        // Mapear tipo de rechazo
        Object rechazoObj = map.get("tipoRechazo");
        if (rechazoObj instanceof String) {
            solicitud.setTipoRechazo((String) rechazoObj);
        }

        return solicitud;
    }

    /**
     * Mapea el contenido de un paquete a un objeto JugadorConfigDTO (host). Este método se usa cuando el EventBus responde con información del host.
     *
     * @param contenido El contenido a mapear (debe ser un Map)
     * @return JugadorConfigDTO mapeado o null si el contenido es inválido
     */
    public static JugadorConfigDTO mapearJugadorEnSala(Object contenido) {
        if (contenido == null) {
            System.err.println("[MapperUnirsePartida] mapearHost: contenido NULO");
            return null;
        }

        if (!(contenido instanceof Map)) {
            System.err.println("[MapperUnirsePartida] mapearHost: contenido NO es Map");
            return null;
        }

        Map<String, Object> hostMap = (Map<String, Object>) contenido;

        // Obtener la IP del host
        String hostIp = (String) hostMap.get("host");
        if (hostIp == null) {
            System.err.println("[MapperUnirsePartida] mapearHost: No se encontró 'host' en el Map");
            return null;
        }

        // Obtener el puerto
        int puerto = 0;
        Object puertoObj = hostMap.get("puerto");
        if (puertoObj instanceof Number) {
            puerto = ((Number) puertoObj).intValue();
        }

        // Crear y devolver el JugadorConfigDTO
        JugadorConfigDTO host = new JugadorConfigDTO();
        host.setIp(hostIp);
        host.setPuerto(puerto);

        return host;
    }

    /**
     * Mapea un Map a un JugadorSolicitanteDTO.
     *
     * @param jugadorSolicitante Map con los datos del jugador solicitante
     * @return JugadorSolicitanteDTO mapeado
     */
    private static JugadorSolicitanteDTO mapearJugadorSolicitante(Map<String, Object> jugadorSolicitante) {
        JugadorSolicitanteDTO solicitante = new JugadorSolicitanteDTO();

        solicitante.setIp((String) jugadorSolicitante.get("ip"));

        Object puertoObj = jugadorSolicitante.get("puerto");
        if (puertoObj instanceof Number) {
            solicitante.setPuerto(((Number) puertoObj).intValue());
        }

        return solicitante;
    }

}
