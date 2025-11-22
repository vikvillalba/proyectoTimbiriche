package MVCJuegoEnCurso.vista;

import MVCJuegoEnCurso.controlador.ControladorPartida;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.observer.ObservadorTablero;
import excepciones.JugadaException;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import objetosPresentables.CuadroPresentable;
import objetosPresentables.LineaPresentable;
import objetosPresentables.PuntoPresentable;
import objetosPresentables.TableroPresentable;

/**
 * representación gráfica del tablero de juego.
 *
 * @author victoria
 */
public class PnlTablero extends JPanel implements ObservadorTablero {

    private TableroPresentable tablero;
    private IModeloTableroLectura modelo;
    private ControladorPartida controlador;

    public PnlTablero(IModeloTableroLectura modelo, ControladorPartida controlador) {
        initComponents();

        this.modelo = modelo;
        this.controlador = controlador;
        this.tablero = modelo.getTablero();

        this.setMinimumSize(new Dimension(600, 600));
        this.setPreferredSize(new Dimension(600, 600));

        cargarPuntos();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Restablece la posición de los puntos
                recalcularPosicionesPuntos();
                repaint();
            }
        });
    }

    private void cargarPuntos() {
        // Añade los paneles PnlPunto al tablero
        for (PuntoPresentable punto : tablero.getPuntos()) {
            PnlPunto pnl = new PnlPunto(punto, this);
            this.add(pnl);
        }
        recalcularPosicionesPuntos(); // Se manda a llamar otra vez en caso de cambios
    }

    private void recalcularPosicionesPuntos() {
        // Se obtiene el tamaño actual del panelTablero
        int alto = this.getHeight();
        int ancho = this.getWidth();
        int filas = this.tablero.getAlto();
        int columnas = this.tablero.getAncho();

        //Calcula el espacio que abarca cada panel de los puntos en el tablero xd
        int anchoPunto = ancho / columnas;
        int altoPunto = alto / filas;
        int diametro = 10;

        // Recorre todos los PnlPunto que ya estaban
        for (Component comp : this.getComponents()) {
            PnlPunto pnl = (PnlPunto) comp;
            PuntoPresentable punto = pnl.getPunto();

            // Recalcula la posición del nuevo tamaño del frame
            int x = punto.getX() * anchoPunto + anchoPunto / 2 - diametro / 2;
            int y = punto.getY() * altoPunto + altoPunto / 2 - diametro / 2;

            // Nuevos límites :p
            pnl.setBounds(x, y, diametro, diametro);
        }
    }

    @Override
    public void actualizar(TableroPresentable tablero) {
        this.tablero = tablero;
        repaint(); // pinta el tablero
//        controlador.actualizarTurno(); // cambia de turno ya que se realiza la jugada
    }

    private void resetearColores() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof PnlPunto) {
                ((PnlPunto) comp).setColor(Color.BLACK);
            }
        }
    }

    public void agregarPuntoSeleccionado(PuntoPresentable punto) {
        try {
            controlador.agregarPuntoSeleccionado(punto);

        } catch (JugadaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Jugada inválida", JOptionPane.ERROR_MESSAGE);
            resetearColores();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tablero == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        for (CuadroPresentable cuadro : tablero.getCuadros()) {
            List<PuntoPresentable> ps = cuadro.getVertices();
            if (ps.size() == 4) {
                int anchoPunto = getWidth() / tablero.getAncho();
                int altoPunto = getHeight() / tablero.getAlto();

                // Calcula coordenadas mínimas y máximas
                int minX = ps.stream().mapToInt(PuntoPresentable::getX).min().getAsInt();
                int maxX = ps.stream().mapToInt(PuntoPresentable::getX).max().getAsInt();
                int minY = ps.stream().mapToInt(PuntoPresentable::getY).min().getAsInt();
                int maxY = ps.stream().mapToInt(PuntoPresentable::getY).max().getAsInt();

                int x = minX * anchoPunto + anchoPunto / 2;
                int y = minY * altoPunto + altoPunto / 2;
                int width = (maxX - minX) * anchoPunto;
                int height = (maxY - minY) * altoPunto;

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
                g2.setColor(cuadro.getColor());
                g2.fillRect(x, y, width, height);

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // regresar la opacidad
                if (cuadro.getDueno() != null) {
                    String inicial = cuadro.getDueno().substring(0, 1).toUpperCase();

                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.BOLD, 20));

                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(inicial);
                    int textHeight = fm.getAscent();

                    int centerX = x + width / 2 - textWidth / 2;
                    int centerY = y + height / 2 + textHeight / 4;

                    g2.drawString(inicial, centerX, centerY);
                }

            }
        }

        for (LineaPresentable linea : tablero.getLineas()) {
            g2.setColor(linea.getColor());

            int anchoPunto = getWidth() / tablero.getAncho();
            int altoPunto = getHeight() / tablero.getAlto();

            int x1 = linea.getOrigen().getX() * anchoPunto + anchoPunto / 2;
            int y1 = linea.getOrigen().getY() * altoPunto + altoPunto / 2;
            int x2 = linea.getDestino().getX() * anchoPunto + anchoPunto / 2;
            int y2 = linea.getDestino().getY() * altoPunto + altoPunto / 2;

            g2.drawLine(x1, y1, x2, y2);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
