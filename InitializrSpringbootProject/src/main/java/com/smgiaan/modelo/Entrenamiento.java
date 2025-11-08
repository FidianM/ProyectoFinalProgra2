/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrenamiento {
    private int atletaId;
    private LocalDate fecha;
    private String tipoEntrenamiento;
    private double valorRendimiento;
    private String tipo; 
    private double valor; 
    private boolean nacional; 
    private String pais;
    
    private int id;

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

    // Constructor con parámetros
    public Entrenamiento(LocalDate fecha, String tipo, double valor, boolean nacional, String pais) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;
        this.nacional = nacional;
        this.pais = nacional ? "" : (pais == null ? "" : pais);
    }

    // Constructor vacío
    public Entrenamiento() {}

    // Getters
    public int getAtletaId() { return atletaId; }
    public LocalDate getFecha() { return fecha; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public boolean isNacional() { return nacional; }
    public String getPais() { return pais; }

    // Getters/setters compatibles con MenuPrincipal
    public void setAtletaId(int atletaId) { this.atletaId = atletaId; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTipoEntrenamiento() { return tipoEntrenamiento != null ? tipoEntrenamiento : tipo; }
    public void setTipoEntrenamiento(String tipoEntrenamiento) { 
        this.tipoEntrenamiento = tipoEntrenamiento; 
        this.tipo = tipoEntrenamiento;
    }

    public double getValorRendimiento() { return valorRendimiento != 0 ? valorRendimiento : valor; }
    public void setValorRendimiento(double valorRendimiento) { 
        this.valorRendimiento = valorRendimiento; 
        this.valor = valorRendimiento;
    }

    public String getUbicacion() { return nacional ? "Nacional" : "Internacional - " + pais; }
    public void setUbicacion(String ubicacion) {
        if (ubicacion != null && ubicacion.startsWith("Nacional")) {
            this.nacional = true;
            this.pais = "";
        } else {
            this.nacional = false;
            this.pais = ubicacion != null ? ubicacion : "";
        }
    }
     public void setPais(String pais) {
        this.pais = pais;
    }

    // CSV
    public String toCSV() {
        return fecha.toString() + "," + tipo + "," + valor + "," + nacional + "," + pais;
    }

    public static Entrenamiento fromCSV(String linea) {
        String[] p = linea.split(",");
        LocalDate fecha = LocalDate.parse(p[0]);
        String tipo = p[1];
        double valor = Double.parseDouble(p[2]);
        boolean nacional = Boolean.parseBoolean(p[3]);
        String pais = p.length > 4 ? p[4] : "";
        return new Entrenamiento(fecha, tipo, valor, nacional, pais);
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(df) + " - " + tipo + " - " + valor + (nacional ? " (Nacional)" : " (Int: " + pais + ")");
    }
}
