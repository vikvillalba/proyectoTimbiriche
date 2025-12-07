package EventBus;

import Servicio.Servicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.itson.componenteemisor.IEmisor;

import org.itson.dto.PaqueteDTO;

/**
 * Bus que recibe y canaliza mensajes recibidos por servicios.
 *
 * @author Jack Murrieta
 */
public class EventBus {

    private Map<String, List<Servicio>> servicios;
    private IEmisor emisor;

    // Mapa para almacenar las solicitudes en proceso de consenso
    // Key: IP:Puerto del solicitante, Value: [SolicitudUnirse, cantidadEsperada, votosAceptados, votosRechazados]
    private Map<String, Object[]> solicitudesEnConsenso;

    public EventBus() {
        this.servicios = new ConcurrentHashMap<>();
        this.solicitudesEnConsenso = new ConcurrentHashMap<>();
    }

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    public void publicarEvento(PaqueteDTO paquete) {
        Servicio origen = new Servicio(paquete.getPuertoOrigen(), paquete.getHost());
        normalizarPaquete(paquete, origen);

        if (paquete.getTipoEvento().equalsIgnoreCase("INICIAR_CONEXION")) {
            Servicio nuevoServicio = new Servicio(paquete.getPuertoOrigen(), paquete.getHost());

            List<String> eventos = (List<String>) paquete.getContenido();

            if (eventos != null) {
                for (String evento : eventos) {
                    registrarServicio(evento, nuevoServicio);
                }
            }

            System.out.println("[EventBus] Servicio registrado: " + nuevoServicio);
            return;
        }

        notificarServicios(paquete);
    }

    public void registrarServicio(String tipoEvento, Servicio servicio) {
        List<Servicio> lista = servicios.computeIfAbsent(tipoEvento, serviciosSuscritos -> new ArrayList<>());
        for (Servicio s : lista) {
            if (s.getHost().equals(servicio.getHost()) && s.getPuerto() == servicio.getPuerto()) {

                return;
            }
        }
        lista.add(servicio);
    }

    public void eliminarServicio(String tipoEvento, Servicio servicio) {
        List<Servicio> lista = servicios.get(tipoEvento);
        if (lista != null) {
            lista.remove(servicio);
        }
    }

    //notificar servicios por red
    public void notificarServicios(PaqueteDTO paquete) {
        //  Obtener suscriptores del tipo de evento del paquete
        List<Servicio> lista = servicios.get(paquete.getTipoEvento());

        if (lista != null) {

            for (Servicio servicio : lista) {
                if (Objects.equals(servicio.getHost(), paquete.getHost())
                        && servicio.getPuerto() == paquete.getPuertoOrigen()) {
                    continue;
                }

                paquete.setHost(servicio.getHost());
                paquete.setPuertoDestino(servicio.getPuerto());
                emisor.enviarCambio(paquete);
            }
        }
    }

    public void agregarEvento(String nombreEvento, Servicio servicio) {
        List<Servicio> lista = servicios.get(nombreEvento);
        if (lista == null) {
            lista = new ArrayList<>();
            servicios.put(nombreEvento, lista);
        }
        for (Servicio s : lista) {
            boolean mismoHost = s.getHost().equals(servicio.getHost());
            boolean mismoPuerto = s.getPuerto() == servicio.getPuerto();

            if (mismoHost && mismoPuerto) {
                System.out.println("[EventBus] Servicio ya registrado para este evento.");
                return;
            }
        }
        lista.add(servicio);
    }

    private void normalizarPaquete(PaqueteDTO paquete, Servicio origen) {

        // Si el host viene vacío o null, se lo ponemos
        if (paquete.getHost() == null || paquete.getHost().isEmpty()) {
            paquete.setHost(origen.getHost());
        }

        // Si el puertoOrigen viene en 0, se lo ponemos
        if (paquete.getPuertoOrigen() == 0) {
            paquete.setPuertoOrigen(origen.getPuerto());
        }
    }

