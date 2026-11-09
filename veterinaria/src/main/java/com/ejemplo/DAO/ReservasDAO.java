package com.ejemplo.DAO;

import java.util.ArrayList;

import com.ejemplo.control.ConectarBase;
import com.ejemplo.modelo.Reservas;

public class ReservasDAO {

    // Inserta una reserva en la base de datos y devuelve el ID generado.
    // Se guardan los ID de la mascota y la veterinaria (tabla de relación)
    public int insertarReserva(Reservas reserva) {
        int idReserva = 0;

        String sql = "INSERT INTO reservas (idMascota, idVeterinaria, fechaReserva, numeroReserva, precio, pagada) VALUES (?, ?, ?, ?, ?, ?)";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, reserva.getMascota().getIdMascota());
            preparedStatement.setInt(2, reserva.getVeterinaria().getIdVeterinaria());
            preparedStatement.setDate(3, java.sql.Date.valueOf(reserva.getFechaReserva()));
            preparedStatement.setInt(4, reserva.getNumeroReserva());
            preparedStatement.setInt(5, reserva.getPrecio());
            preparedStatement.setBoolean(6, reserva.isPagada());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idReserva = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idReserva; // Retorna el ID de la reserva insertada
    }

    // Borra una reserva por su ID
    public static boolean borrarReservaPorId(int idReserva) {
        String sql = "DELETE FROM reservas WHERE idReserva = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idReserva);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Busca una reserva por su ID. Si no la encuentra devuelve null
    public Reservas leerReservaPorId(int idReserva) {
        Reservas reserva = null;
        String sql = "SELECT * FROM reservas WHERE idReserva = ?";

        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, idReserva);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    reserva = armarReserva(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserva;
    }

    // Modifica los datos de una reserva existente. Devuelve true si se actualizó
    public boolean modificarReserva(Reservas reserva) {
        String sql = "UPDATE reservas SET idMascota = ?, idVeterinaria = ?, fechaReserva = ?, numeroReserva = ?, precio = ?, pagada = ? WHERE idReserva = ?";
        try (var conexion = new ConectarBase().conectar();
             var preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, reserva.getMascota().getIdMascota());
            preparedStatement.setInt(2, reserva.getVeterinaria().getIdVeterinaria());
            preparedStatement.setDate(3, java.sql.Date.valueOf(reserva.getFechaReserva()));
            preparedStatement.setInt(4, reserva.getNumeroReserva());
            preparedStatement.setInt(5, reserva.getPrecio());
            preparedStatement.setBoolean(6, reserva.isPagada());
            preparedStatement.setInt(7, reserva.getIdReserva());

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Devuelve todas las reservas de la base en un ArrayList
    public ArrayList<Reservas> obtenerReservas() {
        ArrayList<Reservas> listaReservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas";

        try (var conexion = new ConectarBase().conectar();
             var statement = conexion.createStatement();
             var resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                listaReservas.add(armarReserva(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaReservas;
    }

    // Arma un objeto Reservas con la fila actual del resultSet.
    // Con los ID guardados se buscan la mascota y la veterinaria completas
    private Reservas armarReserva(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Reservas reserva = new Reservas();
        reserva.setIdReserva(resultSet.getInt("idReserva"));
        reserva.setMascota(new MascotasDAO().leerMascotaPorId(resultSet.getInt("idMascota")));
        reserva.setVeterinaria(new VeterinariasDAO().leerVeterinariaPorId(resultSet.getInt("idVeterinaria")));
        reserva.setFechaReserva(resultSet.getDate("fechaReserva").toLocalDate());
        reserva.setNumeroReserva(resultSet.getInt("numeroReserva"));
        reserva.setPrecio(resultSet.getInt("precio"));
        reserva.setPagada(resultSet.getBoolean("pagada"));
        return reserva;
    }
}
