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
import MVCConfiguraciones.vista.FrmSalaEsperaMock;
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
public class EnsambladorEsperaFake {

    private static EnsambladorEsperaFake instancia;

    private String host;
    private int puertoEntrada;
    private int puertoServidor;

    private EnsambladorEsperaFake(Configuraciones config) {
        this.host = config.getString("host");
        this.puertoEntrada = config.getInt("puerto.entrada");
        this.puertoServidor = config.getInt("puerto.servidor");
    }

    public static EnsambladorEsperaFake getInstancia(String configName) {
        try {
            Configuraciones loader = new Configuraciones(configName);
            instancia = new EnsambladorEsperaFake(loader);
            return instancia;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    public void iniciarEsperaFake() {
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
        ServidorTCP servidor = new ServidorTCP(colaRecibos, puertoServidor);
        configPartida.setEmisor(emisor);

        Receptor receptorServidor = new Receptor();
        receptorServidor.setCola(colaRecibos);
        receptorServidor.setReceptor(partidaComunicacion);
        partidaComunicacion.setConfig(configPartida);
        colaRecibos.agregarObservador(receptorServidor);
        List<String> eventos = Arrays.asList(
                "REGISTRAR_JUGADOR",
                "SOLICITAR_ELEMENTOS_USADOS"
        );

        ModeloArranque modelo = new ModeloArranque(configPartida);
        IModeloArranqueEscritura ime = modelo;

        ControladorArranque controlador = new ControladorArranque(ime);
        FrmSalaEsperaMock frm = new FrmSalaEsperaMock(controlador);

        configPartida.agregarObserverPrueba(frm);

        PaqueteDTO solicitarConexion = new PaqueteDTO(eventos, TipoEvento.INICIAR_CONEXION.toString());
        solicitarConexion.setHost(host);
        solicitarConexion.setPuertoOrigen(puertoServidor);
        solicitarConexion.setPuertoDestino(puertoEntrada);
        emisor.enviarCambio(solicitarConexion);
        new Thread(() -> servidor.iniciar()).start();
        frm.setVisible(true);

    }
}
