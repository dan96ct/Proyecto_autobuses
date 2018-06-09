/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Pasajero;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author dani
 */
public class Billete {

    private String origen, destino, horaSalida, horaLlegada,codigo;
    private LocalDate dia;
    private double precio;
    private int personas, idViaje;
    private ArrayList<Pasajero> arrayPasajeros = new ArrayList<>();

    public ArrayList<Pasajero> getArrayPasajeros() {
        return arrayPasajeros;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }
    

    public String getHoraSalida() {
        return horaSalida;
    }

    public void addPasajero(Pasajero pasajero) {
        arrayPasajeros.add(pasajero);
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public Billete(String origen, String destino, LocalDate dia, int personas) {
        this.origen = origen;
        this.destino = destino;
        this.dia = dia;
        this.personas = personas;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getPersonas() {
        return personas;
    }

    @Override
    public String toString() {
        return "Billete{" + "origen=" + origen + ", destino=" + destino + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", dia=" + dia + ", precio=" + precio + ", personas=" + personas + ", idViaje=" + idViaje + ", arrayPasajeros=" + arrayPasajeros + '}';
    }


    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public Billete() {
    }

}
