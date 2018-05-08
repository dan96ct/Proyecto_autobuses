/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Dani
 */
public class Cliente {
    private String nif, nombre, apellidos, email, password;
    private ArrayList<Tarjeta> tarjetas;

    public Cliente(String nif, String nombre, String apellidos, String email, String password) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        tarjetas = new ArrayList<>();
    }

    public Cliente() {
    }

    public ArrayList<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nif=" + nif + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email + ", password=" + password + ", tarjetas=" + tarjetas + '}';
    }
    
   
    public void setTarjetas(ArrayList<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void addTarjeta(Tarjeta tarjeta){
        this.tarjetas.add(tarjeta);
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
    
}
