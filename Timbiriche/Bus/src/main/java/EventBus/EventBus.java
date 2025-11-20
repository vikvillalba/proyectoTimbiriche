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
 *
 * @author Jack Murrieta
 */
public class EventBus {

    private Map<String, List<Servicio>> servicios;
    private IEmisor emisor;

    public EventBus(IEmisor emisor) {
        this.servicios = new ConcurrentHashMap<>();
        this.emisor = emisor;
    }

    public void publicarEvento(PaqueteDTO paquete) {
        if (paquete.getTipoEvento().equalsIgnoreCase("INICIAR_CONEXION")) {
            Servicio nuevoServicio = new Servicio(paquete.getPuertoOrigen(), paquete.getHost());
            registrarServicio(paquete.getTipoEvento(), nuevoServicio);
            System.out.println("servicio conectao" + nuevoServicio.toString());
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

}
