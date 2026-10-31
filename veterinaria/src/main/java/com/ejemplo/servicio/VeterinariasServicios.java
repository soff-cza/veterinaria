
package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.modelo.Veterinarias;

public interface VeterinariasServicios {

    public int calcularAntiguedad(Veterinarias veterinaria);
    public Veterinarias buscarVeterinariaPorNombre(LinkedList<Veterinarias> listaVeterinarias, String nombre);
    public Veterinarias registrarVeterinaria(String nombre, String direccion, boolean abierta, LocalDate fechaApertura);
    public void agregarVeterinaria(Veterinarias veterinaria, LinkedList<Veterinarias> listaVeterinarias);
    public void incrementarConsultas(Veterinarias veterinaria);

    default void mostrarVeterinarias(LinkedList<Veterinarias> listaVeterinarias) {
        if (listaVeterinarias.isEmpty()) {
            System.out.println("No hay veterinarias registradas.");
        } else {
            System.out.println("Lista de Veterinarias:");
            for (Veterinarias veterinaria : listaVeterinarias) {
                System.out.println(veterinaria.toString());
            }
        }
    }

}