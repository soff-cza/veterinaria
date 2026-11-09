package com.ejemplo.DAO;

import com.ejemplo.control.ConectarBase;
import com.ejemplo.modelo.Usuarios;

public class UsuariosDAO {

    // Lee un usuario de la base por su nombre. Si no existe, devuelve un
    // usuario vacío (con ID 0)
    public Usuarios leerUsuario(String nombreUsuario) {

        Usuarios usuario = new Usuarios();
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, nombreUsuario);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idUsuario = resultSet.getInt("idUsuario");
                    String claveUsuario = resultSet.getString("clave");
                    usuario.setIdUsuario(idUsuario);
                    usuario.setNombreUsuario(nombreUsuario);
                    usuario.setClaveUsuario(claveUsuario);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
