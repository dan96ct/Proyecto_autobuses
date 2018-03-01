/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Dani
 */
import java.sql.*;


    /**
     * Clase que devuelve una variable Connection de la base de datos que hemos especificado.
     * <PRE> ConexionBBDD nombre = new ConexionBBDD();</PRE>
     * @author Daniel Cebrián
     * @return Connection variable que será utilizada como conexion
     * @exception La excepcion saltará cuando no haya ninguna conexion con la bd    
     * @since incluido desde la version 1.0
     */


public class ConexionBBDD {

    private static ConexionBBDD UnicaConexion = null;
    private Connection Conex;

    private ConexionBBDD() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionUrl = "jdbc:mysql://localhost:3306/bd_autobuses";
        Conex = DriverManager.getConnection(connectionUrl, "root", "root");
    }

    public synchronized static ConexionBBDD GetConexion()
            throws ClassNotFoundException, SQLException {
        if (UnicaConexion == null) {
            UnicaConexion = new ConexionBBDD();
        }
        return UnicaConexion;
    }

    public Connection GetCon() {
        return Conex;
    }

    public void Destroy() throws SQLException {
        Conex.close();
    }
}
