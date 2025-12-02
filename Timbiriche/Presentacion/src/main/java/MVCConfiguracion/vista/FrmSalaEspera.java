package MVCConfiguracion.vista;

import MVCConfiguracion.controlador.ControladorArranque;
import MVCConfiguracion.modelo.IModeloArranqueLectura;
import MVCConfiguracion.observer.INotificadorUnirsePartida;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import MVCConfiguracion.vista.unirsePartida.DlgSolicitudHost;
import SolicitudEntity.SolicitudUnirse;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;
import objetosPresentables.TableroConfig;

/**
 *
 * @author victoria
 */
public class FrmSalaEspera extends javax.swing.JFrame implements ObservadorConfiguraciones, INotificadorUnirsePartida {

    private final Color COLOR_FONDO = new Color(224, 233, 255);
    private List<JugadorConfig> jugadores;
    private TableroConfig tablero;
    private JugadorConfig sesion;

    private IModeloArranqueLectura modelo;
    private ControladorArranque controlador;
    private DlgSolicitudHost dlgSolicitudHost;

    public FrmSalaEspera(List<JugadorConfig> jugadores, TableroConfig tablero, JugadorConfig sesion) {
        initComponents();
        pnlJugadores.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.sesion = sesion;
        cargarJugadores();

    }

    public void setControlador(ControladorArranque controlador) {
        this.controlador = controlador;

    }

    /**
     * Método llamado cuando se recibe una nueva solicitud de unirse a la partida.
     * Solo muestra el diálogo si la solicitud NO ha sido procesada aún (estado = false y sin tipo de rechazo).
     * Esto evita que el diálogo se muestre múltiples veces después de que el host ya respondió.
     */
    @Override
    public void actualizar(SolicitudUnirse solicitud) {
        System.out.println("[FrmSalaEspera] actualizar() llamado. Estado: " + solicitud.isSolicitudEstado() + ", TipoRechazo: " + solicitud.getTipoRechazo());

        // Solo mostrar el diálogo si la solicitud está PENDIENTE (no procesada)
        // Una solicitud está pendiente si:
        // - NO está aceptada (solicitudEstado = false)
        // - Y NO tiene tipo de rechazo asignado (es null o vacío)
        boolean estaPendiente = !solicitud.isSolicitudEstado()
            && (solicitud.getTipoRechazo() == null || solicitud.getTipoRechazo().isEmpty());

        if (!estaPendiente) {
            System.out.println("[FrmSalaEspera] Solicitud ya fue procesada. No se muestra el diálogo.");
            return;
        }

        // Cerrar diálogo previo si existe
        if (dlgSolicitudHost != null && dlgSolicitudHost.isDisplayable()) {
            dlgSolicitudHost.dispose();
            dlgSolicitudHost = null;
        }

        System.out.println("[FrmSalaEspera] Mostrando diálogo de solicitud...");

        dlgSolicitudHost = new DlgSolicitudHost(this, true);
        dlgSolicitudHost.setControlador(controlador);

        dlgSolicitudHost.setLocationRelativeTo(this);
        dlgSolicitudHost.setVisible(true);
    }

    private void cargarJugadores() {
        for (JugadorConfig jugador : jugadores) {
            PnlJugador pnl = new PnlJugador(jugador);
            this.pnlJugadores.add(pnl);
        }
    }

    @Override
    public void actualizar(PartidaPresentable configuraciones) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void iniciarJuego() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarVista() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFooter = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        btnIniciar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        pnlJugadores = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 800));

        pnlFooter.setBackground(new java.awt.Color(224, 233, 255));
        pnlFooter.setPreferredSize(new java.awt.Dimension(300, 100));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresar.png"))); // NOI18N
        btnRegresar.setBorder(null);
        btnRegresar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresarHover.png"))); // NOI18N
        btnRegresar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresarHover.png"))); // NOI18N
        btnRegresar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresarHover.png"))); // NOI18N

        btnIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/iniciarPatida.png"))); // NOI18N
        btnIniciar.setBorder(null);
        btnIniciar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/iniciarPatidaHover.png"))); // NOI18N
        btnIniciar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/iniciarPatidaHover.png"))); // NOI18N

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(btnRegresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIniciar)
                .addContainerGap())
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegresar)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(224, 233, 255));

        jPanel2.setBackground(new java.awt.Color(224, 233, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 800));

        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sala de espera.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel1.setText(".");

        jLabel2.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel2.setText(".");

        jLabel3.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel3.setText(".");

        jLabel4.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel4.setText(".");

        jLabel5.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel5.setText(".");

        jLabel6.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel6.setText(".");

        jLabel7.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel7.setText(".");

        jLabel8.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel8.setText(".");

        jLabel9.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel9.setText(".");

        jLabel10.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel10.setText(".");

        jLabel11.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel11.setText(".");

        jLabel12.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel12.setText(".");

        jLabel13.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel13.setText(".");

        jLabel14.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel14.setText(".");

        jLabel15.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel15.setText(".");

        jLabel16.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel16.setText(".");

        jLabel17.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel17.setText(".");

        jLabel18.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel18.setText(".");

        jLabel19.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel19.setText(".");

        jLabel20.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel20.setText(".");

        jLabel21.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel21.setText(".");

        jLabel22.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel22.setText(".");

        jLabel23.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel23.setText(".");

        jLabel24.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel24.setText(".");

        jLabel25.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel25.setText(".");

        jLabel26.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel26.setText(".");

        jLabel27.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel27.setText(".");

        jLabel28.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel28.setText(".");

        jLabel29.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel29.setText(".");

        jLabel30.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel30.setText(".");

        jLabel31.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel31.setText(".");

        jLabel32.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel32.setText(".");

        jLabel33.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel33.setText(".");

        jLabel34.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel34.setText(".");

        jLabel35.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel35.setText(".");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8)
                    .addComponent(jLabel15)
                    .addComponent(jLabel22)
                    .addComponent(jLabel29))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel16)
                    .addComponent(jLabel23)
                    .addComponent(jLabel30))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24)
                    .addComponent(jLabel31))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel18)
                    .addComponent(jLabel25)
                    .addComponent(jLabel32))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12)
                    .addComponent(jLabel19)
                    .addComponent(jLabel26)
                    .addComponent(jLabel33))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel34))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel28)
                    .addComponent(jLabel21)
                    .addComponent(jLabel14)
                    .addComponent(jLabel7))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(218, 218, 218))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(400, 400, 400)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(404, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTitulo)
                .addGap(57, 57, 57)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        pnlJugadores.setBackground(new java.awt.Color(224, 233, 255));

        javax.swing.GroupLayout pnlJugadoresLayout = new javax.swing.GroupLayout(pnlJugadores);
        pnlJugadores.setLayout(pnlJugadoresLayout);
        pnlJugadoresLayout.setHorizontalGroup(
            pnlJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1028, Short.MAX_VALUE)
        );
        pnlJugadoresLayout.setVerticalGroup(
            pnlJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(pnlJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFooter, javax.swing.GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnlFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlJugadores;
    // End of variables declaration//GEN-END:variables

}
