/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuraciones;

import Entidades.TipoEvento;
import Fachada.ConfiguracionesFachada;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.ConfiguracionesDTO;
import org.itson.dto.PaqueteDTO;
import org.itson.observadores.ConfiguracionObservable;
import org.itson.observadores.ObservadorConfiguracion;
import org.itson.observadores.ObservadorConfiguracionLocal;

/**
 *
 * @author erika
 */
public class ConfiguracionesPartida implements ConfiguracionesFachada, ObservadorConfiguracion, ConfiguracionObservable {

    private IEmisor emisor;

    private final java.util.List<ObservadorConfiguracionLocal> observadoresLocales;

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    public ConfiguracionesPartida() {
        this.observadoresLocales = new java.util.ArrayList<>();
    }

    @Override
    public void setConfiguraciones(ConfiguracionesDTO configuraciones) {

        PaqueteDTO<ConfiguracionesDTO> paquete = new PaqueteDTO<>(
                configuraciones,
                TipoEvento.CONFIGURAR_PARTIDA.toString()
        );

        paquete.setHost("localhost"); 
        paquete.setPuertoOrigen(1030); // Puerto de escucha del cliente
        paquete.setPuertoDestino(5555); // Puerto del Event Bus

        if (this.emisor != null) {
            this.emisor.enviarCambio(paquete);
            System.out.println("[onfiguracionesPartida] Configuracion enviada a la cola como: " + paquete.getTipoEvento());
        } else {
            System.err.println("[onfiguracionesPartida] Error: IEmisor no está configurado (es null).");
        }
    }

    @Override
    public void cambioConfiguracion(ConfiguracionesDTO configuracion) {
        this.setConfiguraciones(configuracion);
    }

    @Override
    public void agregarObservadorLocal(ObservadorConfiguracionLocal ob) {
        this.observadoresLocales.add(ob);
    }

    private void notificarObservadoresLocales(ConfiguracionesDTO configuracion) {
        for (ObservadorConfiguracionLocal ob : observadoresLocales) {
            ob.configuracionRecibida(configuracion);
        }
    }

}
