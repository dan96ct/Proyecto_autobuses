/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Excepciones.Excepcion;
import Modelo.Billete;
import Modelo.Cliente;
import Modelo.Horario;
import Modelo.Pasajero;
import Modelo.Tarjeta;
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
        String ordensql = "INSERT INTO `clientes` (`nif`, `nombre`, `apellidos`, `email`,`password`) VALUES (?,?,?,?,?);";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, cliente.getNif());
        PrepStm.setString(2, cliente.getNombre());
        PrepStm.setString(3, cliente.getApellidos());
        PrepStm.setString(4, cliente.getEmail());
        PrepStm.setString(5, cliente.getPassword());
        PrepStm.executeUpdate();

        String ordensqlCliente = "SELECT * FROM clientes WHERE nif=?";
        PreparedStatement PrepStmCliente = conn.prepareStatement(ordensqlCliente);
        PrepStmCliente.setString(1, cliente.getNif());
        ResultSet rsCliente = PrepStmCliente.executeQuery();
        rsCliente.next();
        int idCliente = rsCliente.getInt("id");

        String ordensqlTarjeta = "INSERT INTO `tarjetas` (`numero`, `fechaCaducidad`, `tipoTarjeta`, `idCliente`) VALUES (?,?,?,?);";
        PreparedStatement PrepStmTarjeta = conn.prepareStatement(ordensqlTarjeta);
        PrepStmTarjeta.setString(1, cliente.getTarjetas().get(0).getNumero());
        PrepStmTarjeta.setString(2, cliente.getTarjetas().get(0).getFechaCaducidad());
        PrepStmTarjeta.setString(3, cliente.getTarjetas().get(0).getTipo());
        PrepStmTarjeta.setInt(4, idCliente);
        PrepStmTarjeta.executeUpdate();

        String ordensqlTarjeta1 = "SELECT * FROM tarjetas WHERE idCliente=?";
        PreparedStatement PrepStmTarjeta1 = conn.prepareStatement(ordensqlTarjeta1);
        PrepStmTarjeta1.setInt(1, idCliente);
        ResultSet rsTarjeta1 = PrepStmTarjeta1.executeQuery();
        rsTarjeta1.next();
        int idTarjeta = rsTarjeta1.getInt("id");

        String ordensql2 = "SELECT rutas_horarios.id AS 'idRuta' FROM rutas_horarios,rutas,horarios, estaciones WHERE rutas_horarios.ruta = rutas.id AND horarios.id = rutas_horarios.horaSalida AND estaciones.id = rutas.origen AND estaciones.nombre = ? AND horarios.hora = ?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setString(1, billete.getOrigen());
        PrepStm2.setString(2, billete.getHoraSalida());
        ResultSet rs = PrepStm2.executeQuery();
        rs.next();
        int idRutaHorario = rs.getInt("idRuta");
        System.out.println(idRutaHorario);

        String ordensql3 = "INSERT INTO `viajes` (`idViaje`, `precio`, `metodoPago`, `fecha`) VALUES (?,?,?,?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setInt(1, idRutaHorario);
        PrepStm3.setDouble(2, billete.getPrecio());
        PrepStm3.setInt(3, idTarjeta);
        PrepStm3.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        PrepStm3.executeUpdate();

        String ordensqlViaje = "SELECT * FROM viajes WHERE idViaje=? AND precio=? AND metodoPago=? AND fecha=?";
        PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
        PrepStmViaje.setInt(1, idRutaHorario);
        PrepStmViaje.setDouble(2, billete.getPrecio());
        PrepStmViaje.setInt(3, idTarjeta);
        PrepStmViaje.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        ResultSet rsViaje = PrepStmViaje.executeQuery();
        rsViaje.next();
        int idViaje = rsViaje.getInt("id");

        ArrayList<Pasajero> arrayPasajeros = new ArrayList<>();
        arrayPasajeros = billete.getArrayPasajeros();

        for (int i = 0; i < arrayPasajeros.size(); i++) {
            String ordensqlViajero = "INSERT IGNORE INTO `viajeros` (`nombre`, `apellidos`,`nif`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setString(1, arrayPasajeros.get(i).getNombre());
            PrepStmvViajero.setString(2, arrayPasajeros.get(i).getApellido());
            PrepStmvViajero.setString(3, arrayPasajeros.get(i).getIdentificador());
            PrepStmvViajero.executeUpdate();

            int idViajero = compruebaViajero(arrayPasajeros.get(i), conn);
            String ordensqlViajero2 = "INSERT INTO `viajeros_viajes` (`idViaje`, `idViajero`, `asiento`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero2 = conn.prepareStatement(ordensqlViajero2);
            PrepStmvViajero2.setInt(1, idViaje);
            PrepStmvViajero2.setInt(2, idViajero);
            PrepStmvViajero2.setInt(3, arrayPasajeros.get(i).getAsiento());
            PrepStmvViajero2.executeUpdate();

        }

        String ordensqlAsientos = "SELECT * FROM ocupacion WHERE dia=? AND rutas_horarios=?";
        PreparedStatement PrepStmAsientos = conn.prepareStatement(ordensqlAsientos);
        PrepStmAsientos.setDate(1, java.sql.Date.valueOf(billete.getDia()));
        PrepStmAsientos.setInt(2, idRutaHorario);
        ResultSet rsAsientos = PrepStmAsientos.executeQuery();
        if (rsAsientos.next()) {
            int idViaje_asientos = rsAsientos.getInt("id");
            String ordensqlViajero = "UPDATE `ocupacion` SET `plazasOcupadas`=`plazasOcupadas`+? WHERE `id`=?;";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setInt(1, billete.getPersonas());
            PrepStmvViajero.setInt(2, idViaje_asientos);
            PrepStmvViajero.executeUpdate();

        } else {
            String ordensqlViajero = "INSERT INTO `ocupacion` (`dia`, `plazasOcupadas`, `rutas_horarios`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setDate(1, java.sql.Date.valueOf(billete.getDia()));
            PrepStmvViajero.setInt(2, billete.getPersonas());
            PrepStmvViajero.setInt(3, idRutaHorario);
            PrepStmvViajero.executeUpdate();

        }

    }
    public void compruebaLogin(Connection conn, String email, String pass) throws SQLException, Excepcion{
        String ordensql = "SELECT * FROM clientes WHERE email=? AND password=?";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, email);
        PrepStm.setString(2, pass);
        ResultSet rs = PrepStm.executeQuery();
        if (rs.next() == false) {
            throw new Excepciones.Excepcion("Datos incorrectos");
        }
    }
    public void guardarViajeLogin(Connection conn, Billete billete, Cliente cliente) throws SQLException {
        int idTarjeta = 0;
        for (int i = 0; i < cliente.getTarjetas().size(); i++) {
            if (cliente.getTarjetas().get(i).isSeleccionada()) {
                idTarjeta = cliente.getTarjetas().get(i).getId();
            }
        }
        String ordensql2 = "SELECT rutas_horarios.id AS 'idRuta' FROM rutas_horarios,rutas,horarios, estaciones WHERE rutas_horarios.ruta = rutas.id AND horarios.id = rutas_horarios.horaSalida AND estaciones.id = rutas.origen AND estaciones.nombre = ? AND horarios.hora = ?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setString(1, billete.getOrigen());
        PrepStm2.setString(2, billete.getHoraSalida());
        ResultSet rs = PrepStm2.executeQuery();
        rs.next();
        int idRutaHorario = rs.getInt("idRuta");
        System.out.println(idRutaHorario);

        String ordensql3 = "INSERT INTO `viajes` (`idViaje`, `precio`, `metodoPago`, `fecha`) VALUES (?,?,?,?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setInt(1, idRutaHorario);
        PrepStm3.setDouble(2, billete.getPrecio());
        PrepStm3.setInt(3, idTarjeta);
        PrepStm3.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        PrepStm3.executeUpdate();

        String ordensqlViaje = "SELECT * FROM viajes WHERE idViaje=? AND precio=? AND metodoPago=? AND fecha=?";
        PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
        PrepStmViaje.setInt(1, idRutaHorario);
        PrepStmViaje.setDouble(2, billete.getPrecio());
        PrepStmViaje.setInt(3, idTarjeta);
        PrepStmViaje.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        ResultSet rsViaje = PrepStmViaje.executeQuery();
        rsViaje.next();
        int idViaje = rsViaje.getInt("id");

        ArrayList<Pasajero> arrayPasajeros = new ArrayList<>();
        arrayPasajeros = billete.getArrayPasajeros();

        for (int i = 0; i < arrayPasajeros.size(); i++) {
            String ordensqlViajero = "INSERT IGNORE INTO `viajeros` (`nombre`, `apellidos`,`nif`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setString(1, arrayPasajeros.get(i).getNombre());
            PrepStmvViajero.setString(2, arrayPasajeros.get(i).getApellido());
            PrepStmvViajero.setString(3, arrayPasajeros.get(i).getIdentificador());
            PrepStmvViajero.executeUpdate();

            int idViajero = compruebaViajero(arrayPasajeros.get(i), conn);
            String ordensqlViajero2 = "INSERT INTO `viajeros_viajes` (`idViaje`, `idViajero`, `asiento`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero2 = conn.prepareStatement(ordensqlViajero2);
            PrepStmvViajero2.setInt(1, idViaje);
            PrepStmvViajero2.setInt(2, idViajero);
            PrepStmvViajero2.setInt(3, arrayPasajeros.get(i).getAsiento());
            PrepStmvViajero2.executeUpdate();
        }

        String ordensqlAsientos = "SELECT * FROM ocupacion WHERE dia=? AND rutas_horarios=?";
        PreparedStatement PrepStmAsientos = conn.prepareStatement(ordensqlAsientos);
        PrepStmAsientos.setDate(1, java.sql.Date.valueOf(billete.getDia()));
        PrepStmAsientos.setInt(2, idRutaHorario);
        ResultSet rsAsientos = PrepStmAsientos.executeQuery();
        if (rsAsientos.next()) {
            int idViaje_asientos = rsAsientos.getInt("id");
            String ordensqlViajero = "UPDATE `ocupacion` SET `plazasOcupadas`=`plazasOcupadas`+? WHERE `id`=?;";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setInt(1, billete.getPersonas());
            PrepStmvViajero.setInt(2, idViaje_asientos);
            PrepStmvViajero.executeUpdate();

        } else {
            String ordensqlViajero = "INSERT INTO `ocupacion` (`dia`, `plazasOcupadas`, `rutas_horarios`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setDate(1, java.sql.Date.valueOf(billete.getDia()));
            PrepStmvViajero.setInt(2, billete.getPersonas());
            PrepStmvViajero.setInt(3, idRutaHorario);
            PrepStmvViajero.executeUpdate();

        }

    }

    public void guardarViajeNuevaTarjeta(Connection conn, Billete billete, Cliente cliente) throws SQLException {
        String ordensqlCliente = "SELECT * FROM clientes WHERE nif=?";
        PreparedStatement PrepStmCliente = conn.prepareStatement(ordensqlCliente);
        PrepStmCliente.setString(1, cliente.getNif());
        ResultSet rsCliente = PrepStmCliente.executeQuery();
        rsCliente.next();
        int idCliente = rsCliente.getInt("id");

        String ordensqlTarjeta = "INSERT INTO `tarjetas` (`numero`, `fechaCaducidad`, `tipoTarjeta`, `idCliente`) VALUES (?,?,?,?);";
        PreparedStatement PrepStmTarjeta = conn.prepareStatement(ordensqlTarjeta);
        PrepStmTarjeta.setString(1, cliente.getTarjetas().get(cliente.getTarjetas().size() - 1).getNumero());
        PrepStmTarjeta.setString(2, cliente.getTarjetas().get(cliente.getTarjetas().size() - 1).getFechaCaducidad());
        PrepStmTarjeta.setString(3, cliente.getTarjetas().get(cliente.getTarjetas().size() - 1).getTipo());
        PrepStmTarjeta.setInt(4, idCliente);
        PrepStmTarjeta.executeUpdate();

        String ordensqlTarjeta1 = "SELECT * FROM tarjetas WHERE idCliente=?";
        PreparedStatement PrepStmTarjeta1 = conn.prepareStatement(ordensqlTarjeta1);
        PrepStmTarjeta1.setInt(1, idCliente);
        ResultSet rsTarjeta1 = PrepStmTarjeta1.executeQuery();
        rsTarjeta1.next();
        int idTarjeta = rsTarjeta1.getInt("id");

        String ordensql2 = "SELECT rutas_horarios.id AS 'idRuta' FROM rutas_horarios,rutas,horarios, estaciones WHERE rutas_horarios.ruta = rutas.id AND horarios.id = rutas_horarios.horaSalida AND estaciones.id = rutas.origen AND estaciones.nombre = ? AND horarios.hora = ?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setString(1, billete.getOrigen());
        PrepStm2.setString(2, billete.getHoraSalida());
        ResultSet rs = PrepStm2.executeQuery();
        rs.next();
        int idRutaHorario = rs.getInt("idRuta");
        System.out.println(idRutaHorario);

        String ordensql3 = "INSERT INTO `viajes` (`idViaje`, `precio`, `metodoPago`, `fecha`) VALUES (?,?,?,?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setInt(1, idRutaHorario);
        PrepStm3.setDouble(2, billete.getPrecio());
        PrepStm3.setInt(3, idTarjeta);
        PrepStm3.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        PrepStm3.executeUpdate();

        String ordensqlViaje = "SELECT * FROM viajes WHERE idViaje=? AND precio=? AND metodoPago=? AND fecha=?";
        PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
        PrepStmViaje.setInt(1, idRutaHorario);
        PrepStmViaje.setDouble(2, billete.getPrecio());
        PrepStmViaje.setInt(3, idTarjeta);
        PrepStmViaje.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        ResultSet rsViaje = PrepStmViaje.executeQuery();
        rsViaje.next();
        int idViaje = rsViaje.getInt("id");

        ArrayList<Pasajero> arrayPasajeros = new ArrayList<>();
        arrayPasajeros = billete.getArrayPasajeros();

        for (int i = 0; i < arrayPasajeros.size(); i++) {
            String ordensqlViajero = "INSERT IGNORE INTO `viajeros` (`nombre`, `apellidos`,`nif`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setString(1, arrayPasajeros.get(i).getNombre());
            PrepStmvViajero.setString(2, arrayPasajeros.get(i).getApellido());
            PrepStmvViajero.setString(3, arrayPasajeros.get(i).getIdentificador());
            PrepStmvViajero.executeUpdate();

            int idViajero = compruebaViajero(arrayPasajeros.get(i), conn);
            String ordensqlViajero2 = "INSERT INTO `viajeros_viajes` (`idViaje`, `idViajero`, `asiento`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero2 = conn.prepareStatement(ordensqlViajero2);
            PrepStmvViajero2.setInt(1, idViaje);
            PrepStmvViajero2.setInt(2, idViajero);
            PrepStmvViajero2.setInt(3, arrayPasajeros.get(i).getAsiento());
            PrepStmvViajero2.executeUpdate();
        }

        String ordensqlAsientos = "SELECT * FROM ocupacion WHERE dia=? AND rutas_horarios=?";
        PreparedStatement PrepStmAsientos = conn.prepareStatement(ordensqlAsientos);
        PrepStmAsientos.setDate(1, java.sql.Date.valueOf(billete.getDia()));
        PrepStmAsientos.setInt(2, idRutaHorario);
        ResultSet rsAsientos = PrepStmAsientos.executeQuery();
        if (rsAsientos.next()) {
            int idViaje_asientos = rsAsientos.getInt("id");
            String ordensqlViajero = "UPDATE `ocupacion` SET `plazasOcupadas`=`plazasOcupadas`+? WHERE `id`=?;";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setInt(1, billete.getPersonas());
            PrepStmvViajero.setInt(2, idViaje_asientos);
            PrepStmvViajero.executeUpdate();

        } else {
            String ordensqlViajero = "INSERT INTO `ocupacion` (`dia`, `plazasOcupadas`, `rutas_horarios`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero = conn.prepareStatement(ordensqlViajero);
            PrepStmvViajero.setDate(1, java.sql.Date.valueOf(billete.getDia()));
            PrepStmvViajero.setInt(2, billete.getPersonas());
            PrepStmvViajero.setInt(3, idRutaHorario);
            PrepStmvViajero.executeUpdate();

        }

    }

    public int compruebaViajero(Pasajero pasajero, Connection conn) throws SQLException {
        int idViajero = -1;
        String ordensql = "SELECT * FROM viajeros WHERE nif=?";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, pasajero.getIdentificador());
        ResultSet rs = PrepStm.executeQuery();
        if (rs.next()) {
            idViajero = rs.getInt("id");
        }
        return idViajero;

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
            String ordensqlPlazas = "SELECT * FROM ocupacion WHERE rutas_horarios=? AND dia=?;";
            PreparedStatement PrepStmPlazas = conn.prepareStatement(ordensqlPlazas);
            PrepStmPlazas.setInt(1, rs3.getInt("idRuta"));
            PrepStmPlazas.setDate(2, java.sql.Date.valueOf(billete.getDia()));
            ResultSet rsP = PrepStmPlazas.executeQuery();
            int plazasOcupadas = 0;
            while (rsP.next()) {
                plazasOcupadas = rsP.getInt("plazasOcupadas");
            }
            int plazasLibres = 8 - plazasOcupadas;
            Horario horario = new Horario(rs3.getString("hora"), rs3.getString("horaLLegada"), plazasLibres, rs3.getDouble("precio"));
            horario.setId(rs3.getInt("idRuta"));
            System.out.println(horario.toString());
            horarios.add(horario);
        }

        return horarios;
    }

    public ArrayList getAsientosOcupados(Connection conn, Billete billete) throws SQLException {
        System.out.println("BILLETE>>>>>>>>>>>>>>>>>>>>" + billete.toString());
        ArrayList asientosOcupados = new ArrayList();
        String ordensql = "SELECT * FROM viajeros, viajes,viajeros_viajes, rutas_horarios WHERE viajeros_viajes.idViajero = viajeros.id AND viajeros_viajes.idViaje =viajes.id AND  rutas_horarios.id = viajes.idViaje AND rutas_horarios.id = ? AND viajes.fecha = ?;";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setInt(1, billete.getIdViaje());
        PrepStm.setDate(2, java.sql.Date.valueOf(billete.getDia()));
        ResultSet rs = PrepStm.executeQuery();
        while (rs.next()) {
            asientosOcupados.add(rs.getInt("asiento"));
        }

        return asientosOcupados;
    }

    public Cliente compruebarLogin(Connection conn, String email, String pass) throws SQLException {
        String ordensql = "SELECT * FROM clientes WHERE email=? AND password=?";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, email);
        PrepStm.setString(2, pass);
        ResultSet rs = PrepStm.executeQuery();
        int idCliente = 0;
        Cliente cliente = new Cliente();
        if (rs.next()) {
            cliente = new Cliente(rs.getString("nif"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("email"), rs.getString("password"));
            idCliente = rs.getInt("id");
        }

        String ordensql2 = "SELECT * FROM tarjetas WHERE idCliente=?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setInt(1, idCliente);
        ResultSet rs2 = PrepStm2.executeQuery();
        while (rs2.next()) {
            Tarjeta tarjeta = new Tarjeta(rs2.getString("numero"), rs2.getString("tipoTarjeta"), rs2.getString("fechaCaducidad"), rs2.getInt("id"));
            cliente.addTarjeta(tarjeta);
        }

        return cliente;

    }

    public ArrayList getViajes(Connection conn) throws SQLException {
        ArrayList<Billete> viajes = new ArrayList<>();

        String ordensql = "SELECT * FROM tarjetas";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        ResultSet rs = PrepStm.executeQuery();
        while (rs.next()) {
        }

        return viajes;
    }
}
