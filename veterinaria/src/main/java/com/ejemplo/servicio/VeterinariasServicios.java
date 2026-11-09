package com.ejemplo.servicio;

import java.time.LocalDate;
import java.util.LinkedList;

import com.ejemplo.modelo.Veterinarias;

public interface VeterinariasServicios {

    // Calcula los años que lleva abierta la veterinaria
    public int calcularAntiguedad(Veterinarias veterinaria);

    // Busca una veterinaria por nombre en la lista
    public Veterinarias buscarVeterinariaPorNombre(LinkedList<Veterinarias> listaVeterinarias, String nombre);

    // Arma un objeto Veterinarias con los datos cargados en el formulario
    public Veterinarias registrarVeterinaria(String nombre, String direccion, boolean abierta, LocalDate fechaApertura);

    // Guarda la veterinaria en la base de datos y la agrega a la lista
    public void agregarVeterinaria(Veterinarias veterinaria, LinkedList<Veterinarias> listaVeterinarias);

    // Suma una consulta a la veterinaria y lo guarda en la base
    public void incrementarConsultas(Veterinarias veterinaria);

    // Borra la veterinaria de la base de datos y de la lista
    public boolean borrarVeterinaria(int idVeterinaria, LinkedList<Veterinarias> listaVeterinarias);

    // Muestra por consola todas las veterinarias de la lista
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