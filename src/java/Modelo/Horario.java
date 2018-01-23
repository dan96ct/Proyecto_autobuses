/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Dani
 */
public class Horario {
    private String horaSalida, horaLlegada;
    private int plazasLibres;
    private double precio;

    public String getHoraSalida() {
        return horaSalida;
    }

    @Override
    public String toString() {
        return "Horario{" + "horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", plazasLibres=" + plazasLibres + ", precio=" + precio + '}';
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

    public int getPlazasLibres() {
        return plazasLibres;
    }

    public void setPlazasLibres(int plazasLibres) {
        this.plazasLibres = plazasLibres;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Horario(String horaSalida, String horaLlegada, int plazasLibres, double precio) {
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.plazasLibres = plazasLibres;
        this.precio = precio;
    }
    
    
}
