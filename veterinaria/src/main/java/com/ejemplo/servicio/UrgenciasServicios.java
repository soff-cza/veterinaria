package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Urgencias;
import com.ejemplo.modelo.Veterinarias;

public interface UrgenciasServicios {

    public Urgencias registrarUrgencia(Veterinarias veterinaria, ArrayList<Mascotas> mascotas,
            LocalDate fechaIngreso, LocalDate fechaAlta);
    public void agregarUrgencia(Urgencias urgencia, LinkedList<Urgencias> listaUrgencias);
    public boolean internacionEnCurso(Urgencias urgencia);

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