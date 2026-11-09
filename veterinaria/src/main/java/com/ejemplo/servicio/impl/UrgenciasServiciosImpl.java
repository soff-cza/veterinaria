package com.ejemplo.servicio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import com.ejemplo.DAO.UrgenciasDAO;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Urgencias;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.UrgenciasServicios;

public class UrgenciasServiciosImpl implements UrgenciasServicios {

    @Override
    public Urgencias registrarUrgencia(Veterinarias veterinaria, ArrayList<Mascotas> mascotas,
            LocalDate fechaIngreso, LocalDate fechaAlta) {
        Urgencias urgencia = new Urgencias();
        urgencia.setVeterinaria(veterinaria);
        urgencia.setMascotas(mascotas);
        urgencia.setFechaIngreso(fechaIngreso);
        urgencia.setFechaAlta(fechaAlta);
        return urgencia;
    }

    @Override
    public void agregarUrgencia(Urgencias urgencia, LinkedList<Urgencias> listaUrgencias) {
        int idUrgencia = new UrgenciasDAO().insertarUrgencia(urgencia);
        urgencia.setIdUrgencia(idUrgencia);
        listaUrgencias.add(urgencia);
        System.out.println("Urgencia agregada exitosamente con el id: " + idUrgencia);
    }

    @Override
    public boolean internacionEnCurso(Urgencias urgencia) {
        return urgencia.getFechaAlta() == null;
    }

}
