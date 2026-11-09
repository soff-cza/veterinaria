package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Urgencias;
import com.ejemplo.modelo.Veterinarias;

public interface UrgenciasServicios {

    // Arma un objeto Urgencias con la veterinaria y las mascotas elegidas
    public Urgencias registrarUrgencia(Veterinarias veterinaria, ArrayList<Mascotas> mascotas,
            LocalDate fechaIngreso, LocalDate fechaAlta);

    // Guarda la urgencia en la base de datos y la agrega a la lista
    public void agregarUrgencia(Urgencias urgencia, LinkedList<Urgencias> listaUrgencias);

    // Indica si la internación sigue en curso (sin fecha de alta)
    public boolean internacionEnCurso(Urgencias urgencia);

    // Muestra por consola todas las urgencias de la lista
    default void mostrarUrgencias(LinkedList<Urgencias> listaUrgencias) {
        if (listaUrgencias.isEmpty()) {
            System.out.println("No hay urgencias registradas.");
        } else {
            System.out.println("Lista de Urgencias:");
            for (Urgencias urgencia : listaUrgencias) {
                System.out.println(urgencia.toString());
            }
        }
    }

}