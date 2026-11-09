package com.ejemplo.DAO;

import java.util.ArrayList;

import com.ejemplo.control.ConectarBase;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Urgencias;

public class UrgenciasDAO {

    // Inserta una urgencia y, en la tabla urgencias_mascotas, una fila por cada
    // mascota atendida. Devuelve el ID generado de la urgencia
    public int insertarUrgencia(Urgencias urgencia) {
        int idUrgencia = 0;

        String sql = "INSERT INTO urgencias (idVeterinaria, fechaIngreso, fechaAlta) VALUES (?, ?, ?)";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, urgencia.getVeterinaria().getIdVeterinaria());
            preparedStatement.setDate(2, java.sql.Date.valueOf(urgencia.getFechaIngreso()));
            // La fecha de alta puede quedar vacía (internación en curso)
            if (urgencia.getFechaAlta() == null) {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            } else {
                preparedStatement.setDate(3, java.sql.Date.valueOf(urgencia.getFechaAlta()));
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUrgencia = generatedKeys.getInt(1);
                    }
                }
            }

            // Se guarda cada mascota en la tabla de relación
            String sqlRelacion = "INSERT INTO urgencias_mascotas (idUrgencia, idMascota) VALUES (?, ?)";
            try (var psRelacion = conexion.prepareStatement(sqlRelacion)) {
                for (Mascotas mascota : urgencia.getMascotas()) {
                    psRelacion.setInt(1, idUrgencia);
                    psRelacion.setInt(2, mascota.getIdMascota());
                    psRelacion.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idUrgencia; // Retorna el ID de la urgencia insertada
    }

    // Borra una urgencia por su ID. Primero se borran sus filas de
    // urgencias_mascotas, porque la clave foránea no deja borrarla antes
    public static boolean borrarUrgenciaPorId(int idUrgencia) {
        String sqlRelacion = "DELETE FROM urgencias_mascotas WHERE idUrgencia = ?";
        String sql = "DELETE FROM urgencias WHERE idUrgencia = ?";
        try (var conexion = new ConectarBase().conectar();
             var psRelacion = conexion.prepareStatement(sqlRelacion);
             var preparedStatement = conexion.prepareStatement(sql)) {

            psRelacion.setInt(1, idUrgencia);
            psRelacion.executeUpdate();

            preparedStatement.setInt(1, idUrgencia);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Busca una urgencia por su ID. Si no la encuentra devuelve null
    public Urgencias leerUrgenciaPorId(int idUrgencia) {
        Urgencias urgencia = null;
        String sql = "SELECT * FROM urgencias WHERE idUrgencia = ?";

        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idUrgencia);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    urgencia = armarUrgencia(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urgencia;
    }

    // Modifica la veterinaria y las fechas de una urgencia existente
    public boolean modificarUrgencia(Urgencias urgencia) {
        String sql = "UPDATE urgencias SET idVeterinaria = ?, fechaIngreso = ?, fechaAlta = ? WHERE idUrgencia = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, urgencia.getVeterinaria().getIdVeterinaria());
            preparedStatement.setDate(2, java.sql.Date.valueOf(urgencia.getFechaIngreso()));
            if (urgencia.getFechaAlta() == null) {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            } else {
                preparedStatement.setDate(3, java.sql.Date.valueOf(urgencia.getFechaAlta()));
            }
            preparedStatement.setInt(4, urgencia.getIdUrgencia());

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Devuelve todas las urgencias de la base en un ArrayList
    public ArrayList<Urgencias> obtenerUrgencias() {
        ArrayList<Urgencias> listaUrgencias = new ArrayList<>();
        String sql = "SELECT * FROM urgencias";

        try (var conexion = new ConectarBase().conectar();
             var statement = conexion.createStatement();
             var resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                listaUrgencias.add(armarUrgencia(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUrgencias;
    }

    // Devuelve las mascotas atendidas en una urgencia (desde urgencias_mascotas)
    public ArrayList<Mascotas> obtenerMascotasDeUrgencia(int idUrgencia) {
        ArrayList<Mascotas> mascotas = new ArrayList<>();
        String sql = "SELECT idMascota FROM urgencias_mascotas WHERE idUrgencia = ?";
        MascotasDAO mascotasDAO = new MascotasDAO();

        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idUrgencia);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mascotas.add(mascotasDAO.leerMascotaPorId(resultSet.getInt("idMascota")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    // Arma un objeto Urgencias con la fila actual del resultSet
    private Urgencias armarUrgencia(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Urgencias urgencia = new Urgencias();
        urgencia.setIdUrgencia(resultSet.getInt("idUrgencia"));
        urgencia.setVeterinaria(new VeterinariasDAO().leerVeterinariaPorId(resultSet.getInt("idVeterinaria")));
        urgencia.setFechaIngreso(resultSet.getDate("fechaIngreso").toLocalDate());
        // Si la fecha de alta es NULL en la base, queda null en el objeto
        java.sql.Date fechaAlta = resultSet.getDate("fechaAlta");
        urgencia.setFechaAlta(fechaAlta == null ? null : fechaAlta.toLocalDate());
        urgencia.setMascotas(obtenerMascotasDeUrgencia(urgencia.getIdUrgencia()));
        return urgencia;
    }
}
