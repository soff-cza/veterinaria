package com.ejemplo.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Mascotas implements Serializable {

    
    private int idMascota;
    private String nombre;
    private int edad;
    private boolean vacunada;
    private LocalDate fechaNacimiento;
    private String nombreDueño;
    private String telefonoDueño;
    private String emailDueño;

    public Mascotas() {
    }

    

    public Mascotas(int idMascota, String nombre, int edad, boolean vacunada, LocalDate fechaNacimiento,
            String nombreDueño, String telefonoDueño, String emailDueño) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.edad = edad;
        this.vacunada = vacunada;
        this.fechaNacimiento = fechaNacimiento;
        this.nombreDueño = nombreDueño;
        this.telefonoDueño = telefonoDueño;
        this.emailDueño = emailDueño;
    }



    public int getIdMascota() {
        return idMascota;
    }



    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }



    public String getNombre() {
        return nombre;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public int getEdad() {
        return edad;
    }



    public void setEdad(int edad) {
        this.edad = edad;
    }



    public boolean isVacunada() {
        return vacunada;
    }



    public void setVacunada(boolean vacunada) {
        this.vacunada = vacunada;
    }



    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }



    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }



    public String getNombreDueño() {
        return nombreDueño;
    }



    public void setNombreDueño(String nombreDueño) {
        this.nombreDueño = nombreDueño;
    }



    public String getTelefonoDueño() {
        return telefonoDueño;
    }



    public void setTelefonoDueño(String telefonoDueño) {
        this.telefonoDueño = telefonoDueño;
    }



    public String getEmailDueño() {
        return emailDueño;
    }



    public void setEmailDueño(String emailDueño) {
        this.emailDueño = emailDueño;
    }



    @Override
    public String toString() {
        return "Mascotas [nombre=" + nombre + ", edad=" + edad + ", vacunada=" + vacunada
                + ", fechaNacimiento=" + fechaNacimiento + ", nombreDueño=" + nombreDueño
                + ", telefonoDueño=" + telefonoDueño + ", emailDueño=" + emailDueño + "]";
    }

}