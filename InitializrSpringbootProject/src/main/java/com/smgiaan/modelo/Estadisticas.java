/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.modelo;
import java.util.List;

public class Estadisticas {
    public double calcularPromedio(List<Double> valores) {
        return valores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public double mejorMarca(List<Double> valores) {
        return valores.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }
}