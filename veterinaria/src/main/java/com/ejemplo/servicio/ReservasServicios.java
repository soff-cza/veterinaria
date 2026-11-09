package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Veterinarias;

public interface ReservasServicios {

    // Arma un objeto Reservas vinculando la mascota y la veterinaria elegidas
    public Reservas registrarReserva(Mascotas mascota, Veterinarias veterinaria, LocalDate fechaReserva,
            int numeroReserva, int precio, boolean pagada);

    // Busca una reserva por su número en la lista
    public Reservas buscarReservaPorNumero(LinkedList<Reservas> listaReservas, int numeroReserva);

    // Guarda la reserva en la base de datos y la agrega a la lista
    public void agregarReserva(Reservas reserva, LinkedList<Reservas> listaReservas);

    // Muestra por consola todas las reservas de la lista
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