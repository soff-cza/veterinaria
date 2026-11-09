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
import com.ejemplo.servicio.impl.VeterinariasServiciosImpl;

public class VeterinariaBorrarFrame extends JFrame {

    // Declaración de componentes
    private JTextField idVeterinaria;
    private JButton btnBorrar;
    private JButton btnVolver;

    // Constructor: arma la ventana de borrado de veterinarias
    public VeterinariaBorrarFrame() {
        configurarVentana();
        inicializarComponentes();
    }

    // Configura título, tamaño y posición de la ventana
    private void configurarVentana() {
        setTitle("Borrar Veterinaria");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Crea el campo del ID, los botones y el nombre de la alumna
    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(1, 2, 20, 25));
        panelFormulario.add(new JLabel("Ingrese Id Veterinaria a Borrar:", SwingConstants.CENTER));
        idVeterinaria = new JTextField();
        panelFormulario.add(idVeterinaria);

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
        btnBorrar.addActionListener(e -> { borrarVeterinaria(); });

        btnVolver.addActionListener(e -> { volver(); });
    }

    // Cierra esta ventana y vuelve a la ventana principal
    private void volver() {
        this.dispose();
        MainFrame ventanaPrincipal = new MainFrame();
        ventanaPrincipal.setVisible(true);
    }

    // Valida el ID ingresado y borra la veterinaria a través del servicio
    private void borrarVeterinaria() {

        String texto = idVeterinaria.getText().trim();
        idVeterinaria.setBackground(Color.WHITE);

        if (!ValidarDato.validarNumero(texto)) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID numérico mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
            idVeterinaria.setBackground(Color.YELLOW);
            idVeterinaria.requestFocus();
            return;
        }

        int idVetBorrar = Integer.parseInt(texto);

        VeterinariasServiciosImpl servicios = new VeterinariasServiciosImpl();
        boolean borrado = servicios.borrarVeterinaria(idVetBorrar, Main.listaVeterinarias);

        if (borrado) {
            JOptionPane.showMessageDialog(this, "Veterinaria con ID " + idVetBorrar + " borrada correctamente.");
            idVeterinaria.setText("");
        } else {
            // Si tiene reservas o urgencias, la clave foránea no deja borrarla
            JOptionPane.showMessageDialog(this, "No se pudo borrar la veterinaria. Verifique el ID o que no tenga reservas o urgencias asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
