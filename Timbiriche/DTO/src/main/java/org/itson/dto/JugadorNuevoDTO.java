/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.dto;

/**
 *
 * @author Maryr
 */
public class JugadorNuevoDTO {

    private String nombre;
    private String color;
    private String avatar;

    public JugadorNuevoDTO(String nombre, String color, String avatar) {
        this.nombre = nombre;
        this.color = color;
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
