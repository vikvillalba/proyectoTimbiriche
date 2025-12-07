/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ensambladorGeneral;

import ConfiguracionesFachada.ConfiguracionesPartida;
import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Entidades.TipoEvento;
import Fachada.PartidaComunicacion;
import MVCConfiguraciones.controlador.ControladorArranque;
import MVCConfiguraciones.modelo.IModeloArranqueEscritura;
import MVCConfiguraciones.modelo.IModeloArranqueLectura;
import MVCConfiguraciones.modelo.ModeloArranque;
import MVCConfiguraciones.vista.FrmRegistrarJugador;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import configuraciones.Configuraciones;
import java.util.Arrays;
import java.util.List;
import org.itson.componenteemisor.IEmisor;
import org.itson.dto.PaqueteDTO;

/**
 *
 * @author Maryr
 */
public class EnsambladorRegistro {

    private static EnsambladorRegistro instancia;

    // Config
    private String host;
    private int puertoEntrada;
    private int puertoServidor;

    public EnsambladorRegistro(Configuraciones config) {
        this.host = config.getString("host");
        this.puertoEntrada = config.getInt("puerto.entrada");
        this.puertoServidor = config.getInt("puerto.servidor");
    }

    public static EnsambladorRegistro getInstancia(String configName) {
        try {
            Configuraciones loader = new Configuraciones(configName);
            instancia = new EnsambladorRegistro(loader);
            return instancia;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registrarJugador() {
        ConfiguracionesPartida configPartida = new ConfiguracionesPartida();
        configPartida.setHost(host);
        configPartida.setPuertoOrigen(puertoServidor);
        configPartida.setPuertoDestino(puertoEntrada);
        
        PartidaComunicacion partidaComunicacion = new PartidaComunicacion();

        ColaEnvios colaEnvios = new ColaEnvios();
        IEmisor emisor = new Emisor(colaEnvios);
        ClienteTCP cliente = new ClienteTCP(colaEnvios, puertoEntrada, host);
        colaEnvios.agregarObservador(cliente);

        ColaRecibos colaRecibos = new ColaRecibos();
        ServidorTCP servidorPartida = new ServidorTCP(colaRecibos, puertoServidor);
        configPartida.setEmisor(emisor);

        Receptor receptorPartida = new Receptor();
        receptorPartida.setCola(colaRecibos);
        receptorPartida.setReceptor(partidaComunicacion);
        partidaComunicacion.setConfig(configPartida);
        colaRecibos.agregarObservador(receptorPartida);
        List<String> eventos = Arrays.asList(
                "REGISTRAR_JUGADOR",
                "RESPONDER_ELEMENTOS_USADOS"
        );

        PaqueteDTO solicitarConexion = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        solicitarConexion.setHost(host);
        solicitarConexion.setPuertoOrigen(puertoServidor);
        solicitarConexion.setPuertoDestino(puertoEntrada);
        emisor.enviarCambio(solicitarConexion);
        new Thread(() -> servidorPartida.iniciar()).start();

        ModeloArranque modelo = new ModeloArranque(configPartida);
        IModeloArranqueLectura imlectura = modelo;
        IModeloArranqueEscritura imescritura = modelo;

        ControladorArranque controlador = new ControladorArranque(imescritura);
        FrmRegistrarJugador frm = new FrmRegistrarJugador(imlectura, controlador);

        configPartida.agregarObserver(modelo);
        modelo.agregarObserver(frm);          
        frm.setVisible(true);
    }
}
