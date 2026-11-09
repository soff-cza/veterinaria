package com.ejemplo.control;

public class ValidarDato {

    // Verifica si la cadena del nombre o apellido coincide con el patrón
    // (solo letras, con un espacio entre palabras)
    public static boolean validarNombreApellido(String cadena) {
        return cadena.matches("^[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+([ ]?[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+)*$");
    }

    // Verifica si el teléfono tiene 10 dígitos y es numérico
    public static boolean validarTelefono(String telefono) {
        return telefono.matches("^\\d{9}[0-9]$");
    }

    // Verifica si el correo electrónico tiene un formato válido
    public static boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Verifica que la fecha tenga el formato dd/MM/yyyy
    public static boolean validarFecha(String fecha) {
        return fecha.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    }

    // Verifica que sea un número entero positivo (de 1 a 9 dígitos)
    public static boolean validarNumero(String numero) {
        return numero.matches("^[1-9]\\d{0,8}$");
    }

    // Verifica la dirección: letras, números, espacios, puntos y comas
    public static boolean validarDireccion(String direccion) {
        return direccion.matches("^[A-Za-z0-9áéíóúüñÁÉÍÓÚÜÑ.,° ]{3,150}$");
    }

}