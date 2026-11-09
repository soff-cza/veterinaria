package com.ejemplo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ejemplo.servicio.UsuariosServicios;
import com.ejemplo.servicio.impl.UsuariosServiciosImpl;

public class LoginFrame extends JFrame {

     public LoginFrame() {
        setTitle("Ingreso al Sistema de Veterinaria");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblUsuario = new JLabel("Usuario:", SwingConstants.CENTER);
        JTextField txtUsuario = new JTextField();
        JLabel lblClave = new JLabel("Clave:", SwingConstants.CENTER);
        JPasswordField txtClave = new JPasswordField();
        JButton btnIngresar = new JButton("Ingresar");

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblClave);
        panel.add(txtClave);
        panel.add(new JLabel());
        panel.add(btnIngresar);

        add(panel, BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String clave = new String(txtClave.getPassword());

            if (usuario.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese usuario y clave.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Instanciamos la clase que implementa la interfaz
            UsuariosServicios usuariosServicios = new UsuariosServiciosImpl();

            // Invocamos el método a través del objeto/instancia
            boolean ingresoPermitido = usuariosServicios.validarUsuario(usuario, clave);

            // Si el ingreso es permitido, se muestra un mensaje de éxito y se abre la
            // ventana principal
            if (ingresoPermitido) {
                JOptionPane.showMessageDialog(this, "Ingreso exitoso");
                // Se abre la ventana principal de la aplicación
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                this.dispose(); // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}