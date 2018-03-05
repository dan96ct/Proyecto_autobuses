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
public class Cliente {
    private String nif, nombre, apellidos, email, tarjeta, fechaCaducidad,tipoTarjeta,password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cliente(String nif, String nombre, String apellidos, String email, String tarjeta, String fechaCaducidad, String tipoTarjeta, String password) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.tarjeta = tarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.tipoTarjeta = tipoTarjeta;
        this.password = password;
    }

  

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

   
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nif=" + nif + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email + ", tarjeta=" + tarjeta + ", fechaCaducidad=" + fechaCaducidad + '}';
    }
    
    
}
