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
    SOLICITAR_UNIRSE,           // Evento enviado por solicitante recibido por host
    RESPUESTA_SOLICITUD,        // Evento enviado por host recibido por solicitante
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
