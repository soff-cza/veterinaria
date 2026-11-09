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

    // Devuelve el ID de la mascota
    public int getIdMascota() {
        return idMascota;
    }

    // Asigna el ID de la mascota
    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    // Devuelve el nombre de la mascota
    public String getNombre() {
        return nombre;
    }

    // Asigna el nombre de la mascota
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la edad de la mascota
    public int getEdad() {
        return edad;
    }

    // Asigna la edad de la mascota
    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Devuelve si la mascota está vacunada
    public boolean isVacunada() {
        return vacunada;
    }

    // Asigna si la mascota está vacunada
    public void setVacunada(boolean vacunada) {
        this.vacunada = vacunada;
    }

    // Devuelve la fecha de nacimiento
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Asigna la fecha de nacimiento
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Devuelve el nombre del dueño
    public String getNombreDueño() {
        return nombreDueño;
    }

    // Asigna el nombre del dueño
    public void setNombreDueño(String nombreDueño) {
        this.nombreDueño = nombreDueño;
    }

    // Devuelve el teléfono del dueño
    public String getTelefonoDueño() {
        return telefonoDueño;
    }

    // Asigna el teléfono del dueño
    public void setTelefonoDueño(String telefonoDueño) {
        this.telefonoDueño = telefonoDueño;
    }

    // Devuelve el email del dueño
    public String getEmailDueño() {
        return emailDueño;
    }

    // Asigna el email del dueño
    public void setEmailDueño(String emailDueño) {
        this.emailDueño = emailDueño;
    }


    @Override
    public String toString() {
        return "Mascotas [idMascota=" + idMascota + ", nombre=" + nombre + ", edad=" + edad + ", vacunada=" + vacunada
                + ", fechaNacimiento=" + fechaNacimiento + ", nombreDueño=" + nombreDueño
                + ", telefonoDueño=" + telefonoDueño + ", emailDueño=" + emailDueño + "]";
    }

}
