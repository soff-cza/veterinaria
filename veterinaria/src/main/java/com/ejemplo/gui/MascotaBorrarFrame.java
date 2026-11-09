package com.ejemplo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ejemplo.Main;
import com.ejemplo.control.ValidarDato;
import com.ejemplo.servicio.impl.MascotasServiciosImpl;

public class MascotaBorrarFrame extends JFrame {

    // Declaración de componentes
    private JTextField idMascota;
    private JButton btnBorrar;
    private JButton btnVolver;

    // Constructor: arma la ventana de borrado de mascotas
    public MascotaBorrarFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    // Configura título, tamaño y posición de la ventana
    private void configurarVentana() {
        setTitle("Borrar Mascota");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Crea el campo del ID, los botones y el nombre de la alumna
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(1, 2, 20, 25));
        panelFormulario.add(new JLabel("Ingrese Id Mascota a Borrar:", SwingConstants.CENTER));
        idMascota = new JTextField();
        panelFormulario.add(idMascota);

        btnVolver = new JButton("Volver");
        btnBorrar = new JButton("Borrar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnVolver);
        panelBotones.add(btnBorrar);

        // Panel inferior: botones y nombre de la alumna abajo a la derecha
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelBotones, BorderLayout.CENTER);
        panelSur.add(new JLabel("Alumna: Sofia Carranza ", SwingConstants.RIGHT), BorderLayout.SOUTH);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Aqui declaro los eventos de los botones
        btnBorrar.addActionListener(e -> { borrarMascota(); });

        btnVolver.addActionListener(e -> { volver(); });
    }

    // Cierra esta ventana y vuelve a la ventana principal
    private void volver() {
        this.dispose();
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true);
    }

    // Valida el ID ingresado y borra la mascota a través del servicio
    private void borrarMascota() {

        String texto = idMascota.getText().trim();
        idMascota.setBackground(Color.WHITE);

        if (!ValidarDato.validarNumero(texto)) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID numérico mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
            idMascota.setBackground(Color.YELLOW);
            idMascota.requestFocus();
            return;
        }

        int idMasBorrar = Integer.parseInt(texto);

        MascotasServiciosImpl servicios = new MascotasServiciosImpl();
        boolean borrado = servicios.borrarMascota(idMasBorrar, Main.listaMascotas);

        if (borrado) {
            JOptionPane.showMessageDialog(this, "Mascota con ID " + idMasBorrar + " borrada correctamente.");
            idMascota.setText("");
        } else {
            // Si tiene reservas o urgencias, la clave foránea no deja borrarla
            JOptionPane.showMessageDialog(this, "No se pudo borrar la mascota. Verifique el ID o que no tenga reservas o urgencias asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
