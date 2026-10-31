package com.ejemplo.servicio.impl;

import com.ejemplo.DAO.UsuariosDAO;
import com.ejemplo.modelo.Usuarios;
import com.ejemplo.servicio.UsuariosServicios;

public class UsuariosServiciosImpl implements UsuariosServicios {

    @Override
    public boolean validarUsuario(String nombreUsuario, String claveUsuario) {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Usuarios usuario = usuariosDAO.leerUsuario(nombreUsuario);
        // Verificar si el usuario existe y si la clave coincide
        // adicionalmente verificar que el ID del usuario no sea cero y que el nombre y la clave no sean nulos
        if (usuario.getIdUsuario() != 0 && usuario.getNombreUsuario() != null && usuario.getClaveUsuario() != null) {
            // Comparar la clave ingresada con la clave almacenada en la base de datos
            // Se usa equals para comparar cadenas de texto
            // Si la clave ingresada coincide con la clave almacenada, se retorna TRUE, indicando que el usuario es válido
            return usuario.getClaveUsuario().equals(claveUsuario);
        }
        // Si el usuario no existe o la clave no coincide, se retorna FALSE, indicando que el usuario no es válido
        return false;
    }

}