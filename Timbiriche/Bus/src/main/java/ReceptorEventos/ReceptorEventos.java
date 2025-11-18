/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReceptorEventos;

import DTO.PaqueteSuscripcionDTO;
import EventBus.EventBus;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Jack Murrieta
 */
public class ReceptorEventos implements IReceptor {

    private final EventBus bus;

    public ReceptorEventos(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void recibirCambio(PaqueteDTO paquete) {

        if (paquete.getTipoEvento().equals("CONTROL_EVENTBUS")) {
            PaqueteSuscripcionDTO dto = (PaqueteSuscripcionDTO) paquete.getContenido();
            if (dto.getComando().equals("REGISTRAR")) {

                bus.registrarServicioRed(dto);
            }

            return;
        }

        //notificar a los suscriptores
        bus.notificarServicios(paquete);
    }

}
