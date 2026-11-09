package com.ejemplo.servicio.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;

import com.ejemplo.DAO.VeterinariasDAO;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.VeterinariasServicios;

public class VeterinariasServiciosImpl implements VeterinariasServicios {

    // Calcula los años que lleva abierta la veterinaria
    @Override
    public int calcularAntiguedad(Veterinarias veterinaria) {
        if (veterinaria.getFechaApertura() == null) return 0;
        return Period.between(veterinaria.getFechaApertura(), LocalDate.now()).getYears();
    }

    // Recorre la lista y devuelve la primera veterinaria con ese nombre
    @Override
    public Veterinarias buscarVeterinariaPorNombre(LinkedList<Veterinarias> listaVeterinarias, String nombre) {
        Veterinarias veterinariaEncontrada = null;
        if (!listaVeterinarias.isEmpty()) {
            for (Veterinarias veterinaria : listaVeterinarias) {
                if (veterinaria.getNombre().equalsIgnoreCase(nombre)) {
                    veterinariaEncontrada = veterinaria;
                    break;
                }
            }
        }
        return veterinariaEncontrada;
    }

    // Arma un objeto Veterinarias con los datos del formulario (consultas en 0)
    @Override
    public Veterinarias registrarVeterinaria(String nombre, String direccion, boolean abierta, LocalDate fechaApertura) {
        Veterinarias veterinaria = new Veterinarias();
        veterinaria.setNombre(nombre);
        veterinaria.setDireccion(direccion);
        veterinaria.setConsultas(0); // arranca en 0, se va incrementando con las reservas
        veterinaria.setAbierta(abierta);
        veterinaria.setFechaApertura(fechaApertura);
        return veterinaria;
    }

    // Guarda la veterinaria en la base (a través del DAO) y la agrega a la lista
    @Override
    public void agregarVeterinaria(Veterinarias veterinaria, LinkedList<Veterinarias> listaVeterinarias) {
        int idVeterinaria = new VeterinariasDAO().insertarVeterinaria(veterinaria);
        veterinaria.setIdVeterinaria(idVeterinaria);
        listaVeterinarias.add(veterinaria);
        System.out.println("Veterinaria agregada exitosamente con el id: " + idVeterinaria);
    }

    // Suma una consulta y actualiza la veterinaria en la base
    @Override
    public void incrementarConsultas(Veterinarias veterinaria) {
        veterinaria.setConsultas(veterinaria.getConsultas() + 1);
        new VeterinariasDAO().modificarVeterinaria(veterinaria);
    }

    @Override
    public boolean borrarVeterinaria(int idVeterinaria, LinkedList<Veterinarias> listaVeterinarias) {
        boolean borrado = VeterinariasDAO.borrarVeterinariaPorId(idVeterinaria);
        if (borrado) {
            listaVeterinarias.removeIf(v -> v.getIdVeterinaria() == idVeterinaria);
        }
        return borrado;
    }

}
