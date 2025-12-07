/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MVCConfiguraciones.vista;

import MVCConfiguraciones.controlador.ControladorArranque;
import MVCConfiguraciones.modelo.IModeloArranqueLectura;
import MVCConfiguraciones.observer.ObserverRegistro;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import org.itson.dto.JugadorNuevoDTO;

/**
 *
 * @author Maryr
 */
public class FrmRegistrarJugador extends javax.swing.JFrame implements ObserverRegistro {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmRegistrarJugador.class.getName());
    private IModeloArranqueLectura modelo;
    private ControladorArranque controlador;
    private JPanel panelAvatares;
    private ButtonGroup grupoAvatares;
    private String avatarSeleccionado = null;
    private Map<String, Image> avatars;
    private Map<String, Color> colores;
    private boolean respuestaRecibida = false;
    private javax.swing.Timer timerValidacion;

    /**
     * Creates new form FrmRegistrarJugador
     */
    public FrmRegistrarJugador(IModeloArranqueLectura modelo, ControladorArranque control) {
        initComponents();
        this.modelo = modelo;
        this.controlador = control;
        this.avatars = modelo.getAvatares();
        this.colores = modelo.getColores();
        cargarColores();
        crearPanelAvatares();
        setLocationRelativeTo(null);
    }

    private void cargarColores() {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        for (String colorKey : colores.keySet()) {
            modeloCombo.addElement(colorKey);
        }
        cbBoxColor.setModel(modeloCombo);
        cbBoxColor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    String colorKey = value.toString();
                    Color color = colores.get(colorKey);
                    if (color != null) {
                        label.setIcon(new ColorIcon(color, 580, 30));
                        label.setText("");
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                    }
                }
                return label;
            }
        });
    }

    private void crearPanelAvatares() {
        panelAvatares = new JPanel();
        panelAvatares.setLayout(new GridLayout(2, 5, 10, 10));
        panelAvatares.setBounds(151, 260, 700, 200);
        grupoAvatares = new ButtonGroup();

        for (Map.Entry<String, Image> entry : avatars.entrySet()) {
            String avatarKey = entry.getKey();
            Image imagen = entry.getValue();
            if (imagen != null) {
                JToggleButton btnAvatar = new JToggleButton() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        if (isSelected()) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(new Color(7, 55, 127));
                            g2.setStroke(new java.awt.BasicStroke(5));
                            g2.drawOval(26, 8, getHeight() - 18, getHeight() - 18);
                            g2.dispose();
                        }
                    }
                };
                btnAvatar.setIcon(new ImageIcon(entry.getValue()));
                btnAvatar.setPreferredSize(new Dimension(100, 120));
                btnAvatar.setBorderPainted(false);
                btnAvatar.setFocusPainted(false);
                btnAvatar.setContentAreaFilled(false);
                btnAvatar.addActionListener(e -> {
                    avatarSeleccionado = avatarKey;
                    for (Component comp : panelAvatares.getComponents()) {
                        comp.repaint();
                    }
                });
                grupoAvatares.add(btnAvatar);
                panelAvatares.add(btnAvatar);
            }
        }
        getContentPane().add(panelAvatares);
    }

    private void verificarElementos(List<String> usados) {
        String nombre = txtName.getText().trim();
        String color = cbBoxColor.getSelectedItem().toString();
        String avatar = avatarSeleccionado;

        List<String> errores = new ArrayList<>();

        if (usados.contains(nombre)) {
            errores.add("El nombre ya está en uso");
        }
        if (usados.contains(color)) {
            errores.add("El color ya está en uso");
        }
        if (usados.contains(avatar)) {
            errores.add("El avatar ya está en uso");
        }

        if (!errores.isEmpty()) {
            String mensaje = String.join("\n", errores);

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    mensaje,
                    "Datos repetidos",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        controlador.registrarJugador(new JugadorNuevoDTO(nombre, color, avatar));
    }

    @Override
    public void validarJugador(List<String> usados) {
        respuestaRecibida = true;
        if (timerValidacion != null) {
            timerValidacion.stop();
        }
        verificarElementos(usados);
    }

    private void continuar() {
        controlador.solicitarElementosUso();
        timerValidacion = new javax.swing.Timer(900, e -> {

            if (!respuestaRecibida) {
                System.out.println("[vista] No llegó respuesta, asumiendo primer jugador.");

                JugadorNuevoDTO j = new JugadorNuevoDTO(txtName.getText().trim(), cbBoxColor.getSelectedItem().toString(), avatarSeleccionado);
                FrmSalaEsperaFake frmEspera = new FrmSalaEsperaFake(j);
                controlador.registrarJugador(j);
                this.dispose();
            }

        });

        timerValidacion.setRepeats(false);
        timerValidacion.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbBoxColor = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnContinuar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1040, 638));
        setMinimumSize(new java.awt.Dimension(1040, 638));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(7, 55, 127));
        jLabel1.setText("Registrar jugador");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(7, 55, 127));
        jLabel2.setText("Nombre");

        txtName.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(7, 55, 127));
        jLabel3.setText("Color");

        cbBoxColor.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        cbBoxColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbBoxColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBoxColorActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(7, 55, 127));
        jLabel4.setText("Avatar");

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/regresar.png"))); // NOI18N
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnContinuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/botones/continuar.png"))); // NOI18N
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtName))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(26, 26, 26)
                                .addComponent(cbBoxColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(318, 318, 318)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(190, 190, 190))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 138, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(116, 116, 116)
                .addComponent(btnContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnContinuar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        continuar();
        System.out.println("nombre: " + txtName.getText() + " color: " + cbBoxColor.getSelectedItem().toString() + " avatarr: " + avatarSeleccionado);
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void cbBoxColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBoxColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBoxColorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinuar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbBoxColor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
    /**
     * Clase auxiliar para crear íconos de color
     */
    private static class ColorIcon implements Icon {

        private final Color color;
        private final int width;
        private final int height;

        public ColorIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }
    }
}
