package com.ejemplo.DAO;

import java.util.ArrayList;

import com.ejemplo.control.ConectarBase;
import com.ejemplo.modelo.Veterinarias;

public class VeterinariasDAO {

    // Inserta una veterinaria en la base de datos y devuelve el ID generado
    public int insertarVeterinaria(Veterinarias veterinaria) {
        int idVeterinaria = 0;

        String sql = "INSERT INTO veterinarias (nombre, direccion, consultas, abierta, fechaApertura) VALUES (?, ?, ?, ?, ?)";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, veterinaria.getNombre());
            preparedStatement.setString(2, veterinaria.getDireccion());
            preparedStatement.setInt(3, veterinaria.getConsultas());
            preparedStatement.setBoolean(4, veterinaria.isAbierta());
            preparedStatement.setDate(5, java.sql.Date.valueOf(veterinaria.getFechaApertura()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idVeterinaria = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idVeterinaria; // Retorna el ID de la veterinaria insertada
    }

    // Borra una veterinaria por su ID. Devuelve false si no existe o si tiene
    // reservas/urgencias asociadas (la clave foránea no deja borrarla)
    public static boolean borrarVeterinariaPorId(int idVeterinaria) {
        String sql = "DELETE FROM veterinarias WHERE idVeterinaria = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idVeterinaria);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Busca una veterinaria por su ID. Si no la encuentra devuelve null
    public Veterinarias leerVeterinariaPorId(int idVeterinaria) {
        Veterinarias veterinaria = null;
        String sql = "SELECT * FROM veterinarias WHERE idVeterinaria = ?";

        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idVeterinaria);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    veterinaria = armarVeterinaria(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return veterinaria;
    }

    // Modifica los datos de una veterinaria existente. Devuelve true si se actualizó
    public boolean modificarVeterinaria(Veterinarias veterinaria) {
        String sql = "UPDATE veterinarias SET nombre = ?, direccion = ?, consultas = ?, abierta = ?, fechaApertura = ? WHERE idVeterinaria = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, veterinaria.getNombre());
            preparedStatement.setString(2, veterinaria.getDireccion());
            preparedStatement.setInt(3, veterinaria.getConsultas());
            preparedStatement.setBoolean(4, veterinaria.isAbierta());
            preparedStatement.setDate(5, java.sql.Date.valueOf(veterinaria.getFechaApertura()));
            preparedStatement.setInt(6, veterinaria.getIdVeterinaria());

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Devuelve todas las veterinarias de la base en un ArrayList
    public ArrayList<Veterinarias> obtenerVeterinarias() {
        ArrayList<Veterinarias> listaVeterinarias = new ArrayList<>();
        String sql = "SELECT * FROM veterinarias";

        try (var conexion = new ConectarBase().conectar();
             var statement = conexion.createStatement();
             var resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                listaVeterinarias.add(armarVeterinaria(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaVeterinarias;
    }

    // Arma un objeto Veterinarias con los datos de la fila actual del resultSet
    private Veterinarias armarVeterinaria(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Veterinarias veterinaria = new Veterinarias();
        veterinaria.setIdVeterinaria(resultSet.getInt("idVeterinaria"));
        veterinaria.setNombre(resultSet.getString("nombre"));
        veterinaria.setDireccion(resultSet.getString("direccion"));
        veterinaria.setConsultas(resultSet.getInt("consultas"));
        veterinaria.setAbierta(resultSet.getBoolean("abierta"));
        veterinaria.setFechaApertura(resultSet.getDate("fechaApertura").toLocalDate());
        return veterinaria;
    }
}
