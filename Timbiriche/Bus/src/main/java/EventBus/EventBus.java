/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EventBus;

import PublicadorEventos.PublicadorEventos;
import Servicio.Servicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.itson.componenteemisor.IEmisor;

import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class EventBus {

    private Map<String, List<Servicio>> servicios;
    private PublicadorEventos publicadorEventos;
    private IEmisor emisor;

    public EventBus(int puertoEntrada, String host) {
        this.servicios = new ConcurrentHashMap<>();
        System.out.println("new eventBus");
    }

    public void publicarEvento(String nombreEvento, String paquete) {
        PaqueteDTO dto = new PaqueteDTO(nombreEvento, paquete);
        notificarServicios(dto);
    }

    public void registrarServicio(String tipoEvento, Servicio servicio) {
        //registrar por local
        List<Servicio> lista = servicios.computeIfAbsent(tipoEvento, k -> new ArrayList<>());

        //valida que un servicio en un tipoevwnto no este registrado
        for (Servicio s : lista) {
            if (s.getHost().equals(servicio.getHost()) && s.getPuerto() == servicio.getPuerto()) {
                System.out.println("Servicio duplicado, no se registra de nuevo.");
                return;
            }
        }
        lista.add(servicio);
        System.out.println("Iservicio registrado");
    }

    //registra por red
    public void registrarServicioRed(PaqueteDTO dto) {

        Servicio servicioRed = new Servicio(dto.getPuerto(), dto.getHost());
        registrarServicio(dto.getTipoEvento(), servicioRed);
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
            //  Notificar a cada servicio por red usando su host y puerto
            for (Servicio servicio : lista) {

                publicadorEventos.publicar(paquete);
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