    //obtener el host de la partida
    public Servicio obtenerHostPartida() {
        //el host es el primer suscriptor en sala de espera
        //esto se hace para validar que hay una partida existente si no hay usuarios en espera significa que no existe una partida
        List<Servicio> lista = servicios.get("EN_SALA_ESPERA");
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
    }

    /**
     * Obtiene la cantidad de servicios suscritos a un tipo de evento específico.
     *
     * @param tipoEvento El tipo de evento
     * @return Cantidad de servicios suscritos
     */
    public int obtenerCantidadSuscriptores(String tipoEvento) {
        List<Servicio> lista = servicios.get(tipoEvento);
        if (lista == null) {
            return 0;
        }
        return lista.size();
    }

    public void enviarHost(PaqueteDTO paquete) {
        Servicio host = obtenerHostPartida();

        PaqueteDTO respuesta = new PaqueteDTO();
        respuesta.setTipoEvento("RESPUESTA_HOST");

        respuesta.setHost(paquete.getHost()); // ip del solicitante

        respuesta.setPuertoDestino(paquete.getPuertoOrigen()); // el solicitante va a recibir la respuesta

        respuesta.setPuertoOrigen(host != null ? host.getPuerto() : 0);

        respuesta.setContenido(host);

        emisor.enviarCambio(respuesta);

    }

    /**
     * Inicia el proceso de consenso cuando se recibe una solicitud de unirse.
     * Envía la solicitud a TODOS los servicios suscritos a SOLICITAR_UNIRSE.
     *
     * @param paquete Paquete con la solicitud
     */
    public void iniciarConsensoSolicitud(PaqueteDTO paquete) {
        String keyConsenso = paquete.getHost() + ":" + paquete.getPuertoOrigen();

        // Obtener cantidad de jugadores en sala de espera
        int cantidadJugadoresEnSala = obtenerCantidadSuscriptores("EN_SALA_ESPERA");

        if (cantidadJugadoresEnSala == 0) {
            System.out.println("[EventBus] No hay jugadores en sala de espera");
            // Enviar rechazo automático al solicitante
            enviarResultadoConsenso(paquete, false, "NO_HAY_JUGADORES_EN_SALA");
            return;
        }

        System.out.println("[EventBus] Iniciando consenso para solicitud de: " + keyConsenso);
        System.out.println("[EventBus] Cantidad de jugadores en sala: " + cantidadJugadoresEnSala);

        // Almacenar la solicitud en el mapa de consenso
        // [0] = contenido (solicitud), [1] = cantidad esperada, [2] = votos aceptados, [3] = votos rechazados
        Object[] datosConsenso = new Object[]{
            paquete.getContenido(),
            cantidadJugadoresEnSala,
            0, // votos aceptados
            0  // votos rechazados
        };

        solicitudesEnConsenso.put(keyConsenso, datosConsenso);

        // Notificar a todos los servicios suscritos a SOLICITAR_UNIRSE
        // (que son los jugadores en sala de espera)
        notificarServicios(paquete);
    }

