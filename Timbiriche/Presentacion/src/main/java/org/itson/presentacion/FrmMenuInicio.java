package org.itson.presentacion;

import MVCConfiguracion.controlador.ControladorArranque;
import MVCConfiguracion.modelo.INotificadorHostUnirsePartida;
import MVCConfiguracion.observer.INotificadorUnirsePartida;
import MVCConfiguracion.vista.unirsePartida.DlgEnviarSolicitud;
import MVCConfiguracion.vista.unirsePartida.DlgUnirsePartida;
import MVCConfiguracion.vista.unirsePartida.DlgMostrarMensaje;
import SolicitudEntity.SolicitudUnirse;
import java.awt.Color;
import objetosPresentables.JugadorConfig;

/**
 *
 * @author victoria
 */
public class FrmMenuInicio extends javax.swing.JFrame implements INotificadorUnirsePartida, INotificadorHostUnirsePartida {

    private final Color COLOR_FONDO = new Color(224, 233, 255);
    private ControladorArranque controlador;
    //private IModeloLectura solo puede pedir info de modelo o se le puede asar con observer
    private String ip;
    private int puerto;

    public FrmMenuInicio() {
        initComponents();
        this.setBackground(COLOR_FONDO);
        this.setLocationRelativeTo(null);
        setSize(1050, 740);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Establece el controlador para manejar solicitudes de unirse a partida
     *
     * @param controlador Controlador de arranque
     */
    public void setControlador(ControladorArranque controlador) {
        this.controlador = controlador;
    }

    /**
     * Dependiendo del tipo de respuesta se muestra un JDialog. Implementa INotificadorUnirsePartida.
     *
     * @param solicitud La solicitud con la respuesta del host
     */
    @Override
    public void actualizar(SolicitudUnirse solicitud) {
        boolean estadoSolicitud = solicitud.isSolicitudEstado();
        String tipoRechazo = solicitud.getTipoRechazo();

        if (estadoSolicitud) {
            // SOLICITUD ACEPTADA - Navegar a sala de espera
            mostrarSalaEspera(solicitud);
            return;
        }

        if ("RECHAZADO_POR_HOST".equals(tipoRechazo)) {
            mostrarDialogoRechazadoPorHost(solicitud);
        } else {
            mostrarDialogoRechazo(tipoRechazo);
        }
    }

    /**
     * Muestra la sala de espera cuando la solicitud es aceptada.
     *
     * @param solicitud La solicitud aceptada
     */
    private void mostrarSalaEspera(SolicitudUnirse solicitud) {

        // frmSalaEspera.setVisible(true);
        // this.dispose();
        String mensajeAceptacion = "<html><div style='text-align: center;'>"
                + "¡Tu solicitud fue aceptada!<br><br>"
                + "Entrando a la sala de espera..."
                + "</div></html>";

        DlgMostrarMensaje dlgAceptacion = new DlgMostrarMensaje(this, true, mensajeAceptacion);
        dlgAceptacion.setLocationRelativeTo(this);
        dlgAceptacion.setVisible(true);

    }

    /**
     * Muestra el diálogo de rechazo apropiado según el tipo de rechazo.
     *
     * @param tipoRechazo El tipo de rechazo recibido
     */
    private void mostrarDialogoRechazo(String tipoRechazo) {

        if (tipoRechazo == null) {
            tipoRechazo = "RECHAZADO_POR_HOST";
        }

        switch (tipoRechazo) {
            case "PARTIDA_LLENA":
                mostrarDialogoPartidaLlena();
                break;

            case "PARTIDA_INICIADA":
                mostrarDialogoPartidaIniciada();
                break;

            case "PARTIDA_FINALIZADA":
                mostrarDialogoPartidaFinalizada();
                break;

            default:
                mostrarDialogoRechazoGenerico(tipoRechazo);
                break;
        }
    }

    /**
     * Muestra un diálogo indicando que la partida está llena.
     */
    private void mostrarDialogoPartidaLlena() {
        String mensaje = "<html><div style='text-align: center;'>"
                + "Lo sentimos, la partida está llena.<br>"
                + "No hay cupos disponibles en este momento.<br><br>"
                + "Por favor, intenta unirte a otra partida o crea una nueva."
                + "</div></html>";

        DlgMostrarMensaje dlgRechazo = new DlgMostrarMensaje(this, true, mensaje);
        dlgRechazo.setLocationRelativeTo(this);
        dlgRechazo.setVisible(true);
    }

    /**
     * Muestra un diálogo indicando que la partida ya ha iniciado.
     */
    private void mostrarDialogoPartidaIniciada() {
        // Usar el diálogo con el mensaje por defecto
        DlgMostrarMensaje dlgPartidaEnCurso = new DlgMostrarMensaje(this, true);
        dlgPartidaEnCurso.setLocationRelativeTo(this);
        dlgPartidaEnCurso.setVisible(true);
    }

    /**
     * Muestra un diálogo indicando que la partida ha finalizado.
     */
    private void mostrarDialogoPartidaFinalizada() {
        String mensaje = "<html><div style='text-align: center;'>"
                + "Lo sentimos, la partida ya ha finalizado.<br><br>"
                + "Por favor, intenta unirte a otra partida o crea una nueva."
                + "</div></html>";

        DlgMostrarMensaje dlgRechazo = new DlgMostrarMensaje(this, true, mensaje);
        dlgRechazo.setLocationRelativeTo(this);
        dlgRechazo.setVisible(true);
    }

    /**
     * Muestra un diálogo indicando que el host rechazó la solicitud.
     */
    private void mostrarDialogoRechazadoPorHost(SolicitudUnirse solicitud) {
        DlgEnviarSolicitud dlgEnviarSoli = new DlgEnviarSolicitud(this, true, solicitud);
        dlgEnviarSoli.setLocationRelativeTo(this);
        dlgEnviarSoli.setVisible(true);
    }

    /**
     * Muestra un diálogo genérico de rechazo cuando el tipo no es reconocido.
     *
     * @param tipoRechazo El tipo de rechazo desconocido
     */
    private void mostrarDialogoRechazoGenerico(String tipoRechazo) {
        String mensaje = "<html><div style='text-align: center;'>"
                + "Tu solicitud fue rechazada.<br>"
                + "Motivo: " + tipoRechazo + "<br><br>"
                + "Por favor, intenta unirte a otra partida o crea una nueva."
                + "</div></html>";

        DlgMostrarMensaje dlgRechazo = new DlgMostrarMensaje(this, true, mensaje);
        dlgRechazo.setLocationRelativeTo(this);
        dlgRechazo.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnNuevaPartida = new javax.swing.JButton();
        btnUnirsePartida = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(224, 233, 255));

        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/titulo.png"))); // NOI18N

        btnNuevaPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/crearNuevaPartida.png"))); // NOI18N
        btnNuevaPartida.setBorder(null);
        btnNuevaPartida.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/crearNuevaPartidaHover.png"))); // NOI18N
        btnNuevaPartida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/crearNuevaPartidaHover.png"))); // NOI18N
        btnNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaPartidaActionPerformed(evt);
            }
        });

        btnUnirsePartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/unirsePartida.png"))); // NOI18N
        btnUnirsePartida.setBorder(null);
        btnUnirsePartida.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/unirsePartidaHover.png"))); // NOI18N
        btnUnirsePartida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/unirsePartidaHover.png"))); // NOI18N
        btnUnirsePartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnirsePartidaActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/salir.png"))); // NOI18N
        btnSalir.setBorder(null);
        btnSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/salirHover.png"))); // NOI18N
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/salirHover.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addComponent(lblTitulo)
                .addContainerGap(278, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalir)
                    .addComponent(btnNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnirsePartida, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(349, 349, 349))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(lblTitulo)
                .addGap(165, 165, 165)
                .addComponent(btnNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnUnirsePartida, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnSalir)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaPartidaActionPerformed
        // TODO add your handling code here:
        //Crear Un jugadorConfig y mostrar el panel salaPartida
        //atributos el usuario mokeado
    }//GEN-LAST:event_btnNuevaPartidaActionPerformed

    private void btnUnirsePartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnirsePartidaActionPerformed
        // TODO add your handling code here:
        //controlador llama a buscar a un host 
        controlador.buscarHostPartida(ip, puerto);
    }//GEN-LAST:event_btnUnirsePartidaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevaPartida;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUnirsePartida;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra el diálogo de unirse a partida cuando se encuentra un host. Implementa INotificadorHostUnirsePartida.
     *
     * @param jugadorHost El jugador host encontrado (null si no se encontró)
     */
    @Override
    public void actualizar(JugadorConfig jugadorHost) {
        System.out.println("[FrmMenuInicio] actualizar(JugadorConfig) llamado. Host: " + (jugadorHost != null ? jugadorHost.getNombre() : "NULL"));

        //si es null mostrar Jdialog no se encontro una partida existente
        if (jugadorHost == null) {
            String mensaje = "<html><div style='text-align: center;'>"
                    + "No se encontró ninguna partida disponible.<br><br>"
                    + "Puedes crear una nueva partida desde el menú principal."
                    + "</div></html>";

            DlgMostrarMensaje dlgNoHost = new DlgMostrarMensaje(this, true, mensaje);
            dlgNoHost.setLocationRelativeTo(this);
            dlgNoHost.setVisible(true);

            return;
        }

        // Configurar y mostrar diálogo de unirse a partida
        DlgUnirsePartida dlgUnirse = new DlgUnirsePartida(this, true);

        // Configurar el controlador para que pueda enviar solicitudes
        dlgUnirse.setControlador(controlador);

        // Configurar IP y puerto del cliente
        dlgUnirse.setIp(this.ip);
        dlgUnirse.setPuerto(this.puerto);

        dlgUnirse.setLocationRelativeTo(this);
        dlgUnirse.setVisible(true);
    }
}
