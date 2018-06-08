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

                    String codigo = generarCodigo();

                    String ordensql3 = "INSERT INTO `reservas` (`id_viaje`, `id_tarjeta`, `precio`, `codigo`) VALUES (?,?,?,?);";
                    PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
                    PrepStm3.setInt(1, billete.getIdViaje());
                    double totalPrecio = billete.getPrecio() * billete.getArrayPasajeros().size();
                    PrepStm3.setDouble(2, idTarjeta);
                    PrepStm3.setDouble(3, totalPrecio);
                    PrepStm3.setString(4, codigo);
                    PrepStm3.executeUpdate();

                    String ordensqlViaje = "SELECT * FROM reservas WHERE codigo=?";
                    PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
                    PrepStmViaje.setString(1, codigo);
                    ResultSet rsViaje = PrepStmViaje.executeQuery();
                    int idReserva = 0;
                    while (rsViaje.next()) {
                        idReserva = rsViaje.getInt("id");
                    }
                    System.err.println(">>>>>>>>>>>>>>>>>" + idReserva);
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
                        String ordensqlViajero2 = "INSERT INTO `ocupacion` (`id_reserva`, `id_viajero`, `asiento`) VALUES (?,?,?);";
                        PreparedStatement PrepStmvViajero2 = conn.prepareStatement(ordensqlViajero2);
                        PrepStmvViajero2.setInt(1, idReserva);
                        PrepStmvViajero2.setInt(2, idViajero);
                        PrepStmvViajero2.setInt(3, arrayPasajeros.get(i).getAsiento());
                        PrepStmvViajero2.executeUpdate();

                    }
                    String ordensql4 = "UPDATE `viajes` SET `plazasOcupadas`=`plazasOcupadas`+? WHERE `id`=?;";
                    PreparedStatement PrepSt4 = conn.prepareStatement(ordensql4);
                    PrepSt4.setInt(1, billete.getPersonas());
                    PrepSt4.setInt(2, billete.getIdViaje());
                    PrepSt4.executeUpdate();

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

        String codigo = generarCodigo();

        String ordensql3 = "INSERT INTO `reservas` (`id_viaje`, `id_tarjeta`, `precio`, `codigo`) VALUES (?,?,?,?);";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setInt(1, billete.getIdViaje());
        double totalPrecio = billete.getPrecio() * billete.getArrayPasajeros().size();
        PrepStm3.setDouble(2, idTarjeta);
        PrepStm3.setDouble(3, totalPrecio);
        PrepStm3.setString(4, codigo);
        PrepStm3.executeUpdate();

        String ordensqlViaje = "SELECT * FROM reservas WHERE codigo=?";
        PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
        PrepStmViaje.setString(1, codigo);
        ResultSet rsViaje = PrepStmViaje.executeQuery();
        int idReserva = 0;
        while (rsViaje.next()) {
            idReserva = rsViaje.getInt("id");
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
            String ordensqlViajero2 = "INSERT INTO `ocupacion` (`id_reserva`, `id_viajero`, `asiento`) VALUES (?,?,?);";
            PreparedStatement PrepStmvViajero2 = conn.prepareStatement(ordensqlViajero2);
            PrepStmvViajero2.setInt(1, idReserva);
            PrepStmvViajero2.setInt(2, idViajero);
            PrepStmvViajero2.setInt(3, arrayPasajeros.get(i).getAsiento());
            PrepStmvViajero2.executeUpdate();

        }
        String ordensql4 = "UPDATE `viajes` SET `plazasOcupadas`=`plazasOcupadas`+? WHERE `id`=?;";
        PreparedStatement PrepSt4 = conn.prepareStatement(ordensql4);
        PrepSt4.setInt(1, billete.getPersonas());
        PrepSt4.setInt(2, billete.getIdViaje());
        PrepSt4.executeUpdate();
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

            String codigo = generarCodigo();

            String ordensql3 = "INSERT INTO `reservas` (`id_viaje`, `id_tarjeta`, `precio`, `codigo`) VALUES (?,?,?,?);";
            PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
            PrepStm3.setInt(1, billete.getIdViaje());
            double totalPrecio = billete.getPrecio() * billete.getArrayPasajeros().size();
            PrepStm3.setDouble(2, idTarjeta);
            PrepStm3.setDouble(3, totalPrecio);
            PrepStm3.setString(4, codigo);
            PrepStm3.executeUpdate();

            String ordensqlViaje = "SELECT * FROM reservas WHERE codigo=?";
            PreparedStatement PrepStmViaje = conn.prepareStatement(ordensqlViaje);
            PrepStmViaje.setString(1, codigo);
            ResultSet rsViaje = PrepStmViaje.executeQuery();
            int idReserva = 0;
            while (rsViaje.next()) {
                idReserva = rsViaje.getInt("id");
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
                String ordensqlViajero2 = "INSERT INTO `ocupacion` (`id_reserva`, `id_viajero`, `asiento`) VALUES (?,?,?);";
                PreparedStatement PrepStmvViajero2 = conn.prepareStatement(ordensqlViajero2);
                PrepStmvViajero2.setInt(1, idReserva);
                PrepStmvViajero2.setInt(2, idViajero);
                PrepStmvViajero2.setInt(3, arrayPasajeros.get(i).getAsiento());
                PrepStmvViajero2.executeUpdate();

            }
            String ordensql4 = "UPDATE `viajes` SET `plazasOcupadas`=`plazasOcupadas`+? WHERE `id`=?;";
            PreparedStatement PrepSt4 = conn.prepareStatement(ordensql4);
            PrepSt4.setInt(1, billete.getPersonas());
            PrepSt4.setInt(2, billete.getIdViaje());
            PrepSt4.executeUpdate();
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

        String ordensql3 = "SELECT rutas_horarios.horaSalida'hora', rutas_horarios.horaLLegada'horaLLegada',rutas.precio'precio',rutas_horarios.id'idRuta' FROM rutas,rutas_horarios,viajes WHERE rutas.origen = ? AND rutas.destino = ? AND rutas.id = rutas_horarios.ruta AND viajes.id_rutas_horarios = rutas_horarios.id AND viajes.fecha = ?;";
        PreparedStatement PrepStm3 = conn.prepareStatement(ordensql3);
        PrepStm3.setString(1, idOrigen);
        PrepStm3.setString(2, idDestino);
        PrepStm3.setDate(3, java.sql.Date.valueOf(billete.getDia()));
        ResultSet rs3 = PrepStm3.executeQuery();
        int idViaje = 0;
        while (rs3.next()) {
            String ordensqlPlazas = "SELECT * FROM viajes WHERE id_rutas_horarios=? AND fecha=?;";
            PreparedStatement PrepStmPlazas = conn.prepareStatement(ordensqlPlazas);
            PrepStmPlazas.setInt(1, rs3.getInt("idRuta"));
            PrepStmPlazas.setDate(2, java.sql.Date.valueOf(billete.getDia()));
            ResultSet rsP = PrepStmPlazas.executeQuery();
            int plazasOcupadas = 0;
            while (rsP.next()) {
                plazasOcupadas = rsP.getInt("plazasOcupadas");
                idViaje = rsP.getInt("id");
            }
            int plazasLibres = 8 - plazasOcupadas;
            Horario horario = new Horario(rs3.getString("hora"), rs3.getString("horaLLegada"), plazasLibres, rs3.getDouble("precio"));
            horario.setId(idViaje);
            System.out.println(horario.toString());
            horarios.add(horario);
        }

        return horarios;
    }

    public ArrayList getAsientosOcupados(Connection conn, Billete billete) throws SQLException {
        System.out.println("BILLETE>>>>>>>>>>>>>>>>>>>>" + billete.toString());
        ArrayList asientosOcupados = new ArrayList();
        String ordensql = "SELECT * FROM ocupacion, reservas, viajes WHERE viajes.id = reservas.id_viaje AND reservas.id = ocupacion.id_reserva and viajes.id = ?;";
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setInt(1, billete.getIdViaje());
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

    public ArrayList getViajesBackup(Connection conn, String origen, String destino, LocalDate fecha) throws SQLException {
        String ordensql = "SELECT viajes.id AS 'idViajes', estaciones.nombre AS 'nombreEstacion', rutas_horarios.horaSalida AS 'horaSalida', rutas_horarios.horaLLegada AS 'horaLLegada', viajes.fecha AS 'fecha'   FROM viajes, rutas_horarios, rutas, estaciones WHERE rutas_horarios.id = viajes.id_rutas_horarios AND rutas.id = rutas_horarios.ruta AND estaciones.id = rutas.origen AND viajes.fecha = ? AND estaciones.nombre = ?;";
        ArrayList<Viaje> viajes = new ArrayList<>();
        PreparedStatement PrepStm = conn.prepareStatement(ordensql);
        PrepStm.setDate(1, java.sql.Date.valueOf(fecha));
        PrepStm.setString(2, origen);
        ResultSet rs = PrepStm.executeQuery();
        while (rs.next()) {
            Viaje viaje = new Viaje(rs.getString("nombreEstacion"), rs.getString("horaSalida"), rs.getString("horaLLegada"), rs.getInt("idViajes"), rs.getDate("fecha").toLocalDate());
            viajes.add(viaje);

        }
        return viajes;

    }

    public void borrarViaje(int idViaje, String fecha, Connection conn) throws SQLException {
        String ordenSQL = "SELECT * FROM viajes WHERE id=?";
        PreparedStatement PrepStm = conn.prepareStatement(ordenSQL);
        PrepStm.setInt(1, idViaje);
        ResultSet rs = PrepStm.executeQuery();
        while (rs.next()) {
            String ordenSQLInsertViaje = "INSERT INTO `backup_viajes` (`id_rutas_horarios`, `fecha`, `plazasOcupadas`) VALUES (?,?,?);";
            PreparedStatement PrepStInsertViaje = conn.prepareStatement(ordenSQLInsertViaje);
            PrepStInsertViaje.setInt(1, rs.getInt("id_rutas_horarios"));
            PrepStInsertViaje.setDate(2, rs.getDate("fecha"));
            PrepStInsertViaje.setInt(3, rs.getInt("plazasOcupadas"));

            PrepStInsertViaje.executeUpdate();
        }

        String ordenSQLIdViajeBackUp = "SELECT * FROM backup_viajes";
        PreparedStatement PrepStmIdViajeBackUp = conn.prepareStatement(ordenSQLIdViajeBackUp);
        ResultSet rsViajeBackUp = PrepStmIdViajeBackUp.executeQuery();
        int idViajeBackup = 0;
        while (rsViajeBackUp.next()) {
            idViajeBackup = rsViajeBackUp.getInt("id");
        }
        String ordenSQL2 = "SELECT * FROM reservas WHERE id_viaje=?";
        PreparedStatement PrepStm2 = conn.prepareStatement(ordenSQL2);
        PrepStm2.setInt(1, idViaje);
        ResultSet rs2 = PrepStm2.executeQuery();

        while (rs2.next()) {
            String ordenSQLInsertViaje = "INSERT INTO `backup_reservas` (`id_backup_viajes`, `id_tarjeta`, `precio`, `codigo`) VALUES (?,?,?,?);";
            PreparedStatement PrepStInsertViaje = conn.prepareStatement(ordenSQLInsertViaje);
            PrepStInsertViaje.setInt(1, idViajeBackup);
            PrepStInsertViaje.setString(2, rs2.getString("id_tarjeta"));
            PrepStInsertViaje.setDouble(3, rs2.getDouble("precio"));
            PrepStInsertViaje.setString(4, rs2.getString("codigo"));
            PrepStInsertViaje.executeUpdate();

            String ordenSQLidReserva = "SELECT * FROM backup_reservas;";
            PreparedStatement PrepStmIDReserva = conn.prepareStatement(ordenSQLidReserva);
            ResultSet rsReserva = PrepStmIDReserva.executeQuery();
            String codigo = "";
            int idReservaBackUP = 0;
            while (rsReserva.next()) {
                codigo = rsReserva.getString("codigo");
                idReservaBackUP = rsReserva.getInt("id");
            }

            String ordenSQL3 = "SELECT viajeros.nif AS 'nif', viajeros.nombre AS 'nombre' ,viajeros.apellidos AS 'apellidos', ocupacion.asiento AS 'asiento', viajeros.id AS 'id' FROM viajeros, viajes, ocupacion, reservas WHERE viajes.id = reservas.id_viaje AND reservas.id = ocupacion.id_reserva AND viajeros.id = ocupacion.id_viajero AND reservas.codigo=?";
            PreparedStatement PrepStm3 = conn.prepareStatement(ordenSQL3);
            PrepStm3.setString(1, codigo);
            ResultSet rs3 = PrepStm3.executeQuery();
            while (rs3.next()) {
                String ordenSQLInsertViajeros = "INSERT IGNORE INTO `backup_viajeros` (`nif`, `nombre`, `apellidos`) VALUES (?,?,?);";
                PreparedStatement PrepStInsertViajeros = conn.prepareStatement(ordenSQLInsertViajeros);
                PrepStInsertViajeros.setString(1, rs3.getString("nif"));
                PrepStInsertViajeros.setString(2, rs3.getString("nombre"));
                PrepStInsertViajeros.setString(3, rs3.getString("apellidos"));
                PrepStInsertViajeros.executeUpdate();

                String ordenSQLViajeroID = "SELECT * FROM backup_viajeros WHERE nif=?";
                PreparedStatement PrepStIDViajero = conn.prepareStatement(ordenSQLViajeroID);
                PrepStIDViajero.setString(1, rs3.getString("nif"));
                ResultSet rsViajeroID = PrepStIDViajero.executeQuery();
                int idViajeroBackUp = 0;
                while (rsViajeroID.next()) {
                    idViajeroBackUp = rsViajeroID.getInt("id");

                }
                System.out.println("<<<<<<<><<<<<<<<<<<<<<<" + idReservaBackUP);

                String ordenSQLInsertOcupacionBack_up = "INSERT INTO `backup_ocupacion` (`id_backup_reservas`, `id_backup_viajeros`, `asiento`) VALUES (?,?,?);";
                PreparedStatement PrepStInsertOcupacionBack_up = conn.prepareStatement(ordenSQLInsertOcupacionBack_up);
                PrepStInsertOcupacionBack_up.setInt(1, idReservaBackUP);
                PrepStInsertOcupacionBack_up.setInt(2, idViajeroBackUp);
                PrepStInsertOcupacionBack_up.setInt(3, rs3.getInt("asiento"));
                PrepStInsertOcupacionBack_up.executeUpdate();

                String ordensqlBorradoOcupacionViajero = "DELETE FROM `ocupacion` WHERE `id_viajero`=? AND id_reserva=?;";
                PreparedStatement PrepStBorradoOcupacion = conn.prepareStatement(ordensqlBorradoOcupacionViajero);
                PrepStBorradoOcupacion.setInt(1, rs3.getInt("id"));
                PrepStBorradoOcupacion.setInt(2, rs2.getInt("id"));                
                PrepStBorradoOcupacion.executeUpdate();

                String ordenSql_CompruebaViajeros = "SELECT COUNT(*) AS 'numeroViajeros' FROM ocupacion WHERE id_viajero=?";
                PreparedStatement PrepStCompruebaViajeros = conn.prepareStatement(ordenSql_CompruebaViajeros);
                PrepStCompruebaViajeros.setInt(1, rs3.getInt("id"));
                ResultSet rsViajeros = PrepStCompruebaViajeros.executeQuery();
                int contador = 0;
                while (rsViajeros.next()) {
                    contador = rsViajeros.getInt("numeroViajeros");
                }
                if (contador == 0) {
                    String ordenSqlBorradoViajero = "DELETE FROM viajeros WHERE id=?";
                    PreparedStatement PrepStBorradoViajero = conn.prepareStatement(ordenSqlBorradoViajero);
                    PrepStBorradoViajero.setInt(1, rs3.getInt("id"));
                    PrepStBorradoViajero.executeUpdate();
                }

            }
            String ordensqlBorradoOcupacionReserva = "DELETE FROM `ocupacion` WHERE `id_reserva`=?;";
            PreparedStatement PrepStBorradoOcupacion = conn.prepareStatement(ordensqlBorradoOcupacionReserva);
            PrepStBorradoOcupacion.setInt(1, rs2.getInt("id"));
            PrepStBorradoOcupacion.executeUpdate();

        }

        String ordenSqlBorradoReserva = "DELETE FROM `reservas` WHERE `id_viaje`=?;";
        PreparedStatement PrepStBorradoReserva = conn.prepareStatement(ordenSqlBorradoReserva);
        PrepStBorradoReserva.setInt(1, idViaje);
        PrepStBorradoReserva.executeUpdate();

        String ordenSqlBorradoViaje = "DELETE FROM `viajes` WHERE `id`=?;";
        PreparedStatement PrepStBorradoViaje = conn.prepareStatement(ordenSqlBorradoViaje);
        PrepStBorradoViaje.setInt(1, idViaje);
        PrepStBorradoViaje.executeUpdate();

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
