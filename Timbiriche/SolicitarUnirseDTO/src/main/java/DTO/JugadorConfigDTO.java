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
    private String avatar;
    private String color;
    private boolean listo;
    private String ip;
    private int puerto;

    /**
     * Constructor por defecto.
     */
    public JugadorConfigDTO() {
    }

    /**
     * Constructor para validación de existencia de partida. Solo se usa para verificar que existe un host con ip y puerto.
     *
     * @param ip IP del host
     * @param puerto Puerto del host
     */
    public JugadorConfigDTO(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    /**
     * Constructor completo para crear un jugador de configuración.
     *
     * @param nombre Nombre del jugador
     * @param avatar Avatar del jugador (nombre del enum)
     * @param color Color del jugador (nombre del enum)
     * @param listo Si el jugador está listo para iniciar
     */
    public JugadorConfigDTO(String nombre, String avatar, String color, boolean listo) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.listo = listo;
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

    @Override
    public String toString() {
        return "JugadorConfigDTO{"
                + "nombre='" + nombre + '\''
                + ", avatar='" + avatar + '\''
                + ", color='" + color + '\''
                + ", listo=" + listo
                + '}';
    }
}
