package MVCConfiguracion.vista;

import MVCConfiguracion.controlador.ControladorArranque;
import MVCConfiguracion.modelo.IModeloArranqueLectura;
import MVCConfiguracion.observer.ObservadorConfiguraciones;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;
import objetosPresentables.TableroConfig;

/**
 *
 * @author victoria
 */
public class FrmSalaEspera extends javax.swing.JFrame implements ObservadorConfiguraciones {

    private final Color COLOR_FONDO = new Color(224, 233, 255);
    private List<JugadorConfig> jugadores;
    private TableroConfig tablero;
    private JugadorConfig sesion;
    
    private int tamanoPanel = 0;

    private IModeloArranqueLectura modelo;
    private ControladorArranque controlador;

    public FrmSalaEspera(IModeloArranqueLectura modelo, ControladorArranque controlador) {
        initComponents();
        this.modelo = modelo;
        this.controlador = controlador;
        this.sesion = modelo.getSesion();

        pnlJugadores.setLayout(new BoxLayout(pnlJugadores, BoxLayout.X_AXIS));
        pnlJugadores.setAlignmentX(CENTER_ALIGNMENT);
       

        this.setLocationRelativeTo(null);
        this.setTitle("Sala de espera - Sesión de " + sesion.getNombre());
        this.setBackground(COLOR_FONDO);
        this.setSize(800, 510);

    }

    public void cargarJugadores() {
        pnlJugadores.removeAll();
        for (JugadorConfig jugador : jugadores) {
            tamanoPanel ++;
            pnlJugadores.add(new PnlJugador(jugador));
            pnlJugadores.add(Box.createHorizontalGlue());
        }
        pnlJugadores.setSize((250 * tamanoPanel), 261);
        pnlJugadores.revalidate();
        pnlJugadores.repaint();
    }

    @Override
    public void actualizar(PartidaPresentable configuraciones) {
        this.tablero = configuraciones.getTablero();
        this.jugadores = configuraciones.getJugadores();

        if (modelo.isVista()) {
            cargarJugadores();
            mostrarVista();
        }
    }


    @Override
    public void mostrarVista() {
        this.setVisible(true);
    }

    public void setStatusSesion(boolean status) {
        this.sesion.setListo(status);

        for (JugadorConfig jugador : jugadores) {
            if (jugador.getNombre().equals(sesion.getNombre())) {
                jugador.setListo(sesion.isListo());
            }
        }
    }
    
    
    @Override
    public void ocultarVista() {
        this.dispose();
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
        pnlJugadores = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(224, 233, 255));
        setPreferredSize(new java.awt.Dimension(1200, 940));

        pnlFooter.setBackground(new java.awt.Color(224, 233, 255));
        pnlFooter.setPreferredSize(new java.awt.Dimension(300, 100));

        btnRegresar.setBackground(new java.awt.Color(224, 233, 255));
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresar.png"))); // NOI18N
        btnRegresar.setBorder(null);
        btnRegresar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresarHover.png"))); // NOI18N
        btnRegresar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresarHover.png"))); // NOI18N
        btnRegresar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresarHover.png"))); // NOI18N
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnIniciar.setBackground(new java.awt.Color(224, 233, 255));
        btnIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/iniciarPatida.png"))); // NOI18N
        btnIniciar.setBorder(null);
        btnIniciar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/iniciarPatidaHover.png"))); // NOI18N
        btnIniciar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/iniciarPatidaHover.png"))); // NOI18N
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(btnRegresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 755));

        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sala de espera.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lblTitulo)
                .addContainerGap(398, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblTitulo)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        pnlJugadores.setBackground(new java.awt.Color(224, 233, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE)
            .addComponent(pnlJugadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(pnlJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // comunicar que el jugador que solicita iniciar está listo :P
        setStatusSesion(true);
        cargarJugadores();
        controlador.solicitarInicioJuego(sesion);
    }//GEN-LAST:event_btnIniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlJugadores;
    // End of variables declaration//GEN-END:variables


}
