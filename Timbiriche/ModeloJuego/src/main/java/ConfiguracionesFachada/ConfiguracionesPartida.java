/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConfiguracionesFachada;

import Configuraciones.Observer.ObservableEvento;
import Configuraciones.Observer.ObserverEvento;
import Entidades.TipoEvento;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.JugadorNuevoDTO;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Maryr
 */
public class ConfiguracionesPartida implements ConfiguracionesFachada, ObservableEvento {

    private IEmisor emisor;
    private String host;
    private int puertoOrigen;
    private int puertoDestino;
    private ObserverEvento observer;

    public ConfiguracionesPartida() {
    }

    @Override
    public void registrarJugador(JugadorNuevoDTO jugador) {
        PaqueteDTO jugadorNuevo = new PaqueteDTO(jugador, TipoEvento.REGISTRAR_JUGADOR.toString());
        jugadorNuevo.setHost(host);
        jugadorNuevo.setPuertoOrigen(puertoOrigen);
        jugadorNuevo.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(jugadorNuevo);
        System.out.println("[configPartida] registrar jugador: " + jugador.getNombre() + " " + jugador.getColor() + " " + jugador.getAvatar());
    }

    @Override
    public void solicitarElementosUso() {
        PaqueteDTO solicitud = new PaqueteDTO("Please", TipoEvento.SOLICITAR_ELEMENTOS_USADOS.toString());
        solicitud.setHost(host);
        solicitud.setPuertoOrigen(puertoOrigen);
        solicitud.setPuertoDestino(puertoDestino);
        emisor.enviarCambio(solicitud);
        System.out.println("[configPartida] solicitar usos idk");
    }

    @Override
    public void recibirUsados(PaqueteDTO paquete) {
        List<String> usados = convertirAListaStrings(paquete);
        System.out.println("usados recibidos: " + usados);
        notificarObserver(usados);
    }

    public IEmisor getEmisor() {
        return emisor;
    }

    public void setEmisor(IEmisor emisor) {
        this.emisor = emisor;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuertoOrigen() {
        return puertoOrigen;
    }

    public void setPuertoOrigen(int puertoOrigen) {
        this.puertoOrigen = puertoOrigen;
    }

    public int getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    @Override
    public void agregarObserver(ObserverEvento o) {
        this.observer = o;
    }

    @Override
    public void notificarObserver(List<String> usados) {
        observer.validarJugador(usados);
    }

    private List<String> convertirAListaStrings(Object contenido) {
        if (contenido == null) {
            return null;
        }
        if (contenido instanceof List<?>) {
            List<?> lista = (List<?>) contenido;
            boolean esListaDeStrings = lista.stream().allMatch(e -> e instanceof String);
            if (esListaDeStrings) {
                return (List<String>) lista;
            }
            List<String> listaConvertida = new java.util.ArrayList<>();
            for (Object o : lista) {
                listaConvertida.add(String.valueOf(o));
            }
            return listaConvertida;
        }
        if (contenido instanceof String[]) {
            return Arrays.asList((String[]) contenido);
        }
        if (contenido instanceof Object[]) {
            Object[] arr = (Object[]) contenido;
            List<String> l = new java.util.ArrayList<>();
            for (Object o : arr) {
                l.add(String.valueOf(o));
            }
            return l;
        }
        return java.util.Collections.singletonList(String.valueOf(contenido));
    }

}
