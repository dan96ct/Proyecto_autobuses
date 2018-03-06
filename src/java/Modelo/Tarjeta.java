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
public class Tarjeta {
    private String numero,tipo, fechaCaducidad;
    private boolean seleccionada = false;

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
    private int id;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tarjeta(String numero, String tipo, String fechaCaducidad, int id) {
        this.numero = numero;
        this.tipo = tipo;
        this.fechaCaducidad = fechaCaducidad;
        this.id = id;
    }

    public Tarjeta(String numero, String tipo, String fechaCaducidad) {
        this.numero = numero;
        this.tipo = tipo;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Tarjeta() {
    }

    

    

    @Override
    public String toString() {
        return "Tarjeta{" + "numero=" + numero + ", tipo=" + tipo + ", fechaCaducidad=" + fechaCaducidad + '}';
    }
    
}
