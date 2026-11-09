package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;

public interface MascotasServicios {

    // Calcula la edad de la mascota a partir de su fecha de nacimiento
    public int calcularEdad(Mascotas mascota);

    // Busca una mascota por nombre en la lista
    public Mascotas buscarMascotaPorNombre(LinkedList<Mascotas> listaMascotas, String nombre);

    // Busca todas las mascotas de un mismo dueño
    public LinkedList<Mascotas> buscarMascotasPorDueño(LinkedList<Mascotas> listaMascotas, String nombreDueño);

    // Arma un objeto Mascotas con los datos cargados en el formulario
    public Mascotas registrarMascota(String nombre, boolean vacunada, LocalDate fechaNacimiento,
            String nombreDueño, String telefonoDueño, String emailDueño);

    // Guarda la mascota en la base de datos y la agrega a la lista
    public void agregarMascota(Mascotas mascota, LinkedList<Mascotas> listaMascotas);

    // Borra la mascota de la base de datos y de la lista
    public boolean borrarMascota(int idMascota, LinkedList<Mascotas> listaMascotas);

    // Muestra por consola todas las mascotas de la lista
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
