package com.ejemplo.gui;
import java.awt.BorderLayout;
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

    public UrgenciasFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Urgencias");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 20, 15));

        panelFormulario.add(new JLabel("Mascotas (Ctrl+clic para varias):", SwingConstants.CENTER));
        // A diferencia de los demás formularios, acá usamos un JList en vez de
        // un JComboBox, porque una urgencia puede atender a más de una mascota
        // a la vez (Urgencias.mascotas es un ArrayList<Mascotas>, no un solo objeto)
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

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        btnGuardar.addActionListener(e -> guardarUrgencia());
        btnVolver.addActionListener(e -> volver());
    }

    private void volver() {
        this.dispose(); // Cierra la ventana actual
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true); // Abre la ventana principal
    }

    private void guardarUrgencia() {

        // getSelectedValuesList() devuelve todas las mascotas marcadas en el
        // JList (puede ser una, varias, o ninguna si el usuario no seleccionó)
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

        try {
            final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaIngreso = LocalDate.parse(fechaIngresoTexto, FORMATTER);

            // La fecha de alta es opcional: si el usuario la deja vacía, la
            // urgencia queda "en curso" (ver Urgencias.estaEnCurso, que
            // considera en curso cuando fechaAlta es null)
            String fechaAltaTexto = txtFechaAlta.getText().trim();
            LocalDate fechaAlta = fechaAltaTexto.isEmpty() ? null : LocalDate.parse(fechaAltaTexto, FORMATTER);

            Veterinarias veterinaria = (Veterinarias) cmbVeterinarias.getSelectedItem();

            UrgenciasServiciosImpl servicios = new UrgenciasServiciosImpl();
            Urgencias urgencia = servicios.registrarUrgencia(veterinaria, mascotasSeleccionadas, fechaIngreso, fechaAlta);
            servicios.agregarUrgencia(urgencia, Main.listaUrgencias);

            JOptionPane.showMessageDialog(this, "Urgencia guardada correctamente.");

            limpiarFormulario();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        listMascotas.clearSelection();
        txtFechaIngreso.setText("");
        txtFechaAlta.setText("");
    }

}