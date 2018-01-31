/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author dani
 */
public class Billete {

    private String origen, destino, dia, horaSalida,horaLlegada;
    private double precio;
    private int personas;
    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }


    public Billete(String origen, String destino, String dia, int personas) {
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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
        return "Billete{" + "origen=" + origen + ", destino=" + destino + ", dia=" + dia + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", precio=" + precio + ", personas=" + personas + '}';
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public Billete() {
    }

}
