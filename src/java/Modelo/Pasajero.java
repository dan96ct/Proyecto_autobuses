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
public class Pasajero {
    private String nombre,apellido,correo,identificador;
    private int asiento;

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }
    

    public Pasajero(String nombre, String apellido, String correo, String identificador) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.identificador = identificador;
    }

    public Pasajero() {
    }

    @Override
    public String toString() {
        return "Pasajero{" + "nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + ", identificador=" + identificador + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
}
