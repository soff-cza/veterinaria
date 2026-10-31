package com.ejemplo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ejemplo.Main;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Urgencias;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.impl.MascotasServiciosImpl;

public class MainFrame extends JFrame {

    private JTextArea areaResultado;
    // Campo de texto para la búsqueda extra pedida por la consigna
    private JTextField txtBusquedaDueño;

    public MainFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Veterinaria");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        areaResultado = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaResultado);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // -------------------------- BOTONES REGISTRAR Y LISTAR MASCOTAS -------------------------
        JButton btnRegistrarMas = new JButton("Registrar Mascota");
        JButton btnListarMas = new JButton("Listar Mascotas");
        panel.add(btnRegistrarMas);
        panel.add(btnListarMas);
        btnRegistrarMas.addActionListener(e -> {
            MascotasFrame ventanaMasFrame = new MascotasFrame();
            ventanaMasFrame.setVisible(true);
            this.dispose();
        });
        btnListarMas.addActionListener(e -> {
            areaResultado.setText("Lista de Mascotas:\n");
            for (Mascotas mascota : Main.listaMascotas) {
                areaResultado.append(mascota.toString() + "\n");
            }
        });

        // -------------------------- BOTONES REGISTRAR Y LISTAR VETERINARIAS -------------------------
        JButton btnRegistrarVet = new JButton("Registrar Veterinaria");
        JButton btnListarVet = new JButton("Listar Veterinarias");
        panel.add(btnRegistrarVet);
        panel.add(btnListarVet);
        btnRegistrarVet.addActionListener(e -> {
            VeterinariasFrame ventanaVetFrame = new VeterinariasFrame();
            ventanaVetFrame.setVisible(true);
            this.dispose();
        });
        btnListarVet.addActionListener(e -> {
            areaResultado.setText("Lista de Veterinarias:\n");
            for (Veterinarias veterinaria : Main.listaVeterinarias) {
                areaResultado.append(veterinaria.toString() + "\n");
            }
        });

        // -------------------------- BOTONES REGISTRAR Y LISTAR RESERVAS -------------------------
        JButton btnRegistrarRes = new JButton("Registrar Reserva");
        JButton btnListarRes = new JButton("Listar Reservas");
        panel.add(btnRegistrarRes);
        panel.add(btnListarRes);
        btnRegistrarRes.addActionListener(e -> {
            ReservasFrame ventanaResFrame = new ReservasFrame();
            ventanaResFrame.setVisible(true);
            this.dispose();
        });
        btnListarRes.addActionListener(e -> {
            areaResultado.setText("Lista de Reservas:\n");
            for (Reservas reserva : Main.listaReservas) {
                areaResultado.append(reserva.toString() + "\n");
            }
        });

        // -------------------------- BOTONES REGISTRAR Y LISTAR URGENCIAS -------------------------
        JButton btnRegistrarUrg = new JButton("Registrar Urgencia");
        JButton btnListarUrg = new JButton("Listar Urgencias");
        panel.add(btnRegistrarUrg);
        panel.add(btnListarUrg);
        btnRegistrarUrg.addActionListener(e -> {
            UrgenciasFrame ventanaUrgFrame = new UrgenciasFrame();
            ventanaUrgFrame.setVisible(true);
            this.dispose();
        });
        btnListarUrg.addActionListener(e -> {
            areaResultado.setText("Lista de Urgencias:\n");
            for (Urgencias urgencia : Main.listaUrgencias) {
                areaResultado.append(urgencia.toString() + "\n");
            }
        });

        // ------------------------- BOTON SALIR -------------------------
        JButton btnSalir = new JButton("Salir");
        panel.add(btnSalir);
        btnSalir.addActionListener(e -> {
            System.out.println("Saliendo del sistema...");
            System.exit(0);
        });

        // ------------------------- BOTON GUARDAR -------------------------
        JButton btnGuardar = new JButton("Guardar Datos");
        panel.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
            Main.guardarDatos(Main.listaMascotas, Main.listaVeterinarias, Main.listaReservas, Main.listaUrgencias);
            areaResultado.setText("Datos guardados exitosamente.");
        });

        // ------------------------- PANEL DE BÚSQUEDA  -------------------------
        // Se ubica en la parte inferior de la ventana (BorderLayout.SOUTH), separado
        // de los botones de registrar/listar porque no pertenece a ninguna entidad
        // en particular, sino que busca sobre la lista de Mascotas por el nombre
        // del dueño (usa el método buscarMascotasPorDueño del servicio)
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar mascota por dueño:"));
        txtBusquedaDueño = new JTextField(15);
        panelBusqueda.add(txtBusquedaDueño);

        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        btnBuscar.addActionListener(e -> buscarPorDueño());

        add(panelBusqueda, BorderLayout.SOUTH);

    }

    private void buscarPorDueño() {

        String nombreDueño = txtBusquedaDueño.getText().trim();

        if (nombreDueño.isEmpty()) {
            areaResultado.setText("Ingrese un nombre de dueño para buscar.\n");
            return;
        }

        MascotasServiciosImpl servicios = new MascotasServiciosImpl();
        var mascotasEncontradas = servicios.buscarMascotasPorDueño(Main.listaMascotas, nombreDueño);

        if (mascotasEncontradas.isEmpty()) {
            areaResultado.setText("No se encontraron mascotas para el dueño: " + nombreDueño + "\n");
        } else {
            areaResultado.setText("Mascotas de " + nombreDueño + ":\n");
            for (Mascotas mascota : mascotasEncontradas) {
                areaResultado.append(mascota.toString() + "\n");
            }
        }
    }

}