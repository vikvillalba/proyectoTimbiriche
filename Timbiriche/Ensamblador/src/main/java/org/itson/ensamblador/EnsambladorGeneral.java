/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.ensamblador;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.Jugador;
import EventBus.EventBus;
import Fachada.Partida;
import Fachada.PartidaFachada;
import MVCJuegoEnCurso.controlador.ControladorPartida;
import MVCJuegoEnCurso.modelo.implementaciones.ModeloPartida;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.vista.FrmPartida;
import PublicadorEventos.PublicadorEventos;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import Turnos.ManejadorTurnos;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Maryr
 */
public class EnsambladorGeneral {
    
    private static EnsambladorGeneral instancia;

    // Config
    private final String host = "localhost";
    private final int puertoEntrada = 5555;
    private final int puertoBus = 5556;

    // Componentes del sistema
    private EventBus eventBus;
    private IEmisor emisorBus;
    
    private EnsambladorGeneral() {
    }
    
    public static EnsambladorGeneral getInstancia() {
        if (instancia == null) {
            instancia = new EnsambladorGeneral();
        }
        return instancia;
    }
    
    public void iniciar(List<Jugador> jugadores, int alto, int ancho) {

        // configuracion del bus
        ColaEnvios colaEnviosBus = new ColaEnvios();
        ClienteTCP clienteTCPBus = new ClienteTCP(colaEnviosBus, puertoBus, host);
        colaEnviosBus.agregarObservador(clienteTCPBus);
        emisorBus = new Emisor(colaEnviosBus);
        eventBus = new EventBus(emisorBus);
        ColaRecibos recibosBus = new ColaRecibos();
        ServidorTCP servidorTCPBus = new ServidorTCP(recibosBus, puertoBus);
        
        IReceptor publicador = new PublicadorEventos(puertoBus, host, eventBus);
        Receptor ReceptorBus = new Receptor(recibosBus, publicador);
        recibosBus.agregarObservador(ReceptorBus);
        new Thread(() -> servidorTCPBus.iniciar()).start();
        
        
       
        
        ColaEnvios colaEnviosCliente = new ColaEnvios();
        ClienteTCP clienteTCPCliente = new ClienteTCP(colaEnviosCliente, puertoBus, host);
        colaEnviosCliente.agregarObservador(clienteTCPCliente);
        
        
        ManejadorTurnos manejador = new ManejadorTurnos(emisorBus);
//        PartidaFachada partida = new Partida(jugadores, alto, ancho, puertoEntrada, host);

    // pruebas con el bus xd
        PaqueteDTO conectar = new PaqueteDTO("hole", "INICIAR_CONEXION");
        conectar.setPuertoOrigen(puertoEntrada);
       
        colaEnviosCliente.queue(conectar);
        

//        
//        
//        ModeloPartida modelo = new ModeloPartida(partida);
//        IModeloJugadoresLectura imjl = modelo;
//        IModeloTableroLectura imtl = modelo;
//        IModeloPartidaEscritura impe = modelo;
//        ControladorPartida controlador = new ControladorPartida(impe);
//        FrmPartida frm = new FrmPartida(imjl, imtl, controlador);
//        partida.agregarObservadorInicioJuego(modelo);
//        modelo.agregarObservadorJugadores(frm.getObservadorJugadores());
//        modelo.agregarObservadorTablero(frm.getObservadorTablero());
//        modelo.agregarObservadorInicioJuego(frm);
//        partida.notificarObservadorInicioJuego();
        System.out.println("Jesus en moto");
    }
}
