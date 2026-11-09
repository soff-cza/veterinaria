package com.ejemplo.servicio.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import com.ejemplo.DAO.UsuariosDAO;
import com.ejemplo.modelo.Usuarios;
import com.ejemplo.servicio.UsuariosServicios;

public class UsuariosServiciosImpl implements UsuariosServicios {

    // Lee el usuario con el DAO y compara la clave ingresada (convertida a MD5)
    // con la clave guardada en la base
    @Override
    public boolean validarUsuario(String nombreUsuario, String claveUsuario) {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Usuarios usuario = usuariosDAO.leerUsuario(nombreUsuario);
        // Verificar que el ID del usuario no sea cero y que el nombre y la clave no sean nulos
        if (usuario.getIdUsuario() != 0 && usuario.getNombreUsuario() != null && usuario.getClaveUsuario() != null) {
            // Comparar la clave ingresada (convertida a MD5) con la clave almacenada en la base de datos
            String claveMD5 = convertirMD5(claveUsuario);

            return usuario.getClaveUsuario().equalsIgnoreCase(claveMD5);
        }
        // Si el usuario no existe o la clave no coincide, se retorna FALSE
        return false;
    }

    // Convierte un texto a su hash MD5 (32 caracteres hexadecimales)
    private String convertirMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar en MD5", e);
        }
    }

}
