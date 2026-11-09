package com.ejemplo.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ejemplo.Main;
import com.ejemplo.control.ValidarDato;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Veterinarias;
import com.ejemplo.servicio.impl.ReservasServiciosImpl;


public class ReservasFrame extends JFrame {

    // Declaración de componentes
    private JComboBox<Mascotas> cmbMascotas;
    private JComboBox<Veterinarias> cmbVeterinarias;
    private JTextField txtFechaRes;
    private JTextField txtNumRes;
    private JTextField txtPrecio;
    private JCheckBox chkPagado;

    private JButton btnGuardar;
    private JButton btnVolver;

    // Constructor: arma la ventana de registro de reservas
    public ReservasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    // Configura título, tamaño y posición de la ventana
    private void configurarVentana() {
        setTitle("Reservas");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Crea los campos del formulario, los botones y el nombre de la alumna
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 20, 20));

        panelFormulario.add(new JLabel("Mascota:", SwingConstants.CENTER));
        // Llenamos el combo de mascotas desde Main.listaMascotas
        DefaultComboBoxModel<Mascotas> mascotasModel = new DefaultComboBoxModel<>();
        for (Mascotas m : Main.listaMascotas) {
            mascotasModel.addElement(m);
        }
        cmbMascotas = new JComboBox<>(mascotasModel);
        panelFormulario.add(cmbMascotas);

        panelFormulario.add(new JLabel("Veterinaria:", SwingConstants.CENTER));
        // Llenamos el combo de veterinarias desde Main.listaVeterinarias
        DefaultComboBoxModel<Veterinarias> veterinariasModel = new DefaultComboBoxModel<>();
        for (Veterinarias v : Main.listaVeterinarias) {
            veterinariasModel.addElement(v);
        }
        cmbVeterinarias = new JComboBox<>(veterinariasModel);
        panelFormulario.add(cmbVeterinarias);

        panelFormulario.add(new JLabel("Fecha (dd/MM/yyyy):", SwingConstants.CENTER));
        txtFechaRes = new JTextField();
        panelFormulario.add(txtFechaRes);

        panelFormulario.add(new JLabel("Nro Reserva:", SwingConstants.CENTER));
        txtNumRes = new JTextField();
        panelFormulario.add(txtNumRes);

        panelFormulario.add(new JLabel("Precio:", SwingConstants.CENTER));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Pagado:", SwingConstants.CENTER));
        chkPagado = new JCheckBox();
        panelFormulario.add(chkPagado);

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

        btnGuardar.addActionListener(e -> guardarReserva());
        btnVolver.addActionListener(e -> volver());
    }

    // Cierra esta ventana y vuelve a la ventana principal
    private void volver() {
        this.dispose(); // Cierra la ventana actual
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true); // Abre la ventana principal
    }

    // Valida los datos, vincula la mascota y la veterinaria elegidas en los
    // combos y guarda la reserva a través del servicio
    private void guardarReserva() {

        // Si no hay mascotas o veterinarias cargadas, los combos quedan vacíos
        if (cmbMascotas.getSelectedItem() == null || cmbVeterinarias.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe existir al menos una mascota y una veterinaria registradas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fechaTexto = txtFechaRes.getText().trim();
        String numResTexto = txtNumRes.getText().trim();
        String precioTexto = txtPrecio.getText().trim();

        if (fechaTexto.isEmpty() || numResTexto.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        restablecerColores();

        if (!ValidarDato.validarFecha(fechaTexto)) {
            marcarError(txtFechaRes, "La fecha debe tener el formato dd/MM/yyyy.");
            return;
        }

        if (!ValidarDato.validarNumero(numResTexto)) {
            marcarError(txtNumRes, "El número de reserva debe ser un número mayor a cero.");
            return;
        }

        if (!ValidarDato.validarNumero(precioTexto)) {
            marcarError(txtPrecio, "El precio debe ser un número mayor a cero.");
            return;
        }

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaReserva = LocalDate.parse(fechaTexto, FORMATTER);
            int numeroReserva = Integer.parseInt(numResTexto);
            int precio = Integer.parseInt(precioTexto);
            boolean pagada = chkPagado.isSelected();

            Mascotas mascota = (Mascotas) cmbMascotas.getSelectedItem();
            Veterinarias veterinaria = (Veterinarias) cmbVeterinarias.getSelectedItem();

            // Arma la reserva y se guarda en la base y en la lista
            ReservasServiciosImpl servicios = new ReservasServiciosImpl();
            Reservas reserva = servicios.registrarReserva(mascota, veterinaria, fechaReserva, numeroReserva, precio, pagada);
            servicios.agregarReserva(reserva, Main.listaReservas);

            JOptionPane.showMessageDialog(this, "Reserva guardada correctamente.");

            limpiarFormulario();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaRes.setText("");
            txtFechaRes.requestFocus();
        }
    }

    // Deja el formulario vacío para cargar un nuevo registro
    private void limpiarFormulario() {
        txtNumRes.setText("");
        txtFechaRes.setText("");
        txtPrecio.setText("");
        chkPagado.setSelected(false);
        cmbMascotas.requestFocus();
    }

    // Muestra el mensaje de error y pinta de amarillo el campo incorrecto
    private void marcarError(JTextField campo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        campo.setBackground(Color.YELLOW);
        campo.requestFocus();
    }

    // Vuelve a poner en blanco el fondo de todos los campos
    private void restablecerColores() {
        txtFechaRes.setBackground(Color.WHITE);
        txtNumRes.setBackground(Color.WHITE);
        txtPrecio.setBackground(Color.WHITE);
    }
}