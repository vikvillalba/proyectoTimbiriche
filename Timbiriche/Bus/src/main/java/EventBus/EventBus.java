/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EventBus;

import DTO.PaqueteSuscripcionDTO;
import InterfazServicio.IServicio;
import PublicadorEventos.PublicadorEventos;
import Servicio.ServicioRed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class EventBus {

    private Map<String, List<IServicio>> suscriptores;
    public int puertoEntrada;
    public int puertoSalida;
    public String host;
    private PublicadorEventos publicadorEventos;

    public EventBus(int puertoEntrada, String host) {
        this.puertoEntrada = puertoEntrada;
        this.host = host;
        this.suscriptores = new ConcurrentHashMap<>();
    }

    public PublicadorEventos getPublicadorEventos() {
        return publicadorEventos;
    }

    public void setPublicadorEventos(PublicadorEventos publicadorEventos) {
        this.publicadorEventos = publicadorEventos;
    }

    public void registrarServicio(String tipoEvento, IServicio servicio) {
        //registrar por local
        List<IServicio> lista = suscriptores.computeIfAbsent(tipoEvento, k -> new ArrayList<>());

        //valida que un servicio en un tipoevwnto no este registrado
        for (IServicio s : lista) {
            if (s.getHost().equals(servicio.getHost()) && s.getPuerto() == servicio.getPuerto()) {
                System.out.println("Servicio duplicado, no se registra de nuevo.");
                return;
            }
        }

        lista.add(servicio);
        System.out.println("Iservicio registrado");
    }

    //registra por red
    public void registrarServicioRed(PaqueteSuscripcionDTO dto) {

        IServicio servicioRed = new ServicioRed(dto.getPuerto(), dto.getHost());
        registrarServicio(dto.getEvento(), servicioRed);
    }

    public void eliminarServicio(String tipoEvento, IServicio servicio) {
        List<IServicio> lista = suscriptores.get(tipoEvento);
        if (lista != null) {
            lista.remove(servicio);
        }
    }

    //notificar servicios por red
    public void notificarServicios(PaqueteDTO paquete) {
        //  Obtener suscriptores del tipo de evento del paquete
        List<IServicio> lista = suscriptores.get(paquete.getTipoEvento());

        if (lista != null) {
            //  Notificar a cada servicio por red usando su host y puerto
            for (IServicio servicio : lista) {

                publicadorEventos.publicar(paquete, servicio.getHost(), servicio.getPuerto());
            }
        }
    }

}
