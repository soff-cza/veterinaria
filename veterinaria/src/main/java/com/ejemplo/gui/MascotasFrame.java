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
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.servicio.impl.MascotasServiciosImpl;


public class MascotasFrame extends JFrame {

    private JTextField txtNombre;
    private JCheckBox chkVacunada;
    private JTextField txtFechaNacimiento;
    private JTextField txtNombreDueño;
    private JTextField txtTelefonoDueño;
    private JTextField txtEmailDueño;

    private JButton btnGuardar;
    private JButton btnVolver;

    public MascotasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Mascotas");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // 6 filas: Nombre, Vacunada, Fecha Nacimiento, y los 3 datos del dueño
        // (no hay campo de Edad porque se calcula sola a partir de la fecha)
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 20, 12));

        panelFormulario.add(new JLabel("Nombre:", SwingConstants.CENTER));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Vacunada:", SwingConstants.CENTER));
        chkVacunada = new JCheckBox();
        panelFormulario.add(chkVacunada);

        panelFormulario.add(new JLabel("Fecha de Nacimiento (dd/MM/yyyy):", SwingConstants.CENTER));
        txtFechaNacimiento = new JTextField();
        panelFormulario.add(txtFechaNacimiento);

        panelFormulario.add(new JLabel("Nombre del Dueño:", SwingConstants.CENTER));
        txtNombreDueño = new JTextField();
        panelFormulario.add(txtNombreDueño);

        panelFormulario.add(new JLabel("Teléfono del Dueño:", SwingConstants.CENTER));
        txtTelefonoDueño = new JTextField();
        panelFormulario.add(txtTelefonoDueño);

        panelFormulario.add(new JLabel("Email del Dueño:", SwingConstants.CENTER));
        txtEmailDueño = new JTextField();
        panelFormulario.add(txtEmailDueño);

        btnVolver = new JButton("Volver");
        btnGuardar = new JButton("Guardar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVolver);
        panelBotones.add(btnGuardar);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        btnGuardar.addActionListener(e -> guardarMascota());
        btnVolver.addActionListener(e -> volver());
    }

    private void volver() {
        this.dispose();
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true);
    }

    private void guardarMascota() {

        String nombre = txtNombre.getText().trim();
        String fechaTexto = txtFechaNacimiento.getText().trim();
        String nombreDueño = txtNombreDueño.getText().trim();
        String telefonoDueño = txtTelefonoDueño.getText().trim();
        String emailDueño = txtEmailDueño.getText().trim();

        // Solo nombre, fecha y nombre del dueño son obligatorios; teléfono y
        // email quedan como datos opcionales de contacto
        if (nombre.isEmpty() || fechaTexto.isEmpty() || nombreDueño.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre, fecha de nacimiento y nombre del dueño son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNacimiento = LocalDate.parse(fechaTexto, FORMATTER);
            boolean vacunada = chkVacunada.isSelected();

            MascotasServiciosImpl servicios = new MascotasServiciosImpl();
            Mascotas mascota = servicios.registrarMascota();
            servicios.agregarMascota(mascota, Main.listaMascotas);

            JOptionPane.showMessageDialog(this, "Mascota guardada correctamente.");

            limpiarFormulario();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaNacimiento.setText("");
            txtFechaNacimiento.requestFocus();
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        chkVacunada.setSelected(false);
        txtFechaNacimiento.setText("");
        txtNombreDueño.setText("");
        txtTelefonoDueño.setText("");
        txtEmailDueño.setText("");
        txtNombre.requestFocus();
    }

}