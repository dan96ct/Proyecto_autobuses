/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author dani
 */
public class Viaje {
    private String origen, destino, horaSalida, horaLLegada;
    private int idViaje;
    private LocalDate fecha;

    public Viaje(String origen, String horaSalida, String horaLLegada, int id, LocalDate fecha) {
        this.origen = origen;
        this.horaSalida = horaSalida;
        this.horaLLegada = horaLLegada;
        this.idViaje = id;
        this.fecha = fecha;
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

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLLegada() {
        return horaLLegada;
    }

    public void setHoraLLegada(String horaLLegada) {
        this.horaLLegada = horaLLegada;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Viaje{" + "salida=" + origen + ", destino=" + destino + ", horaSalida=" + horaSalida + ", horaLLegada=" + horaLLegada + ", id=" + idViaje + ", fecha=" + fecha + '}';
    }
    
    
    
}
