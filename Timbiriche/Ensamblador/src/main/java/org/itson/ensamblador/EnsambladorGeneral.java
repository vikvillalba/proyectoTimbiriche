/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.ensamblador;

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
import Receptor.ServidorTCP;
import Turnos.ManejadorTurnos;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;

/**
 *
 * @author Maryr
 */
public class EnsambladorGeneral {

    private static EnsambladorGeneral instancia;

    // Config
    private final String host = "localhost";
    private final int puertoEntrada = 5000;
    private final int puertoBus = 5001;

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
        eventBus = new EventBus(puertoEntrada, host);
        ColaRecibos cr = new ColaRecibos();
//        ReceptorBus receptorBus = new ReceptorBus(cr, adaptadorReceptor);
//        cr.agregarObservador(receptorBus);
        ServidorTCP servidorTCP = new ServidorTCP(cr, puertoEntrada);
        new Thread(() -> servidorTCP.iniciar()).start();
        ColaEnvios colaEnvios = new ColaEnvios();
//        ClienteTCPBus cliente = new ClienteTCPBus(colaEnvios);
        emisorBus = new Emisor(colaEnvios);
//        colaEnvios.agregarObservador(cliente);
        PublicadorEventos publicador = new PublicadorEventos(emisorBus);
        ManejadorTurnos manejador = new ManejadorTurnos(emisorBus, host, puertoBus);
//        eventBus.registrarServicio("INICIO_PARTIDA", manejador);
//        eventBus.registrarServicio("ACTUALIZAR_TURNO", manejador);
//        eventBus.registrarServicio("TURNO_ACTUALIZADO", manejador);
        PartidaFachada partida = new Partida(jugadores, alto, ancho);
        ModeloPartida modelo = new ModeloPartida(partida);
        IModeloJugadoresLectura imjl = modelo;
        IModeloTableroLectura imtl = modelo;
        IModeloPartidaEscritura impe = modelo;
        ControladorPartida controlador = new ControladorPartida(impe);
        FrmPartida frm = new FrmPartida(imjl, imtl, controlador);
        partida.agregarObservadorInicioJuego(modelo);
        modelo.agregarObservadorJugadores(frm.getObservadorJugadores());
        modelo.agregarObservadorTablero(frm.getObservadorTablero());
        modelo.agregarObservadorInicioJuego(frm);
        partida.notificarObservadorInicioJuego();
        frm.setVisible(true);
        System.out.println("Jesus en moto");
    }
}
