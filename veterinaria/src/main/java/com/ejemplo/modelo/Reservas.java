package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservas implements Serializable {

    private int idReserva;
    private Mascotas mascota;
    private Veterinarias veterinaria;
    private LocalDate fechaReserva;
    private int numeroReserva;
    private int precio;
    private boolean pagada;

    public Reservas() {
    }

    public Reservas(int idReserva, Mascotas mascota, Veterinarias veterinaria, LocalDate fechaReserva,
            int numeroReserva, int precio, boolean pagada) {
        this.idReserva = idReserva;
        this.mascota = mascota;
        this.veterinaria = veterinaria;
        this.fechaReserva = fechaReserva;
        this.numeroReserva = numeroReserva;
        this.precio = precio;
        this.pagada = pagada;
    }

    // Devuelve el ID de la reserva
    public int getIdReserva() {
        return idReserva;
    }

    // Asigna el ID de la reserva
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    // Devuelve la mascota de la reserva
    public Mascotas getMascota() {
        return mascota;
    }

    // Asigna la mascota de la reserva
    public void setMascota(Mascotas mascota) {
        this.mascota = mascota;
    }

    // Devuelve la veterinaria de la reserva
    public Veterinarias getVeterinaria() {
        return veterinaria;
    }

    // Asigna la veterinaria de la reserva
    public void setVeterinaria(Veterinarias veterinaria) {
        this.veterinaria = veterinaria;
    }

    // Devuelve la fecha de la reserva
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    // Asigna la fecha de la reserva
    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    // Devuelve el número de reserva
    public int getNumeroReserva() {
        return numeroReserva;
    }

    // Asigna el número de reserva
    public void setNumeroReserva(int numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    // Devuelve el precio
    public int getPrecio() {
        return precio;
    }

    // Asigna el precio
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    // Devuelve si la reserva está pagada
    public boolean isPagada() {
        return pagada;
    }

    // Asigna si la reserva está pagada
    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    @Override
    public String toString() {
        return "Reservas [idReserva=" + idReserva + ", mascota=" + mascota + ", veterinaria=" + veterinaria
                + ", fechaReserva=" + fechaReserva + ", numeroReserva=" + numeroReserva + ", precio=" + precio
                + ", pagada=" + pagada + "]";
    }

}