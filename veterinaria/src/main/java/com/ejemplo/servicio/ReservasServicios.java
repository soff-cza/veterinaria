package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Veterinarias;

public interface ReservasServicios {

    public Reservas registrarReserva(Mascotas mascota, Veterinarias veterinaria, LocalDate fechaReserva,
            int numeroReserva, int precio, boolean pagada);
    public Reservas buscarReservaPorNumero(LinkedList<Reservas> listaReservas, int numeroReserva);
    public void agregarReserva(Reservas reserva, LinkedList<Reservas> listaReservas);

    default void mostrarReservas(LinkedList<Reservas> listaReservas) {
        if (listaReservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            System.out.println("Lista de Reservas:");
            for (Reservas reserva : listaReservas) {
                System.out.println(reserva.toString());
            }
        }
    }

}