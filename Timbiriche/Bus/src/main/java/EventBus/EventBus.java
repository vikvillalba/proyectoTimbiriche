package EventBus;

import Servicio.Servicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.ConfiguracionesDTO;

import org.itson.dto.PaqueteDTO;

/**
 * Bus que recibe y canaliza mensajes recibidos por servicios.
 *
 * @author Jack Murrieta
 */
public class EventBus {

    private Map<String, List<Servicio>> servicios;
    private IEmisor emisor;
    private ConfiguracionesDTO configuraciones;

    public EventBus() {
        this.servicios = new ConcurrentHashMap<>();
        this.configuraciones = new ConfiguracionesDTO();

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

        if (paquete.getTipoEvento().equalsIgnoreCase("OBTENER_CONFIGURACIONES_PARTIDA")) {
            paquete.setContenido(configuraciones);
            notificarServicios(paquete);
            return;
        }

        if (paquete.getTipoEvento().equalsIgnoreCase("REGISTRAR_JUGADOR")) {
            configuraciones.agregarJugador(paquete);

            paquete.setContenido(configuraciones);
            paquete.setTipoEvento("OBTENER_CONFIGURACIONES_PARTIDA");
            notificarServicios(paquete);
            return;
        }

        if (paquete.getTipoEvento().equalsIgnoreCase("REGISTRAR_TABLERO")) {
            configuraciones.setTablero(paquete);
            System.out.println("Tablero registrado");
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
        List<Servicio> lista = servicios.get(paquete.getTipoEvento());

        if (lista == null) {
            return;
        }

        if (paquete.getTipoEvento().equalsIgnoreCase("OBTENER_CONFIGURACIONES_PARTIDA")) {

            for (Servicio servicio : lista) {
                if (servicio.getHost().equals(paquete.getHost())
                        && servicio.getPuerto() == paquete.getPuertoOrigen()) {

                    paquete.setHost(servicio.getHost());
                    paquete.setPuertoDestino(servicio.getPuerto());
                    emisor.enviarCambio(paquete);
                    return;
                }
            }

            return;
        }

        for (Servicio servicio : lista) {

            if (servicio.getHost().equals(paquete.getHost())
                    && servicio.getPuerto() == paquete.getPuertoOrigen()) {
                continue;
            }

            paquete.setHost(servicio.getHost());
            paquete.setPuertoDestino(servicio.getPuerto());
            emisor.enviarCambio(paquete);
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

    public void setConfiguracionJugadores(PaqueteDTO jugador) {
        configuraciones.agregarJugador(jugador);
    }

    public void setConfiguracionesTablero(PaqueteDTO tablero) {
        configuraciones.setTablero(tablero);
    }
}
