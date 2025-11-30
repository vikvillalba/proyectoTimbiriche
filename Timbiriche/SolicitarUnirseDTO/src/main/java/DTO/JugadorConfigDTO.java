package DTO;

/**
 * Clase DTO que representa a un jugador durante la fase de configuración (antes de iniciar la partida). Esta clase se usa para transferir información de jugadores entre la presentación y el modelo durante el setUp.
 *
 * A diferencia de JugadorDTO que representa un jugador durante el juego, esta clase incluye información específica de la configuración como avatar, color, estado de listo y si es host.
 *
 * @author Jack Murrieta
 */
public class JugadorConfigDTO {

    private String nombre;
    private String avatar;  // Nombre del enum AvatarEnum (ej: "TIBURON_BALLENA")
    private String color;   // Nombre del enum ColorEnum (ej: "AZUL_PASTEL")
    private boolean listo;  // Indica si el jugador está listo para iniciar
    private boolean esHost; // Indica si el jugador es el host de la partida

    /**
     * Constructor por defecto.
     */
    public JugadorConfigDTO() {
    }

    /**
     * Constructor completo para crear un jugador de configuración.
     *
     * @param nombre Nombre del jugador
     * @param avatar Avatar del jugador (nombre del enum)
     * @param color Color del jugador (nombre del enum)
     * @param listo Si el jugador está listo para iniciar
     * @param esHost Si el jugador es el host de la partida
     */
    public JugadorConfigDTO(String nombre, String avatar, String color, boolean listo, boolean esHost) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.listo = listo;
        this.esHost = esHost;
    }

    /**
     * Constructor sin especificar si es host (por defecto false).
     *
     * @param nombre Nombre del jugador
     * @param avatar Avatar del jugador (nombre del enum)
     * @param color Color del jugador (nombre del enum)
     * @param listo Si el jugador está listo para iniciar
     */
    public JugadorConfigDTO(String nombre, String avatar, String color, boolean listo) {
        this(nombre, avatar, color, listo, false);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public boolean isEsHost() {
        return esHost;
    }

    public void setEsHost(boolean esHost) {
        this.esHost = esHost;
    }

    @Override
    public String toString() {
        return "JugadorConfigDTO{"
                + "nombre='" + nombre + '\''
                + ", avatar='" + avatar + '\''
                + ", color='" + color + '\''
                + ", listo=" + listo
                + ", esHost=" + esHost
                + '}';
    }
}
