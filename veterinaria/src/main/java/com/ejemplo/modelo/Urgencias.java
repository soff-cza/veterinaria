package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;



public class Urgencias implements Serializable {

    private int idUrgencias;
    private Veterinarias veterinaria;
    private ArrayList<Mascotas> mascotas;
    private LocalDate fechaIngreso;
    private LocalDate fechaAlta;

    public Urgencias() {
    }
    public int getIdUrgencias() {
        return idUrgencias;
    }

    public void setIdUrgencias(int idUrgencias) {
        this.idUrgencias = idUrgencias;
    }

    public Veterinarias getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(Veterinarias veterinaria) {
        this.veterinaria = veterinaria;
    }

    public ArrayList<Mascotas> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascotas> mascota) {
        this.mascotas = mascota;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Urgencias [veterinaria=" + veterinaria + ", mascotas=" + mascotas + ", fechaIngreso=" + fechaIngreso
                + ", fechaAlta=" + fechaAlta + "]";
    }

}
