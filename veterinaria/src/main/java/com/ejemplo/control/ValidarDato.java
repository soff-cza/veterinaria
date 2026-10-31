package com.ejemplo.control;

public class ValidarDato {

        public static boolean validarNombreApellido(String cadena) {

        // Verifica si la cadena del nombre o apellido coincide con el patrón

        return cadena.matches("^[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+([ ]?[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+)*$");

    }

    public static boolean validarDNI(String dni) {

        // Verifica si el DNI tiene 8 dígitos y es numérico

        return dni.matches("^\\d{7}[0-9]$");

    }

    public static boolean validarTelefono(String telefono) {

        // Verifica si el teléfono tiene 10 dígitos

        return telefono.matches("^\\d{9}[0-9]$");

    }

    public static boolean validarEmail(String email) {

        // Verifica si el correo electrónico tiene un formato válido

        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    }



}