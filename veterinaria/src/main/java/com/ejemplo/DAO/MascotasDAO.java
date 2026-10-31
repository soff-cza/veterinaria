package com.ejemplo.DAO;

import java.util.LinkedList;

import com.ejemplo.control.ConectarBase;
import com.ejemplo.modelo.Mascotas;

public class MascotasDAO {

    public int insertarMascota(Mascotas mascota) {
        // Implementación para insertar un mascota en la base de datos
        int idMascota = 0; // Aquí deberías generar o recuperar el ID de la mascota insertado
        
        String sql = "INSERT INTO mascotas (nombre, edad, vacunada, fechaNacimiento, nombreDueño, telefonoDueño) VALUES (?, ?, ?, ?, ?, ?)";
        try (var conexion = new ConectarBase().conectar();  
             var preparedStatement = conexion.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(2, mascota.getNombre());
            preparedStatement.setInt(3,mascota.getEdad());
            preparedStatement.setBoolean(4, mascota.isVacunada());
            preparedStatement.setDate(5, java.sql.Date.valueOf(mascota.getFechaNacimiento()));
            preparedStatement.setString(6, mascota.getNombreDueño());
            preparedStatement.setString(7, mascota.getTelefono());
            preparedStatement.setString(8, mascota.getEmail());
            
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows > 0) {
                try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idmascota = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idMascota; // Retorna el ID de la mascota insertado
    }

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

    public LinkedList<Mascotas> obtenerMascotas() {
        LinkedList<Mascotas> listaMascotas = new LinkedList<>();
        String sql = "SELECT * FROM mascotas";
        
        try (var conexion = new ConectarBase().conectar();  
             var statement = conexion.createStatement();  
             var resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Mascotas mascota = new Mascotas();
                mascota.setIdMascota(resultSet.getInt("idMascota"));
                mascota.setNombre(resultSet.getString("nombre"));
                mascota.setApellido(resultSet.getString("apellido"));
                mascota.setEmail(resultSet.getString("email"));
                mascota.setTelefono(resultSet.getString("telefono"));
                mascota.setDni(resultSet.getInt("dni"));
                mascota.setFechaNacimiento(resultSet.getDate("fechaNacimiento").toLocalDate());
                
                listaMascotas.add(mascota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMascotas;
    }
}