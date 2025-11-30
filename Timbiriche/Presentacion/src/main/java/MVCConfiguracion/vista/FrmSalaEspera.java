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
        this.setSize(1100, 940);

    }

    private void cargarJugadores() {
        pnlJugadores.removeAll();
        for (JugadorConfig jugador : jugadores) {
            pnlJugadores.add(new PnlJugador(jugador));
            pnlJugadores.add(Box.createHorizontalGlue());
        }
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
    public void iniciarJuego() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarVista() {
        this.setVisible(true);
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
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
                .addGap(263, 263, 263)
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

        jLabel8.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel8.setText(".");

        jLabel9.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel9.setText(".");

        jLabel10.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel10.setText(".");

        jLabel11.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel11.setText(".");

        jLabel15.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel15.setText(".");

        jLabel16.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel16.setText(".");

        jLabel17.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel17.setText(".");

        jLabel18.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel18.setText(".");

        jLabel22.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel22.setText(".");

        jLabel23.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel23.setText(".");

        jLabel24.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel24.setText(".");

        jLabel25.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N
        jLabel25.setText(".");

        jLabel29.setFont(new java.awt.Font("Hiragino Maru Gothic ProN", 1, 60)); // NOI18N

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
                    .addComponent(jLabel23))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel18)
                    .addComponent(jLabel25))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(lblTitulo)))
                .addContainerGap(243, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblTitulo)
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
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
        this.sesion.setListo(true);
        
        for (JugadorConfig jugador : jugadores) {
            if(jugador.getNombre().equals(sesion.getNombre())){
                jugador.setListo(sesion.isListo());
            }
        }
        
        cargarJugadores();
        controlador.solicitarInicioJuego(sesion);
    }//GEN-LAST:event_btnIniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
