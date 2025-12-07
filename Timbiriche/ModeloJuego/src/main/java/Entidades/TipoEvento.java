/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Entidades;

/**
 *
 * @author erika
 */
public enum TipoEvento {
    NUEVA_LINEA,
    INICIAR_CONEXION,
    EN_SALA_ESPERA,             // Evento para jugadores que están en sala de espera
    SOLICITAR_UNIRSE,           // Evento enviado por solicitante a TODOS los jugadores en sala
    VOTAR_SOLICITUD,            // Evento enviado por cada jugador en sala (aceptar/rechazar)
    RESULTADO_CONSENSO,         // Evento enviado por EventBus al solicitante con resultado final
    RESPUESTA_SOLICITUD,        // Evento enviado por host recibido por solicitante (legacy)
    CONFIGURAR_PARTIDA,
    SOLICITAR_INICIAR_PARTIDA,
    SOLICITAR_FINALIZAR_PARTIDA,
    ABANDONAR_PARTIDA,
    ACTUALIZAR_PUNTOS,
    SOLICITAR_AVANCE_TURNO,
    ACTUALIZAR_TURNO,
    TURNO_ACTUALIZADO,
    INICIO_PARTIDA,
    CONFIRMAR_INICIO_PARTIDA
}
