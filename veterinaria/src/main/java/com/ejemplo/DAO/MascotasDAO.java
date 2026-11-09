package com.ejemplo.DAO;

import java.util.ArrayList;

import com.ejemplo.control.ConectarBase;
import com.ejemplo.modelo.Mascotas;

public class MascotasDAO {

    // Inserta una mascota en la base de datos y devuelve el ID generado
    public int insertarMascota(Mascotas mascota) {
        int idMascota = 0;

        String sql = "INSERT INTO mascotas (nombre, edad, vacunada, fechaNacimiento, nombreDueño, telefonoDueño, emailDueño) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, mascota.getNombre());
            preparedStatement.setInt(2, mascota.getEdad());
            preparedStatement.setBoolean(3, mascota.isVacunada());
            preparedStatement.setDate(4, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
            preparedStatement.setString(5, mascota.getNombreDueño());
            preparedStatement.setString(6, mascota.getTelefonoDueño());
            preparedStatement.setString(7, mascota.getEmailDueño());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idMascota = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idMascota; // Retorna el ID de la mascota insertada
    }

    // Borra una mascota por su ID. Devuelve false si no existe o si tiene
    // reservas/urgencias asociadas (la clave foránea no deja borrarla)
    public static boolean borrarMascotaPorId(int idMascota) {
        String sql = "DELETE FROM mascotas WHERE idMascota = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idMascota);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Retorna true si se borró al menos un registro
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Retorna false en caso de error
        }
    }

    // Busca una mascota por su ID. Si no la encuentra devuelve null
    public Mascotas leerMascotaPorId(int idMascota) {
        Mascotas mascota = null;
        String sql = "SELECT * FROM mascotas WHERE idMascota = ?";

        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idMascota);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    mascota = armarMascota(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mascota;
    }

    // Modifica los datos de una mascota existente. Devuelve true si se actualizó
    public boolean modificarMascota(Mascotas mascota) {
        String sql = "UPDATE mascotas SET nombre = ?, edad = ?, vacunada = ?, fechaNacimiento = ?, nombreDueño = ?, telefonoDueño = ?, emailDueño = ? WHERE idMascota = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, mascota.getNombre());
            preparedStatement.setInt(2, mascota.getEdad());
            preparedStatement.setBoolean(3, mascota.isVacunada());
            preparedStatement.setDate(4, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
            preparedStatement.setString(5, mascota.getNombreDueño());
            preparedStatement.setString(6, mascota.getTelefonoDueño());
            preparedStatement.setString(7, mascota.getEmailDueño());
            preparedStatement.setInt(8, mascota.getIdMascota());

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Devuelve todas las mascotas de la base en un ArrayList
    public ArrayList<Mascotas> obtenerMascotas() {
        ArrayList<Mascotas> listaMascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";

        try (var conexion = new ConectarBase().conectar();
             var statement = conexion.createStatement();
             var resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                listaMascotas.add(armarMascota(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMascotas;
    }

    // Arma un objeto Mascotas con los datos de la fila actual del resultSet
    private Mascotas armarMascota(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Mascotas mascota = new Mascotas();
        mascota.setIdMascota(resultSet.getInt("idMascota"));
        mascota.setNombre(resultSet.getString("nombre"));
        mascota.setEdad(resultSet.getInt("edad"));
        mascota.setVacunada(resultSet.getBoolean("vacunada"));
        mascota.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
        mascota.setNombreDueño(resultSet.getString("nombreDueño"));
        mascota.setTelefonoDueño(resultSet.getString("telefonoDueño"));
        mascota.setEmailDueño(resultSet.getString("emailDueño"));
        return mascota;
    }
}
