package com.ejemplo.servicio.impl;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.DAO.ReservasDAO;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.ReservasServicios;
import com.ejemplo.servicio.VeterinariasServicios;

public class ReservasServiciosImpl implements ReservasServicios {

    private VeterinariasServicios veterinariasServicios = new VeterinariasServiciosImpl();

    // Arma un objeto Reservas vinculando la mascota y la veterinaria elegidas
    @Override
    public Reservas registrarReserva(Mascotas mascota, Veterinarias veterinaria, LocalDate fechaReserva,
            int numeroReserva, int precio, boolean pagada) {
        Reservas reserva = new Reservas();
        reserva.setMascota(mascota);
        reserva.setVeterinaria(veterinaria);
        reserva.setFechaReserva(fechaReserva);
        reserva.setNumeroReserva(numeroReserva);
        reserva.setPrecio(precio);
        reserva.setPagada(pagada);
        return reserva;
    }

    // Recorre la lista y devuelve la reserva con ese número
    @Override
    public Reservas buscarReservaPorNumero(LinkedList<Reservas> listaReservas, int numeroReserva) {
        Reservas reservaEncontrada = null;
        if (!listaReservas.isEmpty()) {
            for (Reservas reserva : listaReservas) {
                if (reserva.getNumeroReserva() == numeroReserva) {
                    reservaEncontrada = reserva;
                    break;
                }
            }
        }
        return reservaEncontrada;
    }


    @Override
    public void agregarReserva(Reservas reserva, LinkedList<Reservas> listaReservas) {
        int idReserva = new ReservasDAO().insertarReserva(reserva);
        reserva.setIdReserva(idReserva);
        listaReservas.add(reserva);
        veterinariasServicios.incrementarConsultas(reserva.getVeterinaria());
        System.out.println("Reserva agregada exitosamente con el id: " + idReserva);
    }

}
