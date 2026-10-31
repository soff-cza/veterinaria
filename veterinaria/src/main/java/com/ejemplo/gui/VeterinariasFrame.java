package com.ejemplo.gui;
import java.awt.BorderLayout;
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

    public VeterinariasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Veterinarias");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // 4 filas: Nombre, Dirección, Abierta, Fecha de Apertura (no hay campo
        // de Consultas porque arranca en 0 y se va incrementando sola con cada reserva)
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

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        btnGuardar.addActionListener(e -> guardarVeterinaria());
        btnVolver.addActionListener(e -> volver());
    }

    private void volver() {
        this.dispose(); // Cierra la ventana actual

        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true); // Abre la ventana principal
    }

    private void guardarVeterinaria() {

        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fechaTexto = txtFechaApertura.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaApertura = LocalDate.parse(fechaTexto, FORMATTER);
            boolean abierta = chkAbierta.isSelected();

            // El servicio arma la Veterinaria con consultas en 0; ese contador
            // se incrementa solo cada vez que se registre una Reserva
            // para esta veterinaria (ver ReservasServiciosImpl.agregarReserva)
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

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDireccion.setText("");
        chkAbierta.setSelected(false);
        txtFechaApertura.setText("");

        txtNombre.requestFocus();
    }

}
