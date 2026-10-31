package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;

public interface MascotasServicios {

    public int calcularEdad(Mascotas mascota);
    public Mascotas buscarMascotaPorNombre(LinkedList<Mascotas> listaMascotas, String nombre);
    // Búsqueda extra pedida por la consigna: por nombre del dueño, más útil
    // en la práctica porque puede haber varias mascotas con el mismo nombre
    public LinkedList<Mascotas> buscarMascotasPorDueño(LinkedList<Mascotas> listaMascotas, String nombreDueño);
    public Mascotas registrarMascota();
    public void agregarMascota(Mascotas mascota, LinkedList<Mascotas> listaMascotas);

    default void mostrarMascotas(LinkedList<Mascotas> listaMascotas) {
        if (listaMascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            System.out.println("Lista de Mascotas:");
            for (Mascotas mascota : listaMascotas) {
                System.out.println(mascota.toString());
            }
        }
    }

}