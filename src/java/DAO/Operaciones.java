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
import Modelo.Viaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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

    public void guardarViaje(Connection conn, Billete billete, Cliente cliente) throws SQLException, Excepcion {
        if (compruebaNifCorreoCliente(conn, cliente)) {
            ValidadorDNI validador = new ValidadorDNI(cliente.getNif());
            if (validador.validar()) {
                if (comprobacionTarjeta(cliente.getTarjetas().get(0).getNumero())) {

                    String ordensql = "INSERT INTO `clientes` (`nif`, `nombre`, `apellidos`, `email`,`password`) VALUES (?,?,?,?, AES_ENCRYPT(?,'sorbete'));";
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

                    String ordensqlTarjeta = "INSERT INTO `tarjetas` (`numero`, `fechaCaducidad`, `tipoTarjeta`, `idCliente`) VALUES (AES_ENCRYPT(?,'sorbete'),?,?,?);";
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

                    String codigo = generarCodigo();

                    String ordensql3 = "INSERT INTO `viajes` (`idViaje`, `precio`, `metodoPago`, `fecha`,`codigo`) VALUES (?,?,?,?,?);";
                    PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
                    PrepStm3.setInt(1, idRutaHorario);
                    double totalPrecio = billete.getPrecio() * billete.getArrayPasajeros().size();
                    PrepStm3.setDouble(2, totalPrecio);
                    PrepStm3.setInt(3, idTarjeta);
                    PrepStm3.setDate(4, java.sql.Date.valueOf(billete.getDia()));
                    PrepStm3.setString(5, codigo);
                    PrepStm3.executeUpdate();

                    String ordensqlViaje = "SELECT * FROM viajes WHERE codigo=?";
                    PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
                    PrepStmViaje.setString(1, codigo);
                    ResultSet rsViaje = PrepStmViaje.executeQuery();
                    int idViaje = 0;
                    while (rsViaje.next()) {
                        idViaje = rsViaje.getInt("id");
                    }
                    System.err.println(">>>>>>>>>>>>>>>>>" + idViaje);
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
                } else {
                    throw new Excepciones.Excepcion("La tarjeta introducida no es correcta");
                }
            } else {
                throw new Excepciones.Excepcion("El NIF introducido no es correcto");
            }
        } else {
            throw new Excepciones.Excepcion("Lo sentimos pero el NIF o el correo introducido ya existe");
        }

    }

    public void compruebaLogin(Connection conn, String email, String pass) throws SQLException, Excepcion {
        String ordensql = "SELECT * FROM clientes WHERE email=? AND AES_DECRYPT(password,'sorbete')=?";
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

        String codigo = generarCodigo();

        String ordensql3 = "INSERT INTO `viajes` (`idViaje`, `precio`, `metodoPago`, `fecha`,`codigo`) VALUES (?,?,?,?,?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setInt(1, idRutaHorario);
        double totalPrecio = billete.getPrecio() * billete.getArrayPasajeros().size();
        PrepStm3.setDouble(2, totalPrecio);
        PrepStm3.setInt(3, idTarjeta);
        PrepStm3.setDate(4, java.sql.Date.valueOf(billete.getDia()));
        PrepStm3.setString(5, codigo);
        PrepStm3.executeUpdate();

        String ordensqlViaje = "SELECT * FROM viajes WHERE codigo=?";
        PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
        PrepStmViaje.setString(1, codigo);
        ResultSet rsViaje = PrepStmViaje.executeQuery();
        int idViaje = 0;
        while (rsViaje.next()) {
            idViaje = rsViaje.getInt("id");
        }

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

    public void guardarViajeNuevaTarjeta(Connection conn, Billete billete, Cliente cliente) throws SQLException, Excepcion {
        if (comprobacionTarjeta(cliente.getTarjetas().get(cliente.getTarjetas().size() - 1).getNumero())) {
            String ordensqlCliente = "SELECT * FROM clientes WHERE nif=?";
            PreparedStatement PrepStmCliente = conn.prepareStatement(ordensqlCliente);
            PrepStmCliente.setString(1, cliente.getNif());
            ResultSet rsCliente = PrepStmCliente.executeQuery();
            rsCliente.next();
            int idCliente = rsCliente.getInt("id");

            String ordensqlTarjeta = "INSERT INTO `tarjetas` (`numero`, `fechaCaducidad`, `tipoTarjeta`, `idCliente`) VALUES (AES_ENCRYPT(?,'sorbete'),?,?,?);";
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

            String codigo = generarCodigo();

            String ordensql3 = "INSERT INTO `viajes` (`idViaje`, `precio`, `metodoPago`, `fecha`,`codigo`) VALUES (?,?,?,?,?);";
            PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
            PrepStm3.setInt(1, idRutaHorario);
            double totalPrecio = billete.getPrecio() * billete.getArrayPasajeros().size();
            PrepStm3.setDouble(2, totalPrecio);
            PrepStm3.setInt(3, idTarjeta);
            PrepStm3.setDate(4, java.sql.Date.valueOf(billete.getDia()));
            PrepStm3.setString(5, codigo);
            PrepStm3.executeUpdate();

            String ordensqlViaje = "SELECT * FROM viajes WHERE codigo=?";
            PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
            PrepStmViaje.setString(1, codigo);
            ResultSet rsViaje = PrepStmViaje.executeQuery();
            int idViaje = 0;
            while (rsViaje.next()) {
                idViaje = rsViaje.getInt("id");
            }

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
        } else {
            throw new Excepciones.Excepcion("La tarjeta introducida no es correcta");
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

    public boolean compruebaNifCorreoCliente(Connection conn, Cliente cliente) throws SQLException {
        boolean validar;
        String ordensql = "SELECT * FROM clientes WHERE nif=?;";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setString(1, cliente.getNif());
        ResultSet rs = PrepStm.executeQuery();
        if (rs.next()) {
            validar = false;
        } else {
            validar = true;
        }

        if (validar) {
            String ordensql2 = "SELECT * FROM clientes WHERE email=?;";
            PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
            PrepStm2.setString(1, cliente.getEmail());
            ResultSet rs2 = PrepStm2.executeQuery();
            if (rs2.next()) {
                validar = false;
            }
        }
        return validar;
    }

    public boolean comprobacionTarjeta(String tarjeta) {
        boolean validar = false;

        int matrizPrincipal[] = new int[16];
        for (int i = 0; i < tarjeta.length(); i++) {
            if (i % 2 == 0) {
                int numero = tarjeta.charAt(i) - '0';
                System.out.println(numero);
                int numeroMultiplicado = numero * 2;
                if (numeroMultiplicado > 9) {
                    String numeroString = String.valueOf(numeroMultiplicado);
                    int numero1 = numeroString.charAt(0) - '0';
                    int numero2 = numeroString.charAt(1) - '0';
                    int numerosSumados = numero1 + numero2;
                    matrizPrincipal[i] = numerosSumados;
                } else {
                    matrizPrincipal[i] = numeroMultiplicado;
                }
            }

        }
        for (int i = 0; i < tarjeta.length(); i++) {
            if (i % 2 != 0) {
                matrizPrincipal[i] = Integer.valueOf(tarjeta.charAt(i) - '0');
            }
        }
        int numeroFinal = 0;
        for (int i = 0; i < matrizPrincipal.length; i++) {
            numeroFinal = +numeroFinal + matrizPrincipal[i];
        }
        if (numeroFinal % 10 == 0) {
            validar = true;
        }
        System.out.println(numeroFinal);
        System.out.print(validar);
        System.out.println(Arrays.toString(matrizPrincipal));

        return validar;
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
        String ordensql = "SELECT * FROM clientes WHERE email=? AND AES_DECRYPT(password,'sorbete')=?;";
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

        String ordensql2 = "SELECT AES_DECRYPT(numero,'sorbete') AS numero, tipoTarjeta, fechaCaducidad, id FROM tarjetas WHERE idCliente=?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordensql2);
        PrepStm2.setInt(1, idCliente);
        ResultSet rs2 = PrepStm2.executeQuery();
        while (rs2.next()) {
            Tarjeta tarjeta = new Tarjeta(rs2.getString("numero"), rs2.getString("tipoTarjeta"), rs2.getString("fechaCaducidad"), rs2.getInt("id"));
            cliente.addTarjeta(tarjeta);
        }

        return cliente;

    }

    public ArrayList getViajesBackup(Connection conn) throws SQLException {
        String ordensql = "SELECT viajes.idViaje AS 'id', viajes.fecha AS 'fecha', horarios.hora AS 'horaSalida', rutas_horarios.horaLLegada AS 'horaLLegada', estaciones.nombre AS 'nombreSalida' FROM viajes,rutas_horarios,rutas, estaciones, horarios WHERE rutas_horarios.id = viajes.idViaje AND rutas_horarios.ruta = rutas.id AND rutas.origen = estaciones.id AND horarios.id = rutas_horarios.horaSalida";
        ArrayList<Viaje> viajes = new ArrayList<>();
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        ResultSet rs = PrepStm.executeQuery();
        while (rs.next()) {
            Viaje viaje = new Viaje(rs.getString("nombreSalida"), rs.getString("horaSalida"), rs.getString("horaLLegada"), rs.getInt("id"), rs.getDate("fecha").toLocalDate());
            boolean validar = true;
            for (int i = 0; i < viajes.size(); i++) {
                if (viajes.get(i).getIdViaje() == rs.getInt("id") && viajes.get(i).getFecha().isEqual(rs.getDate("fecha").toLocalDate()) && viajes.get(i).getHoraSalida().equals(rs.getString("horaSalida"))) {
                    validar = false;
                    break;
                }
            }
            if (validar == true) {
                viajes.add(viaje);
            }

        }
        return viajes;

    }

    public void borrarViaje(int idViaje, String fecha, Connection conn) throws SQLException {
        String ordenSQL = "SELECT * FROM viajes WHERE idViaje=? AND fecha=?";
        PreparedStatement PrepStm = conn.prepareStatement(ordenSQL);
        PrepStm.setInt(1, idViaje);
        PrepStm.setDate(2, java.sql.Date.valueOf(fecha));
        ResultSet rs = PrepStm.executeQuery();
        ArrayList<Integer> idViajes = new ArrayList();
        ArrayList<Integer> idTarjetas = new ArrayList();
        while (rs.next()) {
            idViajes.add(rs.getInt("id"));
            idTarjetas.add(rs.getInt("metodoPago"));
        }

        ArrayList<Integer> idViajeros = new ArrayList();
        for (int i = 0; i < idViajes.size(); i++) {
            String ordenSQL2 = "SELECT * FROM viajeros_viajes WHERE idViaje=?";
            PreparedStatement PrepStm2 = conn.prepareStatement(ordenSQL2);
            PrepStm2.setInt(1, (int) idViajes.get(i));
            ResultSet rs2 = PrepStm2.executeQuery();
            while (rs2.next()) {
                System.out.println("id>>>>>>>>>>>>>" + rs2.getInt("idViajero"));
                idViajeros.add(rs2.getInt("idViajero"));
            }

        }

        String ordenSQL3 = "INSERT INTO `viajes_backup` (`idRuta`, `fecha`) VALUES (?, ?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordenSQL3);
        PrepStm3.setInt(1, idViaje);
        PrepStm3.setDate(2, java.sql.Date.valueOf(fecha));
        PrepStm3.executeUpdate();

        String ordenSQL4 = "SELECT * FROM viajeros";
        PreparedStatement PrepStm4 = conn.prepareStatement(ordenSQL4);
        ResultSet rs3 = PrepStm4.executeQuery();
        ArrayList<Pasajero> viajeros = new ArrayList();
        while (rs3.next()) {
            for (int i = 0; i < idViajeros.size(); i++) {
                if (idViajeros.get(i) == rs3.getInt("id")) {
                    Pasajero pasajero = new Pasajero(rs3.getString("nombre"), rs3.getString("apellidos"), null, rs3.getString("nif"));
                    pasajero.setId(idViajeros.get(i).toString());
                    viajeros.add(pasajero);
                }
            }

        }

        String ordenSQL7 = "SELECT * FROM viajes_backup";
        PreparedStatement PrepStm7 = conn.prepareStatement(ordenSQL7);
        ResultSet rs4 = PrepStm7.executeQuery();
        rs4.last();
        int idViaje_backUp = rs4.getInt("id");

        for (int i = 0; i < viajeros.size(); i++) {
            String ordenSQL5 = "INSERT IGNORE INTO `viajeros_backup` (`nif`, `nombre`, `apellidos`) VALUES (?,?,?);";
            PreparedStatement PrepStm5 = conn.prepareStatement(ordenSQL5);
            PrepStm5.setString(1, viajeros.get(i).getIdentificador());
            PrepStm5.setString(2, viajeros.get(i).getNombre());
            PrepStm5.setString(3, viajeros.get(i).getApellido());
            PrepStm5.executeUpdate();

            String ordenSQL6 = "INSERT INTO `viajeros_viajes_backup` (`idViaje_backup`, `idViajero_backup`) VALUES (?,?);";
            PreparedStatement PrepStm6 = conn.prepareStatement(ordenSQL6);
            PrepStm6.setInt(1, idViaje_backUp);
            PrepStm6.setInt(2, Integer.parseInt(viajeros.get(i).getId()));
            PrepStm6.executeUpdate();
        }

        for (int i = 0; i < idTarjetas.size(); i++) {
            String ordenSQL8 = "INSERT INTO `facturas` (`viajebackup`, `tarjeta`) VALUES (?,?);";
            PreparedStatement PrepStm8 = conn.prepareStatement(ordenSQL8);
            PrepStm8.setInt(1, idViaje_backUp);
            PrepStm8.setInt(2, idTarjetas.get(i));
            PrepStm8.executeUpdate();
        }
        System.out.println("NUmero de id de viajes a borrar >>>>>>>>>>>>>>" + idViajes.size());
        for (int i = 0; i < idViajes.size(); i++) {
            String ordenSQL9 = "DELETE FROM `viajeros_viajes` WHERE `idViaje`=?";
            PreparedStatement PrepStm9 = conn.prepareStatement(ordenSQL9);
            System.out.println("ID del viaje a borrar >>>>>>>>>>>>>><" + idViajes.get(i));
            PrepStm9.setInt(1, idViajes.get(i));
            PrepStm9.executeUpdate();
        }
        String ordenSQL10 = "DELETE FROM `viajes` WHERE `idViaje`=? AND `fecha`=?";
        PreparedStatement PrepStm10 = conn.prepareStatement(ordenSQL10);
        PrepStm10.setInt(1, idViaje);
        PrepStm10.setDate(2, java.sql.Date.valueOf(fecha));
        PrepStm10.executeUpdate();

        for (int i = 0; i < viajeros.size(); i++) {
            boolean validar = true;

            String ordenSQL11 = "SELECT COUNT(*) as 'suma' FROM viajeros_viajes WHERE idViajero=?";
            PreparedStatement PrepStm11 = conn.prepareStatement(ordenSQL11);
            PrepStm11.setString(1, viajeros.get(i).getId());
            ResultSet rs11 = PrepStm11.executeQuery();
            if (rs11.next()) {
                int resultado = rs11.getInt("suma");
                if (resultado > 0) {
                    validar = false;
                }
            }

            if (validar) {
                String ordenSQL12 = "DELETE FROM `viajeros` WHERE `id`=?";
                PreparedStatement PrepStm12 = conn.prepareStatement(ordenSQL12);
                PrepStm12.setString(1, viajeros.get(i).getId());
                PrepStm12.executeUpdate();
            }
        }

        String ordenSQL13 = "DELETE FROM `ocupacion` WHERE `dia`=? AND `rutas_horarios`=?";
        PreparedStatement PrepStm13 = conn.prepareStatement(ordenSQL13);
        PrepStm13.setDate(1, java.sql.Date.valueOf(fecha));
        PrepStm13.setInt(2, idViaje);
        PrepStm13.executeUpdate();
    }

    public String generarCodigo() {
        String cad = "";
        String[] matriz = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "1", "2", "3", "4", "5", "6"};
        for (int i = 0; i < 7; i++) {
            int numAle = (int) (Math.random() * matriz.length + 0);
            cad += matriz[numAle];
        }
        return cad;
    }

}
