/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smgiaan.modelo;
import com.smgiaan.modelo.Atleta;
import com.smgiaan.modelo.Entrenamiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Atleta {
    private int id; 
    private String carnet; 
    private String nombre;
    private int edad;
    private String disciplina;
    private String departamento;
    private String nacionalidad;
    private LocalDate fechaIngreso;
    private List<Entrenamiento> entrenamientos;

    // Constructor con parámetros
    public Atleta(int id, String carnet, String nombre, int edad, String disciplina,
                  String departamento, String nacionalidad, LocalDate fechaIngreso) {
        this.id = id;
        this.carnet = carnet;
        this.nombre = nombre;
        this.edad = edad;
        this.disciplina = disciplina;
        this.departamento = departamento;
        this.nacionalidad = nacionalidad;
        this.fechaIngreso = fechaIngreso;
        this.entrenamientos = new ArrayList<>();
    }

    // Constructor vacío
    public Atleta() {
        this.entrenamientos = new ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public String getCarnet() { return carnet; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getDisciplina() { return disciplina; }
    public String getDepartamento() { return departamento; }
    public String getNacionalidad() { return nacionalidad; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public List<Entrenamiento> getEntrenamientos() { return new ArrayList<>(entrenamientos); }

    // Getters compatibles con MenuPrincipal
    public String getNombreCompleto() { return nombre; }

    // Setters compatibles con MenuPrincipal y DAO
    public void setId(int id) { this.id = id; }
    public void setNombreCompleto(String nombreCompleto) { this.nombre = nombreCompleto; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    // Entrenamientos
    public void agregarEntrenamiento(Entrenamiento e) { entrenamientos.add(e); }

    // Cálculos
    public double getPromedio() {
        if (entrenamientos.isEmpty()) return 0.0;
        double suma = 0.0;
        for (Entrenamiento e : entrenamientos) suma += e.getValor();
        return suma / entrenamientos.size();
    }

    public double getMejorMarca() {
        if (entrenamientos.isEmpty()) return 0.0;
        boolean mayorMejor = disciplina.equalsIgnoreCase("Pesas") || 
                             disciplina.equalsIgnoreCase("Lanzamientos") || 
                             disciplina.equalsIgnoreCase("Saltos");
        if (mayorMejor) {
            return Collections.max(entrenamientos, Comparator.comparing(Entrenamiento::getValor)).getValor();
        } else {
            return Collections.min(entrenamientos, Comparator.comparing(Entrenamiento::getValor)).getValor();
        }
    }

    public List<Entrenamiento> getEvolucion() {
        List<Entrenamiento> sorted = new ArrayList<>(entrenamientos);
        sorted.sort(Comparator.comparing(Entrenamiento::getFecha));
        return sorted;
    }

    public long contarEntrenamientosNacionales() {
        return entrenamientos.stream().filter(Entrenamiento::isNacional).count();
    }

    public long contarEntrenamientosInternacionales() {
        return entrenamientos.stream().filter(e -> !e.isNacional()).count();
    }

    // CSV
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",")
          .append(escape(carnet)).append(",")
          .append(escape(nombre)).append(",")
          .append(edad).append(",")
          .append(escape(disciplina)).append(",")
          .append(escape(departamento)).append(",")
          .append(escape(nacionalidad)).append(",")
          .append(fechaIngreso.toString());
        for (Entrenamiento e : entrenamientos) sb.append("|").append(e.toCSV());
        return sb.toString();
    }

    private String escape(String s) { return s == null ? "" : s.replace("|"," ").replace(","," "); }
    

    public static Atleta fromCSV(String linea) {
        String[] partes = linea.split("\\|");
        String[] info = partes[0].split(",");
        int id = Integer.parseInt(info[0]);
        String carnet = info[1];
        String nombre = info[2];
        int edad = Integer.parseInt(info[3]);
        String disciplina = info[4];
        String departamento = info[5];
        String nacionalidad = info[6];
        LocalDate fechaIngreso = LocalDate.parse(info[7]);
        Atleta a = new Atleta(id, carnet, nombre, edad, disciplina, departamento, nacionalidad, fechaIngreso);
        for (int i = 1; i < partes.length; i++) {
            if (!partes[i].trim().isEmpty()) a.agregarEntrenamiento(Entrenamiento.fromCSV(partes[i]));
        }
        return a;
    }
}
