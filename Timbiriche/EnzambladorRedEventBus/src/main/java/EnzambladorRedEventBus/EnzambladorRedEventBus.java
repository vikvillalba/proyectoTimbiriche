/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EnzambladorRedEventBus;

import ColaRecibosBus.ColaRecibosBus;
import IEmisorBus.ClienteTCPBus;
import IEmisorBus.ColaEnviosBus;
import IEmisorBus.EmisorBus;
import IEmisorBus.IEmisorBus;
import ObserverReceptor.ObservadorRecibos;
import Receptor.Receptor;
import Receptor.ServidorTCP;
import org.itson.componentereceptor.IReceptor;

/**
 *
 * @author Jack Murrieta
 */
public class EnzambladorRedEventBus {

    private static EnzambladorRedEventBus instancia;

    private String host;
    private int puertoEntrada;

    private IReceptor receptorInyectado;

    private IEmisorBus emisor;
    private Receptor receptor;
    private ServidorTCP servidorTCP;

    private EnzambladorRedEventBus() {
    }

    public static synchronized EnzambladorRedEventBus getInstancia() {
        if (instancia == null) {
            instancia = new EnzambladorRedEventBus();
        }
        return instancia;
    }

    /**
     * configurar
     */
    public EnzambladorRedEventBus configurar(
            String host,
            int puertoEntrada,
            IReceptor receptorInyectado) {

        this.host = host;
        this.puertoEntrada = puertoEntrada;
        this.receptorInyectado = receptorInyectado;

        return this;
    }

    /**
     * enzambla componentes de red e inyecta el Ireceptor como param
     */
    public void ensamblar() {

        ColaRecibosBus colaRecibos = new ColaRecibosBus();
        
        receptor = new Receptor(colaRecibos, receptorInyectado);

        // Observador de recibos
        ObservadorRecibos obs = receptor;
        colaRecibos.agregarObservador(obs);

        // Servidor TCP para escuchar eventos
        servidorTCP = new ServidorTCP(colaRecibos, puertoEntrada,host);
        new Thread(() -> servidorTCP.iniciar()).start();

        ColaEnviosBus colaEnvios = new ColaEnviosBus();

        ClienteTCPBus cliente = new ClienteTCPBus(colaEnvios);

        colaEnvios.agregarObservador(cliente);

        emisor = new EmisorBus(colaEnvios);
    }

    public IEmisorBus getEmisor() {
        return emisor;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public ServidorTCP getServidorTCP() {
        return servidorTCP;
    }
}
