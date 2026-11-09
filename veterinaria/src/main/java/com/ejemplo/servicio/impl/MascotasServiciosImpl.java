package com.ejemplo.servicio.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;

import com.ejemplo.DAO.MascotasDAO;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.servicio.MascotasServicios;

public class MascotasServiciosImpl implements MascotasServicios {

    // Calcula la edad de la mascota a partir de su fecha de nacimiento
    @Override
    public int calcularEdad(Mascotas mascota) {
        if (mascota.getFechaNacimiento() == null) return 0;
        return Period.between(mascota.getFechaNacimiento(), LocalDate.now()).getYears();
    }

    // Recorre la lista y devuelve la primera mascota con ese nombre
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

    // Recorre la lista y devuelve todas las mascotas del dueño indicado
    @Override
    public LinkedList<Mascotas> buscarMascotasPorDueño(LinkedList<Mascotas> listaMascotas, String nombreDueño) {
        LinkedList<Mascotas> encontradas = new LinkedList<>();
        for (Mascotas mascota : listaMascotas) {
            if (mascota.getNombreDueño() != null && mascota.getNombreDueño().equalsIgnoreCase(nombreDueño)) {
                encontradas.add(mascota);
            }
        }
        return encontradas;
    }

    // Arma un objeto Mascotas con los datos del formulario. La edad se calcula sola
    @Override
    public Mascotas registrarMascota(String nombre, boolean vacunada, LocalDate fechaNacimiento,
            String nombreDueño, String telefonoDueño, String emailDueño) {
        Mascotas mascota = new Mascotas();
        mascota.setNombre(nombre);
        mascota.setVacunada(vacunada);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setEdad(calcularEdad(mascota));
        mascota.setNombreDueño(nombreDueño);
        mascota.setTelefonoDueño(telefonoDueño);
        mascota.setEmailDueño(emailDueño);
        return mascota;
    }

    // Guarda la mascota en la base (a través del DAO) y la agrega a la lista
    @Override
    public void agregarMascota(Mascotas mascota, LinkedList<Mascotas> listaMascotas) {
        int idMascota = new MascotasDAO().insertarMascota(mascota);
        mascota.setIdMascota(idMascota); // Establece el ID de la mascota después de la inserción
        listaMascotas.add(mascota);
        System.out.println("Mascota agregada exitosamente con el id: " + idMascota);
    }

    // Borra la mascota de la base (a través del DAO) y, si se pudo, de la lista
    @Override
    public boolean borrarMascota(int idMascota, LinkedList<Mascotas> listaMascotas) {
        boolean borrado = MascotasDAO.borrarMascotaPorId(idMascota);
        if (borrado) {
            listaMascotas.removeIf(m -> m.getIdMascota() == idMascota);
        }
        return borrado;
    }

}