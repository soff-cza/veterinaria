package com.ejemplo.servicio.impl;

import java.util.LinkedList;
import java.time.LocalDate;

import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.ReservasServicios;
import com.ejemplo.servicio.VeterinariasServicios;

public class ReservasServiciosImpl implements ReservasServicios {

    private VeterinariasServicios veterinariaServicios = new VeterinariasServiciosImpl();

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
        listaReservas.add(reserva);
        veterinariaServicios.incrementarConsultas(reserva.getVeterinaria());
        System.out.println("Reserva agregada exitosamente con el número: " + reserva.getNumeroReserva());
    }

}
