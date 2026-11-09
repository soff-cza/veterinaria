package com.ejemplo.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ejemplo.Main;
import com.ejemplo.control.ValidarDato;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.impl.VeterinariasServiciosImpl;

public class VeterinariasFrame extends JFrame {

    // Declaración de componentes
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JCheckBox chkAbierta;
    private JTextField txtFechaApertura;

    private JButton btnGuardar;
    private JButton btnVolver;

    // Constructor: arma la ventana de registro de veterinarias
    public VeterinariasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    // Configura título, tamaño y posición de la ventana
    private void configurarVentana() {
        setTitle("Veterinarias");
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Crea los campos del formulario, los botones y el nombre de la alumna
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // No hay campo de Consultas porque arranca en 0 y suma una con cada reserva
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 20, 15));

        panelFormulario.add(new JLabel("Nombre:", SwingConstants.CENTER));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Dirección:", SwingConstants.CENTER));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Abierta:", SwingConstants.CENTER));
        chkAbierta = new JCheckBox();
        panelFormulario.add(chkAbierta);

        panelFormulario.add(new JLabel("Fecha de Apertura (dd/MM/yyyy):", SwingConstants.CENTER));
        txtFechaApertura = new JTextField();
        panelFormulario.add(txtFechaApertura);

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

        btnGuardar.addActionListener(e -> guardarVeterinaria());
        btnVolver.addActionListener(e -> volver());
    }

    // Cierra esta ventana y vuelve a la ventana principal
    private void volver() {
        this.dispose(); // Cierra la ventana actual

        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true); // Abre la ventana principal
    }

    // Valida los datos con expresiones regulares y guarda la veterinaria a través del servicio
    private void guardarVeterinaria() {

        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fechaTexto = txtFechaApertura.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        restablecerColores();

        if (!ValidarDato.validarNombreApellido(nombre)) {
            marcarError(txtNombre, "El nombre de la veterinaria no es válido (solo letras).");
            return;
        }

        if (!ValidarDato.validarDireccion(direccion)) {
            marcarError(txtDireccion, "La dirección no es válida.");
            return;
        }

        if (!ValidarDato.validarFecha(fechaTexto)) {
            marcarError(txtFechaApertura, "La fecha debe tener el formato dd/MM/yyyy.");
            return;
        }

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaApertura = LocalDate.parse(fechaTexto, FORMATTER);
            boolean abierta = chkAbierta.isSelected();

            // Aquí se arma el objeto veterinaria y se guarda en la base y en la lista
            VeterinariasServiciosImpl servicios = new VeterinariasServiciosImpl();
            Veterinarias veterinaria = servicios.registrarVeterinaria(nombre, direccion, abierta, fechaApertura);
            servicios.agregarVeterinaria(veterinaria, Main.listaVeterinarias);

            JOptionPane.showMessageDialog(this, "Veterinaria guardada correctamente.");

            limpiarFormulario();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaApertura.setText("");
            txtFechaApertura.requestFocus();
        }
    }

    // Deja el formulario vacío para cargar un nuevo registro
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDireccion.setText("");
        chkAbierta.setSelected(false);
        txtFechaApertura.setText("");

        txtNombre.requestFocus();
    }

    // Muestra el mensaje de error y pinta de amarillo el campo incorrecto
    private void marcarError(JTextField campo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        campo.setBackground(Color.YELLOW);
        campo.requestFocus();
    }

    // Vuelve a poner en blanco el fondo de todos los campos
    private void restablecerColores() {
        txtNombre.setBackground(Color.WHITE);
        txtDireccion.setBackground(Color.WHITE);
        txtFechaApertura.setBackground(Color.WHITE);
    }

}
