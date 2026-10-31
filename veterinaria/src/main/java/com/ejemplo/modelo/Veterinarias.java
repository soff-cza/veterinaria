package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Veterinarias implements Serializable {

    private int idVeterinarias;
    private String nombre;
    private String direccion;
    private int consultas;
    private boolean abierta;
    private LocalDate fechaApertura;

    public Veterinarias() {
    }

    public Veterinarias(int idVeterinarias, String nombre, String direccion, int consultas, boolean abierta,
            LocalDate fechaApertura) {
        this.idVeterinarias = idVeterinarias;
        this.nombre = nombre;
        this.direccion = direccion;
        this.consultas = consultas;
        this.abierta = abierta;
        this.fechaApertura = fechaApertura;
    }


    public int getIdVeterinarias() {
        return idVeterinarias;
    }

    public void setIdVeterinarias(int idVeterinarias) {
        this.idVeterinarias = idVeterinarias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getConsultas() {
        return consultas;
    }

    public void setConsultas(int consultas) {
        this.consultas = consultas;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    @Override
    public String toString() {
        return "Veterinarias [nombre=" + nombre + ", direccion=" + direccion + ", consultas=" + consultas
                + ", abierta=" + abierta + ", fechaApertura=" + fechaApertura + "]";
    }

}