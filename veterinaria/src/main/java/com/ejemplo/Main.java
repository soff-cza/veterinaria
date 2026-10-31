package com.ejemplo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import com.ejemplo.gui.MainFrame;
import com.ejemplo.modelo.Mascotas;
import com.ejemplo.modelo.Reservas;
import com.ejemplo.modelo.Urgencias;
import com.ejemplo.modelo.Veterinarias;


public class Main {

    // Se crea una lista para almacenar las mascotas registradas
    // Se la declara como public static final
    public static final LinkedList<Mascotas> listaMascotas = new LinkedList<>();

    // Se crea una lista para almacenar las veterinarias registradas
    public static final LinkedList<Veterinarias> listaVeterinarias = new LinkedList<>();

    // Se crea una lista para almacenar las reservas realizadas
    public static final LinkedList<Reservas> listaReservas = new LinkedList<>();

    // Se crea una lista para almacenar las urgencias registradas
    public static final LinkedList<Urgencias> listaUrgencias = new LinkedList<>();

    public static void main(String[] args) {

        // Recupero de archivos los datos previamente guardados (si existen)
        leerDatos(listaMascotas, listaVeterinarias, listaReservas, listaUrgencias);

        // Recupero de la base de datos los datos previamente guardados (si existen)
        //MascotasDAO mascotasDAO = new MascotasDAO();
        // listaMascotas.addAll(mascotasDAO.obtenerMascotas());

        // Iniciamos la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {

            MainFrame ventana = new MainFrame();

            ventana.setVisible(true);
        });

    }

    public static void guardarDatos(LinkedList<Mascotas> listaMascotas, LinkedList<Veterinarias> listaVeterinarias,
            LinkedList<Reservas> listaReservas, LinkedList<Urgencias> listaUrgencias) {

        // Carpeta donde se van a guardar los archivos .dat
        String rutaDirectorio = "C:\\datos\\";
        File directorio = new File("C:\\datos");
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crea el directorio si no existe
        }

        // Se abren los 4 flujos de escritura, uno por cada lista, y se cierran solos
        // gracias al try-with-resources
        try (ObjectOutputStream oosMascotas = new ObjectOutputStream(new FileOutputStream(rutaDirectorio + "mascotas.dat"));
                ObjectOutputStream oosVeterinarias = new ObjectOutputStream(new FileOutputStream(rutaDirectorio + "veterinarias.dat"));
                ObjectOutputStream oosReservas = new ObjectOutputStream(new FileOutputStream(rutaDirectorio + "reservas.dat"));
                ObjectOutputStream oosUrgencias = new ObjectOutputStream(new FileOutputStream(rutaDirectorio + "urgencias.dat"))) {

            // writeObject serializa la lista entera (todos sus objetos) al archivo
            oosMascotas.writeObject(listaMascotas);
            oosVeterinarias.writeObject(listaVeterinarias);
            oosReservas.writeObject(listaReservas);
            oosUrgencias.writeObject(listaUrgencias);
            System.out.println("Datos guardados correctamente en: " + rutaDirectorio);

        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // @SuppressWarnings porque readObject() devuelve Object y hay que castearlo
    // a LinkedList<Mascota>, lo cual el compilador no puede verificar en tiempo
    // de compilación (por eso "unchecked")
    @SuppressWarnings("unchecked")
    public static void leerDatos(LinkedList<Mascotas> listaMascotas, LinkedList<Veterinarias> listaVeterinarias,
            LinkedList<Reservas> listaReservas, LinkedList<Urgencias> listaUrgencias) {

        String rutaDirectorio = "C:\\datos\\";

        // Se abren los 4 flujos de lectura, uno por cada archivo .dat
        try (ObjectInputStream oisMascotas = new ObjectInputStream(new FileInputStream(rutaDirectorio + "mascotas.dat"));
                ObjectInputStream oisVeterinarias = new ObjectInputStream(new FileInputStream(rutaDirectorio + "veterinarias.dat"));
                ObjectInputStream oisReservas = new ObjectInputStream(new FileInputStream(rutaDirectorio + "reservas.dat"));
                ObjectInputStream oisUrgencias = new ObjectInputStream(new FileInputStream(rutaDirectorio + "urgencias.dat"))) {

            // readObject reconstruye la lista completa desde el archivo
            LinkedList<Mascotas> mascotasCargadas = (LinkedList<Mascotas>) oisMascotas.readObject();
            LinkedList<Veterinarias> veterinariasCargadas = (LinkedList<Veterinarias>) oisVeterinarias.readObject();
            LinkedList<Reservas> reservasCargadas = (LinkedList<Reservas>) oisReservas.readObject();
            LinkedList<Urgencias> urgenciasCargadas = (LinkedList<Urgencias>) oisUrgencias.readObject();

            // Se vuelcan los datos leídos a las listas reales del sistema
            listaMascotas.clear();
            listaMascotas.addAll(mascotasCargadas);

            listaVeterinarias.clear();
            listaVeterinarias.addAll(veterinariasCargadas);

            listaReservas.clear();
            listaReservas.addAll(reservasCargadas);

            listaUrgencias.clear();
            listaUrgencias.addAll(urgenciasCargadas);

            System.out.println("Datos cargados correctamente desde: " + rutaDirectorio);

        } catch (FileNotFoundException e) {
            // Se utiliza la primera vez que se ejecuta el programa, cuando todavía
            // no existen los archivos .dat
            System.out.println("Archivos de datos no encontrados en " + rutaDirectorio + ". Asegúrese de guardar los datos primero.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer los datos: " + e.getMessage());
        }
    }

}