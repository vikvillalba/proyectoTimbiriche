package MVCJuegoEnCurso.modelo.implementaciones;

import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import Fachada.PartidaFachada;
import MVCJuegoEnCurso.modelo.interfaces.IModeloJugadoresLectura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloPartidaEscritura;
import MVCJuegoEnCurso.modelo.interfaces.IModeloTableroLectura;
import MVCJuegoEnCurso.observer.ObservablePartida;
import MVCJuegoEnCurso.observer.ObservadorJugadores;
import MVCJuegoEnCurso.observer.ObservadorTablero;
import Observer.ObservadorTurnos;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import objetosPresentables.JugadorPresentable;
import objetosPresentables.LineaPresentable;
import objetosPresentables.PuntoPresentable;
import objetosPresentables.TableroPresentable;
import MVCJuegoEnCurso.observer.ObservadorInicioPartida;
import Observer.ObservadorInicio;
import java.util.stream.Collectors;
import objetosPresentables.CuadroPresentable;

/**
 *
 * @author victoria
 */
public class ModeloPartida implements IModeloJugadoresLectura, IModeloPartidaEscritura, IModeloTableroLectura, ObservablePartida, ObservadorTurnos, ObservadorInicio {

    private PartidaFachada partida;
    private ObservadorJugadores observadorJugadores;
    private ObservadorTablero observadorTablero;
    private ObservadorInicioPartida observadorInicioJuego;
    private static final Map<String, Color> COLORES = new HashMap<>();
    private static final Map<String, Image> AVATARES = new HashMap<>();

    public ModeloPartida(PartidaFachada partida) {
        this.partida = partida;
    }

    // traduce jugadores de la partida a presentable
    @Override
    public List<JugadorPresentable> getJugadores() {
        return jugadoresEntidadAPresentable(partida.getJugadores());

    }

    static {
        // Mapa con los nombres que se usan
        COLORES.put("rojo", new Color(220, 20, 60));
        COLORES.put("azul_pastel", new Color(135, 206, 235));
        COLORES.put("verde", new Color(143, 188, 139));
        COLORES.put("amarillo", new Color(242, 201, 104));
        COLORES.put("magenta", new Color(201, 91, 170));
        COLORES.put("naranja", new Color(240, 120, 77));
        COLORES.put("rosa_pastel", new Color(247, 163, 176));
        COLORES.put("azul_marino", new Color(49, 49, 125));
        COLORES.put("morado", new Color(147, 112, 219));

        // mapa con los avatares disponibles
        AVATARES.put("tiburon_ballena", cargarAvatar("tiburonBallena.png"));
        AVATARES.put("tiburon_blanco", cargarAvatar("tiburonBlanco.png"));
        AVATARES.put("tiburon_martillo", cargarAvatar("tiburonMartillo.png"));
        AVATARES.put("tiburon_smile_blue", cargarAvatar("tiburonSmileBlue.png"));
        AVATARES.put("tiburon_smile_gray", cargarAvatar("tiburonSmileGray.png"));
        AVATARES.put("tiburon_jump_blue", cargarAvatar("tiburonJumpBlue.png"));
        AVATARES.put("tiburon_jump_gray", cargarAvatar("tiburonJumpGray.png"));
        AVATARES.put("tiburon_still_blue", cargarAvatar("tiburonStillBlue.png"));
        AVATARES.put("tiburon_still_gray", cargarAvatar("tiburonStillGray.png"));
    }

    private static Image cargarAvatar(String nombreArchivo) {
        URL url = ModeloPartida.class.getResource("/avatares/" + nombreArchivo);
        if (url != null) {
            return new ImageIcon(url).getImage();
        } else {
            System.out.println("No se encontró la imagen: " + nombreArchivo);
            return null;
        }
    }

    private List<JugadorPresentable> jugadoresEntidadAPresentable(List<Jugador> jugadores) {
        List<JugadorPresentable> jugadoresVista = new ArrayList<>();

        for (Jugador jugador : jugadores) {

            JugadorPresentable jugadorVista = new JugadorPresentable(jugador.getNombre(),
                    obtenerAvatar(jugador.getAvatar().toString()),
                    obtenerColor(jugador.getColor().toString()),
                    jugador.getScore(),
                    jugador.isTurno());
            jugadoresVista.add(jugadorVista);
        }
        return jugadoresVista;
    }

    private Color obtenerColor(String colorEnum) {
        return COLORES.get(colorEnum.toLowerCase());
    }

    private Image obtenerAvatar(String avatarEnum) {
        return AVATARES.get(avatarEnum.toLowerCase());
    }

