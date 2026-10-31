package com.ejemplo.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*Conexion con la base de datos y conexion con el driver*/
public class ConectarBase {
    Connection conexion;
    
    public ConectarBase(){
        this.conexion = null;
    }
    
    public Connection conectar() throws InstantiationException,IllegalAccessException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = Configuracion.BBDD_DRIVER + "://" + Configuracion.BBDD_IP + ":"
                       + Configuracion.BBDD_PUERTO + "/" + Configuracion.BBDD_NOMBRE;
            conexion = DriverManager.getConnection(url, Configuracion.BBDD_USUARIO, Configuracion.BBDD_PASSWORD);
        System.out.println("Conexión establecida.");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error en la conexión " + e);
            }
        
        return conexion;
    }
}
