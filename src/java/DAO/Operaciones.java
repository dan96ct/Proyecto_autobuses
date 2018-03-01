/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Billete;
import Modelo.Cliente;
import Modelo.Horario;
import Modelo.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dani
 */
public class Operaciones {

    public ArrayList getEstaciones(Connection conn) throws SQLException {
        ArrayList<String> estaciones = new ArrayList<>();
        String ordensql = "SELECT * FROM estaciones;";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        ResultSet rs = PrepStm.executeQuery();
        while (rs.next()) {
            estaciones.add(rs.getString("nombre"));
        }
        return estaciones;
    }

    public void guardarViaje(Connection conn, Billete billete, Cliente cliente) throws SQLException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + cliente);
        String ordensql = "INSERT INTO `clientes` (`nif`, `nombre`, `apellidos`, `email`, `tarjeta`, `fechaCaducidad`) VALUES (?,?,?,?,?,?);";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, cliente.getNif());
        PrepStm.setString(2, cliente.getNombre());
        PrepStm.setString(3, cliente.getApellidos());
        PrepStm.setString(4, cliente.getEmail());
        PrepStm.setString(5, cliente.getTarjeta());
        PrepStm.setString(6, cliente.getFechaCaducidad());
        PrepStm.executeUpdate();

        String ordensqlCliente = "SELECT * FROM clientes WHERE nif=?";
        PreparedStatement PrepStmCliente = conn.prepareStatement(ordensqlCliente);
        PrepStmCliente.setString(1, cliente.getNif());
        ResultSet rsCliente = PrepStmCliente.executeQuery();
        rsCliente.next();
        int idCliente = rsCliente.getInt("id");

        String ordensql2 = "SELECT rutas_horarios.id AS 'idRuta' FROM rutas_horarios,rutas,horarios, estaciones WHERE rutas_horarios.ruta = rutas.id AND horarios.id = rutas_horarios.horaSalida AND estaciones.id = rutas.origen AND estaciones.nombre = ? AND horarios.hora = ?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setString(1, billete.getOrigen());
        PrepStm2.setString(2, billete.getHoraSalida());
        ResultSet rs = PrepStm2.executeQuery();
        rs.next();
        int idRutaHorario = rs.getInt("idRuta");
        System.out.println(idRutaHorario);
        
        String ordensql3 = "INSERT INTO `viajes` (`idRutaHorarios`, `precio`, `idCliente`, `fecha`) VALUES (?,?,?,?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setInt(1, idRutaHorario);
        PrepStm3.setDouble(2, billete.getPrecio());
        PrepStm3.setInt(3, idCliente);
        PrepStm3.setString(4, billete.getDia());
        PrepStm3.executeUpdate();
        
        
        String ordensqlViaje = "SELECT * FROM viajes WHERE idRutaHorarios=? AND precio=? AND idCliente=? AND fecha=?";
        PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
        PrepStmViaje.setInt(1, idRutaHorario);
        PrepStmViaje.setDouble(2, billete.getPrecio());
        PrepStmViaje.setInt(3, idCliente);
        PrepStmViaje.setString(4, billete.getDia());
        ResultSet rsViaje = PrepStmViaje.executeQuery();
        rsViaje.next();
        int idViaje = rsViaje.getInt("id");
        
        ArrayList<Pasajero> arrayPasajeros = new ArrayList<>();
        arrayPasajeros = billete.getArrayPasajeros();

        for (int i = 0; i < arrayPasajeros.size(); i++) {
            String ordensqlViajero = "INSERT INTO `viajeros` (`nombre`, `apellidos`, `asiento`, `idViaje`,`nif`) VALUES (?,?,?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setString(1, arrayPasajeros.get(i).getNombre());
            PrepStmvViajero.setString(2, arrayPasajeros.get(i).getApellido());
            PrepStmvViajero.setInt(3, arrayPasajeros.get(i).getAsiento());
            PrepStmvViajero.setInt(4, idViaje);
            PrepStmvViajero.setString(5, arrayPasajeros.get(i).getIdentificador());
            PrepStmvViajero.executeUpdate();
        }
         
 
        String ordensqlPlazasOcupadas = "INSERT INTO `plazas_ocupadas` (`dia`, `plazasOcupadas`, `rutas_horarios`) VALUES (?, (`plazasOcupadas` + ?), ?);";
        PreparedStatement PrepStmvPlazas = conn.prepareStatement(ordensqlPlazasOcupadas);
        PrepStmvPlazas.setString(1, billete.getDia());
        PrepStmvPlazas.setInt(2, billete.getPersonas());
        PrepStmvPlazas.setInt(3, idRutaHorario);
        PrepStmvPlazas.executeUpdate();
         
    }

    public ArrayList getRutas(Connection conn, String estacion) throws SQLException {
        ArrayList<String> estacionesRuta = new ArrayList<>();
        String ordensql = "SELECT * FROM estaciones WHERE nombre=?";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, estacion);
        ResultSet rs = PrepStm.executeQuery();

        String ordensql2 = "SELECT * FROM rutas WHERE `origen`=?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        rs.next();
        PrepStm2.setString(1, rs.getString("id"));
        ResultSet rs2 = PrepStm2.executeQuery();
        ArrayList<String> estacionesID = new ArrayList<>();
        while (rs2.next()) {
            estacionesID.add(rs2.getString("destino"));
        }

        String ordensql3 = "SELECT * FROM estaciones;";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        ResultSet rs3 = PrepStm3.executeQuery();
        while (rs3.next()) {
            for (int i = 0; i < estacionesID.size(); i++) {
                if (rs3.getString("id").equals(estacionesID.get(i))) {
                    estacionesRuta.add(rs3.getString("nombre"));

                }
            }

        }

        return estacionesRuta;
    }

    public ArrayList getHorariosRuta(Billete billete, Connection conn) throws SQLException {
        ArrayList<Horario> horarios = new ArrayList<>();
        String ordensql = "SELECT * FROM estaciones WHERE nombre = ?;";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, billete.getOrigen());
        ResultSet rs = PrepStm.executeQuery();
        String idOrigen = "";
        while (rs.next()) {
            idOrigen = rs.getString("id");
        }
        String ordensql2 = "SELECT * FROM estaciones WHERE nombre = ?;";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setString(1, billete.getDestino());
        ResultSet rs2 = PrepStm2.executeQuery();
        String idDestino = "";
        while (rs2.next()) {
            idDestino = rs2.getString("id");
        }

        String ordensql3 = "SELECT horarios.hora'hora', rutas_horarios.horaLLegada'horaLLegada',rutas.precio'precio',rutas_horarios.id'idRuta' FROM rutas,rutas_horarios, horarios WHERE rutas.origen = ? AND rutas.destino = ? AND rutas.id = rutas_horarios.ruta AND rutas_horarios.horaSalida = horarios.id;";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setString(1, idOrigen);
        PrepStm3.setString(2, idDestino);
        ResultSet rs3 = PrepStm3.executeQuery();
        while (rs3.next()) {
            Horario horario = new Horario(rs3.getString("hora"), rs3.getString("horaLLegada"), 8, rs3.getDouble("precio"));
            horario.setId(rs3.getInt("idRuta"));
            System.out.println(horario.toString());
            horarios.add(horario);
        }

        return horarios;
    }

}