    // traduce a entidad y llama al validar de fachada
    @Override
    public boolean unirPuntos(PuntoPresentable[] puntos) {
        boolean jugada = partida.validarPuntos(
                partida.getPuntoTablero(puntos[0].getX(), puntos[0].getY()), partida.getPuntoTablero(puntos[1].getX(), puntos[1].getY()));

        if (jugada) {
            notificarObservadorTablero();
            return true;
        }
        
        if(!jugada) {
            notificarObservadorTablero();
            actualizarTurnos();
            return true;
        }
        return false;
    }

    // llama a fachada q actualice los turnos
    @Override
    public void actualizarTurnos() {
        partida.actualizarTurno();
        //cambia el estado de turno en la lista de jugadores 
        notificarObservadorJugadores(partida.getJugadores());
    }

    // traduce a presentable
    @Override
    public TableroPresentable getTablero() {
        Punto[][] tablero = partida.getTablero();

        List<PuntoPresentable> puntosVista = new ArrayList<>();

        for (int i = 0; i < tablero.length; i++) { // recorre filas
            for (int j = 0; j < tablero[i].length; j++) { // recorre elementos de la fila
                PuntoPresentable puntoVista = new PuntoPresentable(i, j);
                puntosVista.add(puntoVista);
            }
        }

       // Líneas
        List<Linea> lineasTablero = partida.getLineasTablero();
        List<LineaPresentable> lineasVista = new ArrayList<>();
        for (Linea linea : lineasTablero) {
            PuntoPresentable origen = new PuntoPresentable(linea.getOrigen().getX(), linea.getOrigen().getY());
            PuntoPresentable destino = new PuntoPresentable(linea.getDestino().getX(), linea.getDestino().getY());

            Color colorLinea;
            if (linea.getDueño() != null) {
                colorLinea = obtenerColor(linea.getDueño().getColor().toString());
            } else {
                colorLinea = Color.BLACK; // color por defecto para líneas vacías
            }

            lineasVista.add(new LineaPresentable(origen, destino, colorLinea));
        }
         // Cuadros
        List<CuadroPresentable> cuadrosVista = new ArrayList<>();
        for (Cuadro cuadro : partida.getCuadrosTablero()) {
            List<PuntoPresentable> aristasVista = cuadro.getAristas().stream()
                    .map(p -> new PuntoPresentable(p.getX(), p.getY()))
                    .collect(Collectors.toList());
            Color colorDueno = new Color(0, 0, 0, 0);// color transparente para que no se vean al inicio
            if (cuadro.getDueno() != null){
                colorDueno = (obtenerColor(cuadro.getDueno().getColor().toString()));
            }
            Color base = colorDueno;
            Color colorTransparente = new Color(base.getRed(), base.getGreen(), base.getBlue(), 150);
            CuadroPresentable cuadroP = new CuadroPresentable(aristasVista, colorDueno);
            cuadrosVista.add(cuadroP);
            cuadroP.setColor(colorDueno);
            if (cuadro.getDueno() != null){
                cuadroP.setDueno(cuadro.getDueno().getNombre());
            }
        }
            
            // construir tablero presentable (tipo de objeto con el que la vista trabaja)
           TableroPresentable tableroVista = new TableroPresentable(puntosVista, lineasVista, cuadrosVista, tablero.length, tablero[0].length);
           return tableroVista;
        }

    // actualizar del observador
    @Override
    public void notificarObservadorTablero() {
        observadorTablero.actualizar(getTablero());
    }

    // actualizar del observador
    @Override
    public void notificarObservadorJugadores(List<Jugador> jugadores) {
        List<JugadorPresentable> jugadoresVista = jugadoresEntidadAPresentable(jugadores);
        observadorJugadores.actualizar(jugadoresVista);
    }

    // observa a la partida y cuando se actualizan los turnos notifica 
    // al observador de los jugadores (metodo d arriba)
    @Override
    public void actualizar(List<Jugador> jugadores) {
        notificarObservadorJugadores(jugadores);
    }

    @Override
    public void agregarObservadorTablero(ObservadorTablero ob) {
        this.observadorTablero = ob;
    }

    @Override
    public void agregarObservadorJugadores(ObservadorJugadores ob) {
        this.observadorJugadores = ob;
    }

    @Override
    public void agregarObservadorInicioJuego(ObservadorInicioPartida ob) {
        this.observadorInicioJuego = ob;
    }

    @Override
    public void iniciarJuego() {
        observadorInicioJuego.mostrarJuego();
    }

}
