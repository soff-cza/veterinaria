package com.ejemplo.servicio.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;

import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.VeterinariasServicios;

public class VeterinariasServiciosImpl implements VeterinariasServicios {

    @Override
    public int calcularAntiguedad(Veterinarias veterinaria) {
        if (veterinaria.getFechaApertura() == null) return 0;
        return Period.between(veterinaria.getFechaApertura(), LocalDate.now()).getYears();
    }

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

    @Override
    public void agregarVeterinaria(Veterinarias veterinaria, LinkedList<Veterinarias> listaVeterinarias) {
        listaVeterinarias.add(veterinaria);
        System.out.println("Veterinaria agregada exitosamente: " + veterinaria.getNombre());
    }

    @Override
    public void incrementarConsultas(Veterinarias veterinaria) {
        veterinaria.setConsultas(veterinaria.getConsultas() + 1);
    }

}