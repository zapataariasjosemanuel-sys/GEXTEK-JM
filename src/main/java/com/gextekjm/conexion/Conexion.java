package com.gextekjm.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
        "jdbc:postgresql://localhost:5432/gextek_jm";
    private static final String USUARIO = "postgres";
    private static final String CLAVE = "Tatiana123";   // cambia por tu contraseña

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL no encontrado", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}