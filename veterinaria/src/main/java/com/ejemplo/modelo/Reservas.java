package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservas implements Serializable {

    private int idReservas;
    private Mascotas mascota;
    private Veterinarias veterinaria;
    private LocalDate fechaReserva;
    private int numeroReserva;
    private int precio;
    private boolean pagada;

    public Reservas() {
    }

    public Reservas(int idReservas, Mascotas mascota, Veterinarias veterinaria, LocalDate fechaReserva,
            int numeroReserva, int precio, boolean pagada) {
        this.idReservas = idReservas;
        this.mascota = mascota;
        this.veterinaria = veterinaria;
        this.fechaReserva = fechaReserva;
        this.numeroReserva = numeroReserva;
        this.precio = precio;
        this.pagada = pagada;
    }


    public int getIdReservas() {
        return idReservas;
    }

    public void setIdReservas(int idReservas) {
        this.idReservas = idReservas;
    }

    public Mascotas getMascota() {
        return mascota;
    }

    public void setMascota(Mascotas mascota) {
        this.mascota = mascota;
    }

    public Veterinarias getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(Veterinarias veterinaria) {
        this.veterinaria = veterinaria;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(int numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    
    @Override
    public String toString() {
        return "Reservas [mascota=" + mascota + ", veterinaria=" + veterinaria + ", fechaReserva=" + fechaReserva + ", numeroReserva=" + numeroReserva + ", precio=" + precio
                + ", pagada=" + pagada + "]";
    }

    
    
}