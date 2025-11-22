/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prueba;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import EventBus.EventBus;
import Mapper.MapperJugadores;
import PublicadorEventos.PublicadorEventos;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import Turnos.ManejadorTurnos;
import configuración.InyectorDependencias;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author pablo
 */
public class EnsambladorTurnos {

    public static EnsambladorTurnos instancia;

    private final String host = "localhost";
    private final int puertoEntrada = 5555;
    private final int puertoBus = 5556;

    private MapperJugadores mapperJugadores;

    // Componentes del sistema
    private EventBus eventBus;
    private IEmisor emisorBus;

    public EnsambladorTurnos() {
        this.mapperJugadores = new MapperJugadores();
    }

    public static EnsambladorTurnos getInstancia() {
        if (instancia == null) {
            instancia = new EnsambladorTurnos();
        }
        return instancia;
    }

    public void iniciarTurnero() {
        ColaRecibos colaRecibosTurnero = new ColaRecibos();
        ServidorTCP servidorTurnero = new ServidorTCP(colaRecibosTurnero, puertoEntrada);
        System.out.println("[Turnero] Servidor escuchando en puerto " + puertoEntrada);
        
        ColaEnvios colaEnviosBus = new ColaEnvios();
        ClienteTCP clienteTCPBus = new ClienteTCP(colaEnviosBus, puertoBus, host);
        colaEnviosBus.agregarObservador(clienteTCPBus);
        emisorBus = new Emisor(colaEnviosBus);
        
        eventBus = new EventBus(emisorBus);
        
        InyectorDependencias inyector = new InyectorDependencias();
        ManejadorTurnos manejador = inyector.setEmisor(emisorBus);
        
        eventBus.suscribirReceptorLocal("SOLICITAR_TURNOS", manejador);
        eventBus.suscribirReceptorLocal("ACTUALIZAR_TURNO", manejador);
        eventBus.suscribirReceptorLocal("TURNO_ACTUALIZADO", manejador);
        
        IReceptor publicador = new PublicadorEventos(puertoEntrada, host, eventBus);
        Receptor ReceptorTurnero = new Receptor(colaRecibosTurnero, publicador);
        colaRecibosTurnero.agregarObservador(ReceptorTurnero);
        new Thread(() -> servidorTurnero.iniciar()).start();

        PaqueteDTO paquete = new PaqueteDTO(null, "INICIAR_CONEXION");
        paquete.setHost(host);
        paquete.setPuertoOrigen(puertoEntrada);
        paquete.setPuertoDestino(puertoBus);
        emisorBus.enviarCambio(paquete);

        System.out.println("Esperando que se conecte el turnero.. ");
    }
}
