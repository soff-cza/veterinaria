package com.ejemplo.servicio;

public interface UsuariosServicios {

    // Valida que el usuario exista y que la clave ingresada sea correcta
    public boolean validarUsuario(String nombreUsuario, String claveUsuario);

}
