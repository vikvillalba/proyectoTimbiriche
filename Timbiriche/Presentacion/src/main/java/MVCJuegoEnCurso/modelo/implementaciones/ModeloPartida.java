package MVCJuegoEnCurso.modelo.implementaciones;

import Entidades.AvatarEnum;
import Entidades.ColorEnum;
import Entidades.Cuadro;
import Entidades.Jugador;
import Entidades.Linea;
import Entidades.Punto;
import Fachada.PartidaFachada;
import MVCConfiguracion.observer.ObservableEnsambladorInicio;
import MVCConfiguracion.observer.ObservadorEnsamblador;
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
import Mapper.MapperJugadores;
import Observer.ObservadorInicio;
import excepciones.JugadaException;
import excepciones.PartidaExcepcion;
import java.util.stream.Collectors;
import objetosPresentables.CuadroPresentable;
import objetosPresentables.JugadorConfig;
import objetosPresentables.PartidaPresentable;
import org.itson.dto.JugadorDTO;
import org.itson.dto.PartidaDTO;

/**
 *
 * @author victoria
 */
public class ModeloPartida implements IModeloJugadoresLectura,
        IModeloPartidaEscritura,
        IModeloTableroLectura,
        ObservablePartida,
        ObservadorInicio,
        ObservadorTurnos,
        Observer.ObservadorJugadores,
        Observer.ObservadorEventos,
        ObservableEnsambladorInicio {

    private PartidaFachada partida;
    private ObservadorJugadores observadorJugadores;
    private ObservadorTablero observadorTablero;
    private ObservadorInicioPartida observadorInicioJuego;
    private ObservadorEnsamblador observadorEnsamblador;
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
        COLORES.put("rojo_pastel", new Color(220, 20, 60));
        COLORES.put("azul_pastel", new Color(135, 206, 235));
        COLORES.put("verde_pastel", new Color(143, 188, 139));
        COLORES.put("amarillo_pastel", new Color(242, 201, 104));
        COLORES.put("magenta", new Color(201, 91, 170));
        COLORES.put("naranja_pastel", new Color(240, 120, 77));
        COLORES.put("rosa_pastel", new Color(247, 163, 176));
        COLORES.put("azul_marino", new Color(49, 49, 125));
        COLORES.put("moras", new Color(147, 112, 219));

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

    private ColorEnum getColorEnum(Color color) {
        for (Map.Entry<String, Color> entry : COLORES.entrySet()) {
            if (entry.getValue().equals(color)) {
                return ColorEnum.valueOf(entry.getKey().toUpperCase());
            }
        }
        return null;
    }

    private AvatarEnum getAvatarEnum(String avatarName) {
        if (avatarName == null) {
            return AvatarEnum.TIBURON_SMILE_GRAY;
        }

        try {
            return AvatarEnum.valueOf(avatarName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return AvatarEnum.TIBURON_SMILE_GRAY; // valor x defecto
        }
    }

    private ColorEnum getColorEnum(String colorName) {
        if (colorName == null) {
            return ColorEnum.AZUL_PASTEL;
        }
        try {
            return ColorEnum.valueOf(colorName.toUpperCase());
        } catch (IllegalArgumentException e) {

            return ColorEnum.AZUL_PASTEL; // valor x defecto
        }
    }

    private List<Jugador> jugadoresDTOAEntidad(List<JugadorDTO> jugadoresDTO) {
        List<Jugador> jugadores = new ArrayList<>();

        for (JugadorDTO config : jugadoresDTO) {

            AvatarEnum avatar = getAvatarEnum(config.getAvatar());
            ColorEnum color = getColorEnum(config.getColor());

            Jugador jugador = new Jugador();
            jugador.setNombre(config.getId());
            jugador.setTurno(config.isTurno());
            jugador.setAvatar(avatar);
            jugador.setColor(color);
            jugador.setScore(config.getScore());
            jugadores.add(jugador);
        }

        return jugadores;
    }

    private Jugador jugadorDTOAEntidad(JugadorDTO config) {
        AvatarEnum avatar = getAvatarEnum(config.getAvatar());
        ColorEnum color = getColorEnum(config.getColor());

        Jugador jugador = new Jugador();
        jugador.setNombre(config.getId());
        jugador.setTurno(config.isTurno());
        jugador.setAvatar(avatar);
        jugador.setColor(color);
        jugador.setScore(config.getScore());

        return jugador;
    }

    // traduce a entidad y llama al validar de fachada
    @Override
    public boolean unirPuntos(PuntoPresentable[] puntos) throws JugadaException {
        boolean jugada;
        try {
            jugada = partida.validarPuntos(partida.getPuntoTablero(puntos[0].getX(), puntos[0].getY()), partida.getPuntoTablero(puntos[1].getX(), puntos[1].getY()));
        } catch (PartidaExcepcion ex) {
            throw new JugadaException(ex.getMessage());
        }

        notificarObservadorTablero();

        if (jugada) {
            return true;
        }

        if (!jugada) {
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
            if (cuadro.getDueno() != null) {
                colorDueno = (obtenerColor(cuadro.getDueno().getColor().toString()));
            }
            Color base = colorDueno;
            Color colorTransparente = new Color(base.getRed(), base.getGreen(), base.getBlue(), 150);
            CuadroPresentable cuadroP = new CuadroPresentable(aristasVista, colorDueno);
            cuadrosVista.add(cuadroP);
            cuadroP.setColor(colorDueno);
            if (cuadro.getDueno() != null) {
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

    @Override
    public void actualizar(List<Jugador> jugadores) {
        observadorJugadores.actualizar(jugadoresEntidadAPresentable(jugadores));
    }

    @Override
    public JugadorPresentable getJugadorSesion() {
        Jugador sesion = partida.getJugadorSesion();
        JugadorPresentable jugadorVista = new JugadorPresentable(sesion.getNombre(),
                obtenerAvatar(sesion.getAvatar().toString()),
                obtenerColor(sesion.getColor().toString()),
                sesion.getScore(),
                sesion.isTurno());

        return jugadorVista;
    }

    @Override
    public void actualizar(Object cambio) {
        System.out.println("[ModeloPartida] Evento recibido: " + cambio.toString());
        // notificar a vista CAMBIO TABLEROO
        notificarObservadorTablero();
    }

    @Override
    public void iniciarPartida(PartidaDTO partidaDTO, JugadorDTO sesion) {

        List<JugadorDTO> jugadoresDTO = partidaDTO.getJugadores();
        List<Jugador> jugadoresEntidad = jugadoresDTOAEntidad(partidaDTO.getJugadores());
        Jugador sesionEntidad = jugadorDTOAEntidad(sesion);

        Jugador jugadorConTurno = null;
        for (Jugador j : jugadoresEntidad) {
            if (j.isTurno()) {
                jugadorConTurno = j;
                break;
            }
        }
        notificarEnsamblador(
                jugadoresEntidad,
                partidaDTO.getTablero().getAlto(),
                partidaDTO.getTablero().getAncho(),
                sesionEntidad
        );

        notificarObservadorJugadores(jugadoresEntidad);
    }

    @Override
    public void agregarObservadorEnsamblador(ObservadorEnsamblador ob) {
        observadorEnsamblador = ob;
    }

    @Override
    public void notificarEnsamblador(List<Jugador> jugadores, int altoTablero, int anchoTablero, Jugador sesion) {
        observadorEnsamblador.ensamblarPartida(jugadores, altoTablero, anchoTablero, sesion);
    }

}
