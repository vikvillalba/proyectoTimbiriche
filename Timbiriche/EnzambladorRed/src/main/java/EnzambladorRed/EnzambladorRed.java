/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EnzambladorRed;

import Emisor.ClienteTCP;
import Emisor.ColaEnvios;
import Emisor.Emisor;
import Emisor.FabricaEmisores;
import ObserverReceptor.ObservadorRecibos;
import Receptor.ColaRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import org.itson.componenteemisor.IEmisor;
import org.itson.componentereceptor.IReceptor;

/**
 *
 * @author Jack Murrieta
 */
public class EnzambladorRed {
//
//    private static EnzambladorRed instancia;
//
//    private String host;
//    private int puertoEntrada;
//    private int puertoBus;
//
//    private IReceptor receptorInyectado;
//
//    private IEmisor emisor;
//    private Receptor receptor;
//    private ServidorTCP servidorTCP;
//
//    private EnzambladorRed() {
//    }
//
//    public static synchronized EnzambladorRed getInstancia() {
//        if (instancia == null) {
//            instancia = new EnzambladorRed();
//        }
//        return instancia;
//    }
//
//    /**
//     * configurar
//     */
//    public EnzambladorRed configurar(
//            String host,
//            int puertoEntrada,
//            int puertoBus,
//            IReceptor receptorInyectado) {
//
//        this.host = host;
//        this.puertoEntrada = puertoEntrada;
//        this.puertoBus = puertoBus;
//        this.receptorInyectado = receptorInyectado;
//
//        return this;
//    }
//
//    /**
//     * enzambla componentes de red e inyecta el Ireceptor como param
//     */
//    public void ensamblar() {
//
//        // ---------- RECEPTOR ----------
//        ColaRecibos colaRecibos = new ColaRecibos();
//        receptor = new Receptor(colaRecibos, receptorInyectado);
//
//        // Observador de recibos
//        ObservadorRecibos obs = receptor;
//        colaRecibos.agregarObservador(obs);
//
//        // Servidor TCP para escuchar eventos
//        servidorTCP = new ServidorTCP(colaRecibos, puertoEntrada);
//        new Thread(() -> servidorTCP.iniciar()).start();
//
//        // ---------- EMISOR ----------
//        ColaEnvios colaEnvios = new ColaEnvios();
//        ClienteTCP cliente = new ClienteTCP(colaEnvios, puertoBus, host);
//
//        // Cliente observa la cola
//        colaEnvios.agregarObservador(cliente);
//
//        // Emisor de alto nivel
//        emisor = new Emisor(colaEnvios);
//    }
//
//    public IEmisor getEmisor() {
//        return emisor;
//    }
//
//    public Receptor getReceptor() {
//        return receptor;
//    }
//
//    public ServidorTCP getServidorTCP() {
//        return servidorTCP;
//    }
}
