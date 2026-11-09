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
import javax.swing.SwingConstants;

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

    // Constructor: arma la ventana principal del sistema
    public MainFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    // Configura título, tamaño y posición de la ventana
    private void configurarVentana() {
        setTitle("Sistema de Veterinaria");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Crea los botones, el área de resultados y el panel de búsqueda
    private void inicializarComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        areaResultado = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaResultado);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // -------------------------- BOTONES REGISTRAR, LISTAR Y BORRAR MASCOTAS -------------------------
        JButton btnRegistrarMas = new JButton("Registrar Mascota");
        JButton btnListarMas = new JButton("Listar Mascotas");
        JButton btnBorrarMas = new JButton("Borrar Mascota");
        panel.add(btnRegistrarMas);
        panel.add(btnListarMas);
        panel.add(btnBorrarMas);
        // Algoritmo para abrir la ventana de registro de mascotas
        btnRegistrarMas.addActionListener(e -> {
            MascotasFrame ventanaMasFrame = new MascotasFrame();
            ventanaMasFrame.setVisible(true);
            this.dispose();
        });
        // Algoritmo para mostrar la lista de mascotas
        btnListarMas.addActionListener(e -> {
            areaResultado.setText("Lista de Mascotas:\n");
            for (Mascotas mascota : Main.listaMascotas) {
                areaResultado.append(mascota.toString() + "\n");
            }
        });

        // Algoritmo para abrir la ventana de borrado de mascotas
        btnBorrarMas.addActionListener(e -> {
            MascotaBorrarFrame ventanaBorrarMasFrame = new MascotaBorrarFrame();
            ventanaBorrarMasFrame.setVisible(true);
            this.dispose();
        });

        // -------------------------- BOTONES REGISTRAR, LISTAR Y BORRAR VETERINARIAS -------------------------
        JButton btnRegistrarVet = new JButton("Registrar Veterinaria");
        JButton btnListarVet = new JButton("Listar Veterinarias");
        JButton btnBorrarVet = new JButton("Borrar Veterinaria");
        panel.add(btnRegistrarVet);
        panel.add(btnListarVet);
        panel.add(btnBorrarVet);
        // Algoritmo para abrir la ventana de registro de veterinarias
        btnRegistrarVet.addActionListener(e -> {
            VeterinariasFrame ventanaVetFrame = new VeterinariasFrame();
            ventanaVetFrame.setVisible(true);
            this.dispose();
        });
        // Algoritmo para mostrar la lista de veterinarias
        btnListarVet.addActionListener(e -> {
            areaResultado.setText("Lista de Veterinarias:\n");
            for (Veterinarias veterinaria : Main.listaVeterinarias) {
                areaResultado.append(veterinaria.toString() + "\n");
            }
        });

        // Algoritmo para abrir la ventana de borrado de veterinarias
        btnBorrarVet.addActionListener(e -> {
            VeterinariaBorrarFrame ventanaBorrarVetFrame = new VeterinariaBorrarFrame();
            ventanaBorrarVetFrame.setVisible(true);
            this.dispose();
        });

        // -------------------------- BOTONES REGISTRAR Y LISTAR RESERVAS -------------------------
        JButton btnRegistrarRes = new JButton("Registrar Reserva");
        JButton btnListarRes = new JButton("Listar Reservas");
        panel.add(btnRegistrarRes);
        panel.add(btnListarRes);
        // Algoritmo para abrir la ventana de registro de reservas
        btnRegistrarRes.addActionListener(e -> {
            ReservasFrame ventanaResFrame = new ReservasFrame();
            ventanaResFrame.setVisible(true);
            this.dispose();
        });
        // Algoritmo para mostrar la lista de reservas
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
        // Algoritmo para abrir la ventana de registro de urgencias
        btnRegistrarUrg.addActionListener(e -> {
            UrgenciasFrame ventanaUrgFrame = new UrgenciasFrame();
            ventanaUrgFrame.setVisible(true);
            this.dispose();
        });
        // Algoritmo para mostrar la lista de urgencias
        btnListarUrg.addActionListener(e -> {
            areaResultado.setText("Lista de Urgencias:\n");
            for (Urgencias urgencia : Main.listaUrgencias) {
                areaResultado.append(urgencia.toString() + "\n");
            }
        });

        // ------------------------- BOTON SALIR -------------------------
        JButton btnSalir = new JButton("Salir");
        panel.add(btnSalir);
        // Algoritmo para cerrar la sesión y volver al login
        btnSalir.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            this.dispose();
        });

        // ------------------------- BOTON GUARDAR -------------------------
        JButton btnGuardar = new JButton("Guardar Datos");
        panel.add(btnGuardar);
        // Algoritmo para guardar una copia de los datos en archivos .dat
        btnGuardar.addActionListener(e -> {
            Main.guardarDatos(Main.listaMascotas, Main.listaVeterinarias, Main.listaReservas, Main.listaUrgencias);
            areaResultado.setText("Datos guardados exitosamente.");
        });

        // ------------------------- PANEL DE BÚSQUEDA  -------------------------
        // Busca en la lista de mascotas por el nombre del dueño
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar mascota por dueño:"));
        txtBusquedaDueño = new JTextField(15);
        panelBusqueda.add(txtBusquedaDueño);

        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(btnBuscar);
        btnBuscar.addActionListener(e -> buscarPorDueño());

        // Panel inferior: búsqueda y nombre de la alumna abajo a la derecha
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelBusqueda, BorderLayout.CENTER);
        panelSur.add(new JLabel("Alumna: Sofia Carranza ", SwingConstants.RIGHT), BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

    }

    // Busca las mascotas del dueño ingresado y las muestra en el área de resultados
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