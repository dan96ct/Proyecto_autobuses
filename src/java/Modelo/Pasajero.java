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
    private String nombre,apellido,identificador,id;//El id no tiene ninguna relacion con la base de datos, sirve para evitar que se introduzca dos veces el mismo pasajero
    private int asiento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }
    

    public Pasajero(String nombre, String apellido, String identificador) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificador = identificador;
    }

    public Pasajero() {
    }

    @Override
    public String toString() {
        return "Pasajero{" + "nombre=" + nombre + ", apellido=" + apellido + ", identificador=" + identificador + ", id=" + id + ", asiento=" + asiento + '}';
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

  
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
}
