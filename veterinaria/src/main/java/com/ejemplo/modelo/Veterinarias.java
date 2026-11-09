package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Veterinarias implements Serializable {

    private int idVeterinaria;
    private String nombre;
    private String direccion;
    private int consultas;
    private boolean abierta;
    private LocalDate fechaApertura;

    public Veterinarias() {
    }

    public Veterinarias(int idVeterinaria, String nombre, String direccion, int consultas, boolean abierta,
            LocalDate fechaApertura) {
        this.idVeterinaria = idVeterinaria;
        this.nombre = nombre;
        this.direccion = direccion;
        this.consultas = consultas;
        this.abierta = abierta;
        this.fechaApertura = fechaApertura;
    }

    // Devuelve el ID de la veterinaria
    public int getIdVeterinaria() {
        return idVeterinaria;
    }

    // Asigna el ID de la veterinaria
    public void setIdVeterinaria(int idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
    }

    // Devuelve el nombre de la veterinaria
    public String getNombre() {
        return nombre;
    }

    // Asigna el nombre de la veterinaria
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la dirección
    public String getDireccion() {
        return direccion;
    }

    // Asigna la dirección
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Devuelve la cantidad de consultas
    public int getConsultas() {
        return consultas;
    }

    // Asigna la cantidad de consultas
    public void setConsultas(int consultas) {
        this.consultas = consultas;
    }

    // Devuelve si la veterinaria está abierta
    public boolean isAbierta() {
        return abierta;
    }

    // Asigna si la veterinaria está abierta
    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    // Devuelve la fecha de apertura
    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    // Asigna la fecha de apertura
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }


    @Override
    public String toString() {
        return "Veterinarias [idVeterinaria=" + idVeterinaria + ", nombre=" + nombre + ", direccion=" + direccion
                + ", consultas=" + consultas + ", abierta=" + abierta + ", fechaApertura=" + fechaApertura + "]";
    }

}