    /**
     * Procesa un voto de un jugador en sala de espera.
     * Cuando se alcanza el consenso, envía el resultado al solicitante.
     *
     * @param paquete Paquete con el voto (contiene SolicitudUnirse con respuesta)
     */
    public void procesarVotoSolicitud(PaqueteDTO paquete) {
        // El contenido es un Map que representa SolicitudUnirse
        Map<String, Object> solicitudMap = (Map<String, Object>) paquete.getContenido();

        // Extraer datos del solicitante
        Map<String, Object> solicitanteMap = (Map<String, Object>) solicitudMap.get("jugadorSolicitante");
        String ipSolicitante = (String) solicitanteMap.get("ip");
        int puertoSolicitante = ((Number) solicitanteMap.get("puerto")).intValue();

        String keyConsenso = ipSolicitante + ":" + puertoSolicitante;

        Object[] datosConsenso = solicitudesEnConsenso.get(keyConsenso);

        if (datosConsenso == null) {
            System.err.println("[EventBus] No se encontró solicitud en consenso para: " + keyConsenso);
            return;
        }

        boolean votoAceptado = (Boolean) solicitudMap.get("solicitudEstado");
        int cantidadEsperada = (int) datosConsenso[1];
        int votosAceptados = (int) datosConsenso[2];
        int votosRechazados = (int) datosConsenso[3];

        // Incrementar contador según el voto
        if (votoAceptado) {
            votosAceptados++;
            datosConsenso[2] = votosAceptados;
        } else {
            votosRechazados++;
            datosConsenso[3] = votosRechazados;

            // Si hay UN solo rechazo, se rechaza inmediatamente (consenso unánime)
            System.out.println("[EventBus] Solicitud rechazada por un jugador. Consenso: RECHAZADO");
            String tipoRechazo = (String) solicitudMap.get("tipoRechazo");
            enviarResultadoConsenso(crearPaqueteSolicitante(ipSolicitante, puertoSolicitante, datosConsenso[0]),
                                   false,
                                   tipoRechazo != null ? tipoRechazo : "RECHAZADO_POR_JUGADOR");
            solicitudesEnConsenso.remove(keyConsenso);
            return;
        }

        int totalVotos = votosAceptados + votosRechazados;

        System.out.println("[EventBus] Votos recibidos: " + totalVotos + "/" + cantidadEsperada
                         + " (Aceptados: " + votosAceptados + ", Rechazados: " + votosRechazados + ")");

        // Verificar si se alcanzó el consenso
        if (totalVotos >= cantidadEsperada) {
            // Consenso unánime: TODOS deben aceptar
            boolean consensoAlcanzado = (votosAceptados == cantidadEsperada);

            System.out.println("[EventBus] Consenso alcanzado: " + (consensoAlcanzado ? "ACEPTADO" : "RECHAZADO"));

            enviarResultadoConsenso(crearPaqueteSolicitante(ipSolicitante, puertoSolicitante, datosConsenso[0]),
                                   consensoAlcanzado,
                                   consensoAlcanzado ? null : "NO_HAY_CONSENSO");

            // Eliminar solicitud del mapa
            solicitudesEnConsenso.remove(keyConsenso);
        }
    }

    /**
     * Envía el resultado del consenso al solicitante.
     *
     * @param paqueteSolicitante Paquete con datos del solicitante
     * @param aceptado true si fue aceptado, false si rechazado
     * @param tipoRechazo Tipo de rechazo (si aplica)
     */
    private void enviarResultadoConsenso(PaqueteDTO paqueteSolicitante, boolean aceptado, String tipoRechazo) {
        Map<String, Object> solicitudMap = (Map<String, Object>) paqueteSolicitante.getContenido();

        // Actualizar estado de la solicitud
        solicitudMap.put("solicitudEstado", aceptado);
        if (!aceptado && tipoRechazo != null) {
            solicitudMap.put("tipoRechazo", tipoRechazo);
        }

        PaqueteDTO respuesta = new PaqueteDTO();
        respuesta.setTipoEvento("RESULTADO_CONSENSO");
        respuesta.setContenido(solicitudMap);
        respuesta.setHost(paqueteSolicitante.getHost());
        respuesta.setPuertoDestino(paqueteSolicitante.getPuertoOrigen());
        respuesta.setPuertoOrigen(0); // El EventBus es el origen

        System.out.println("[EventBus] Enviando RESULTADO_CONSENSO a "
                         + paqueteSolicitante.getHost() + ":" + paqueteSolicitante.getPuertoOrigen()
                         + " - Estado: " + (aceptado ? "ACEPTADO" : "RECHAZADO"));

        emisor.enviarCambio(respuesta);
    }

    /**
     * Crea un paquete con los datos del solicitante.
     */
    private PaqueteDTO crearPaqueteSolicitante(String ip, int puerto, Object contenido) {
        PaqueteDTO paquete = new PaqueteDTO();
        paquete.setHost(ip);
        paquete.setPuertoOrigen(puerto);
        paquete.setContenido(contenido);
        return paquete;
    }

}
