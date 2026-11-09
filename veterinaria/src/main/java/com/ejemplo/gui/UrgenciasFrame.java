package com.ejemplo.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.ejemplo.Main;
import com.ejemplo.control.ValidarDato;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Urgencias;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.impl.UrgenciasServiciosImpl;

public class UrgenciasFrame extends JFrame {

    // Declaración de componentes
    private JList<Mascotas> listMascotas;
    private JComboBox<Veterinarias> cmbVeterinarias;
    private JTextField txtFechaIngreso;
    private JTextField txtFechaAlta;

    private JButton btnGuardar;
    private JButton btnVolver;

    // Constructor: arma la ventana de registro de urgencias
    public UrgenciasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    // Configura título, tamaño y posición de la ventana
    private void configurarVentana() {
        setTitle("Urgencias");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Crea los campos del formulario, los botones y el nombre de la alumna
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 20, 15));

        panelFormulario.add(new JLabel("Mascotas (Ctrl+clic para varias):", SwingConstants.CENTER));
        // Se usa un JList porque una urgencia puede atender a varias mascotas
        DefaultListModel<Mascotas> mascotasModel = new DefaultListModel<>();
        for (Mascotas m : Main.listaMascotas) {
            mascotasModel.addElement(m);
        }
        listMascotas = new JList<>(mascotasModel);
        listMascotas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panelFormulario.add(new JScrollPane(listMascotas));

        panelFormulario.add(new JLabel("Veterinaria:", SwingConstants.CENTER));
        DefaultComboBoxModel<Veterinarias> veterinariasModel = new DefaultComboBoxModel<>();
        for (Veterinarias v : Main.listaVeterinarias) {
            veterinariasModel.addElement(v);
        }
        cmbVeterinarias = new JComboBox<>(veterinariasModel);
        panelFormulario.add(cmbVeterinarias);

        panelFormulario.add(new JLabel("Fecha Ingreso (dd/MM/yyyy):", SwingConstants.CENTER));
        txtFechaIngreso = new JTextField();
        panelFormulario.add(txtFechaIngreso);

        panelFormulario.add(new JLabel("Fecha Alta (vacío = en curso):", SwingConstants.CENTER));
        txtFechaAlta = new JTextField();
        panelFormulario.add(txtFechaAlta);

        JPanel panelBotones = new JPanel();

        btnGuardar = new JButton("Guardar");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);

        // Panel inferior: botones y nombre de la alumna abajo a la derecha
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelBotones, BorderLayout.CENTER);
        panelSur.add(new JLabel("Alumna: Sofia Carranza ", SwingConstants.RIGHT), BorderLayout.SOUTH);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);

        btnGuardar.addActionListener(e -> guardarUrgencia());
        btnVolver.addActionListener(e -> volver());
    }

    // Cierra esta ventana y vuelve a la ventana principal
    private void volver() {
        this.dispose(); // Cierra la ventana actual
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true); // Abre la ventana principal
    }

    // Valida los datos, vincula las mascotas y la veterinaria elegidas y
    // guarda la urgencia a través del servicio
    private void guardarUrgencia() {

        // getSelectedValuesList() devuelve todas las mascotas marcadas en la lista
        ArrayList<Mascotas> mascotasSeleccionadas = new ArrayList<>(listMascotas.getSelectedValuesList());

        if (mascotasSeleccionadas.isEmpty() || cmbVeterinarias.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una mascota y una veterinaria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fechaIngresoTexto = txtFechaIngreso.getText().trim();
        if (fechaIngresoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha de ingreso es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        restablecerColores();

        if (!ValidarDato.validarFecha(fechaIngresoTexto)) {
            marcarError(txtFechaIngreso, "La fecha de ingreso debe tener el formato dd/MM/yyyy.");
            return;
        }

        String fechaAltaTexto = txtFechaAlta.getText().trim();
        if (!fechaAltaTexto.isEmpty() && !ValidarDato.validarFecha(fechaAltaTexto)) {
            marcarError(txtFechaAlta, "La fecha de alta debe tener el formato dd/MM/yyyy.");
            return;
        }

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaIngreso = LocalDate.parse(fechaIngresoTexto, FORMATTER);

            // La fecha de alta es opcional: si queda vacía la urgencia sigue en curso
            LocalDate fechaAlta = fechaAltaTexto.isEmpty() ? null : LocalDate.parse(fechaAltaTexto, FORMATTER);

            if (fechaAlta != null && fechaAlta.isBefore(fechaIngreso)) {
                marcarError(txtFechaAlta, "La fecha de alta no puede ser anterior a la de ingreso.");
                return;
            }

            Veterinarias veterinaria = (Veterinarias) cmbVeterinarias.getSelectedItem();

            // Aquí se arma la urgencia y se guarda en la base y en la lista
            UrgenciasServiciosImpl servicios = new UrgenciasServiciosImpl();
            Urgencias urgencia = servicios.registrarUrgencia(veterinaria, mascotasSeleccionadas, fechaIngreso, fechaAlta);
            servicios.agregarUrgencia(urgencia, Main.listaUrgencias);

            JOptionPane.showMessageDialog(this, "Urgencia guardada correctamente.");

            limpiarFormulario();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Deja el formulario vacío para cargar un nuevo registro
    private void limpiarFormulario() {
        listMascotas.clearSelection();
        txtFechaIngreso.setText("");
        txtFechaAlta.setText("");
    }

    // Muestra el mensaje de error y pinta de amarillo el campo incorrecto
    private void marcarError(JTextField campo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        campo.setBackground(Color.YELLOW);
        campo.requestFocus();
    }

    // Vuelve a poner en blanco el fondo de los campos de fecha
    private void restablecerColores() {
        txtFechaIngreso.setBackground(Color.WHITE);
        txtFechaAlta.setBackground(Color.WHITE);
    }

}