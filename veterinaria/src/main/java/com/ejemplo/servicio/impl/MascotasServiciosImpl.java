package com.ejemplo.servicio.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;

import com.ejemplo.modelo.Mascotas;
import com.ejemplo.servicio.MascotasServicios;

public class MascotasServiciosImpl implements MascotasServicios {

    @Override
    public int calcularEdad(Mascotas mascota) {
        if (mascota.getFechaNacimiento() == null) return 0;
        return Period.between(mascota.getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public Mascotas buscarMascotaPorNombre(LinkedList<Mascotas> listaMascotas, String nombre) {
        Mascotas mascotaEncontrada = null;
        if (!listaMascotas.isEmpty()) {
            for (Mascotas mascota : listaMascotas) {
                if (mascota.getNombre().equalsIgnoreCase(nombre)) {
                    mascotaEncontrada = mascota;
                    break;
                }
            }
        }
        return mascotaEncontrada;
    }

    @Override
    public LinkedList<Mascotas> buscarMascotasPorDueño(LinkedList<Mascotas> listaMascotas, String nombreDueño) {
        // A diferencia de buscarMascotaPorNombre (que devuelve una sola y corta
        // con break), acá devolvemos TODAS las coincidencias, porque un mismo
        // dueño puede tener más de una mascota registrada
        LinkedList<Mascotas> encontradas = new LinkedList<>();
        for (Mascotas mascota : listaMascotas) {
            if (mascota.getNombreDueño() != null && mascota.getNombreDueño().equalsIgnoreCase(nombreDueño)) {
                encontradas.add(mascota);
            }
        }
        return encontradas;
    }

    @Override
    public Mascotas registrarMascota() {
        Mascotas mascota = new Mascotas();
        mascota.setNombre(nombre);
        mascota.setEdad(calcularEdad(mascota));
        mascota.setVacunada(vacunada);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setNombreDueño(nombreDueño);
        mascota.setTelefonoDueño(telefonoDueño);
        mascota.setEmailDueño(emailDueño);
        return mascota;
    }

    @Override
    public void agregarMascota(Mascotas mascota, LinkedList<Mascotas> listaMascotas) {
        listaMascotas.add(mascota);
        System.out.println("Mascota agregada exitosamente: " + mascota.getNombre());
    }

}