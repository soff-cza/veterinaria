package com.ejemplo.modelo;
import java.io.Serializable;

public class Usuarios implements Serializable {

    private int idUsuario;
    private String nombreUsuario;
    private String claveUsuario;

    // Constructor vacío
    public Usuarios() {
    }

    // Constructor con todos los atributos
    public Usuarios(int idUsuario, String nombreUsuario, String claveUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.claveUsuario = claveUsuario;
    }

    // Devuelve el ID del usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    // Asigna el ID del usuario
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Devuelve el nombre de usuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // Asigna el nombre de usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // Devuelve la clave (guardada en MD5)
    public String getClaveUsuario() {
        return claveUsuario;
    }

    // Asigna la clave
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }


    @Override
    public String toString() {
        return "Usuarios [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + "]";
    }

}
