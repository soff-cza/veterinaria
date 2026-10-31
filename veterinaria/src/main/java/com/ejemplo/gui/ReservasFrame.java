package com.ejemplo.gui;
import java.awt.BorderLayout;
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

    public ReservasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Reservas");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

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

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        btnGuardar.addActionListener(e -> guardarReserva());
        btnVolver.addActionListener(e -> volver());
    }

    private void volver() {
        this.dispose(); // Cierra la ventana actual
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true); // Abre la ventana principal
    }

    private void guardarReserva() {

        // A diferencia del original (donde este método estaba vacío), acá
        // implementamos la lógica completa: validar, armar el objeto y
        // agregarlo a la lista a través del servicio.

        // Primero validamos que existan mascotas y veterinarias cargadas,
        // porque sin ellas los combos estarían vacíos y getSelectedItem()
        // devolvería null
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

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaReserva = LocalDate.parse(fechaTexto, FORMATTER);
            int numeroReserva = Integer.parseInt(numResTexto);
            int precio = Integer.parseInt(precioTexto);
            boolean pagada = chkPagado.isSelected();

            Mascotas mascota = (Mascotas) cmbMascotas.getSelectedItem();
            Veterinarias veterinaria = (Veterinarias) cmbVeterinarias.getSelectedItem();

            // El servicio arma la Reservas y, dentro de agregarReserva, incrementa
            // solo el contador de consultas de la veterinaria seleccionada
            ReservasServiciosImpl servicios = new ReservasServiciosImpl();
            Reservas reserva = servicios.registrarReserva(mascota, veterinaria, fechaReserva, numeroReserva, precio, pagada);
            servicios.agregarReserva(reserva, Main.listaReservas);

            JOptionPane.showMessageDialog(this, "Reserva guardada correctamente.");

            limpiarFormulario();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaRes.setText("");
            txtFechaRes.requestFocus();
        } catch (NumberFormatException ex) {
            // Se dispara si "Nro Reserva" o "Precio" no son números (por ejemplo
            // si el usuario escribe letras en esos campos)
            JOptionPane.showMessageDialog(this, "Número de reserva y precio deben ser numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            txtNumRes.setText("");
            txtPrecio.setText("");
        }
    }

    private void limpiarFormulario() {
        txtNumRes.setText("");
        txtFechaRes.setText("");
        txtPrecio.setText("");
        chkPagado.setSelected(false);
        cmbMascotas.requestFocus();
    }
}