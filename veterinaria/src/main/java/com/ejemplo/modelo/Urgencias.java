package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Urgencias implements Serializable {

    private int idUrgencia;
    private Veterinarias veterinaria;
    private ArrayList<Mascotas> mascotas;
    private LocalDate fechaIngreso;
    private LocalDate fechaAlta;

    public Urgencias() {
    }

    public Urgencias(int idUrgencia, Veterinarias veterinaria, ArrayList<Mascotas> mascotas,
            LocalDate fechaIngreso, LocalDate fechaAlta) {
        this.idUrgencia = idUrgencia;
        this.veterinaria = veterinaria;
        this.mascotas = mascotas;
        this.fechaIngreso = fechaIngreso;
        this.fechaAlta = fechaAlta;
    }

    // Devuelve el ID de la urgencia
    public int getIdUrgencia() {
        return idUrgencia;
    }

    // Asigna el ID de la urgencia
    public void setIdUrgencia(int idUrgencia) {
        this.idUrgencia = idUrgencia;
    }

    // Devuelve la veterinaria de la urgencia
    public Veterinarias getVeterinaria() {
        return veterinaria;
    }

    // Asigna la veterinaria de la urgencia
    public void setVeterinaria(Veterinarias veterinaria) {
        this.veterinaria = veterinaria;
    }

    // Devuelve la lista de mascotas atendidas
    public ArrayList<Mascotas> getMascotas() {
        return mascotas;
    }

    // Asigna la lista de mascotas atendidas
    public void setMascotas(ArrayList<Mascotas> mascotas) {
        this.mascotas = mascotas;
    }

    // Devuelve la fecha de ingreso
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    // Asigna la fecha de ingreso
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    // Devuelve la fecha de alta (null si sigue internada)
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    // Asigna la fecha de alta
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Urgencias [idUrgencia=" + idUrgencia + ", veterinaria=" + veterinaria + ", mascotas=" + mascotas
                + ", fechaIngreso=" + fechaIngreso + ", fechaAlta=" + fechaAlta + "]";
    }

}
