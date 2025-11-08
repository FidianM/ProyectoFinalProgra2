/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.servicio;
import com.smgiaan.modelo.Entrenamiento;
import java.util.*;
import java.time.LocalDate;

public class EstadisticasServicio {

    // Calcula el promedio de rendimiento de una lista de entrenamientos
    public double calcularPromedioRendimiento(List<Entrenamiento> entrenamientos) {
        if (entrenamientos.isEmpty()) return 0.0;
        double suma = 0;
        for (Entrenamiento e : entrenamientos) {
            suma += e.getValorRendimiento();
        }
        return suma / entrenamientos.size();
    }

    // Obtiene la mejor marca (el valor máximo de rendimiento)
    public double obtenerMejorMarca(List<Entrenamiento> entrenamientos) {
        double mejor = Double.MIN_VALUE;
        for (Entrenamiento e : entrenamientos) {
            if (e.getValorRendimiento() > mejor) {
                mejor = e.getValorRendimiento();
            }
        }
        return mejor == Double.MIN_VALUE ? 0.0 : mejor;
    }

    // Devuelve una lista ordenada por fecha (para ver evolución)
    public List<Entrenamiento> obtenerEvolucionPorFecha(List<Entrenamiento> entrenamientos) {
        List<Entrenamiento> copia = new ArrayList<>(entrenamientos);
        copia.sort(Comparator.comparing(Entrenamiento::getFecha));
        return copia;
    }

    // Compara promedios nacionales e internacionales
    public void compararRendimientoNacionalVsInternacional(List<Entrenamiento> entrenamientos) {
        double sumaNacional = 0, sumaInternacional = 0;
        int contNac = 0, contInt = 0;

        for (Entrenamiento e : entrenamientos) {
            if (e.getUbicacion().equalsIgnoreCase("nacional")) {
                sumaNacional += e.getValorRendimiento();
                contNac++;
            } else {
                sumaInternacional += e.getValorRendimiento();
                contInt++;
            }
        }

        double promNac = contNac > 0 ? sumaNacional / contNac : 0;
        double promInt = contInt > 0 ? sumaInternacional / contInt : 0;

        System.out.println("=== COMPARACIÓN DE RENDIMIENTO ===");
        System.out.println("Promedio Nacional: " + promNac);
        System.out.println("Promedio Internacional: " + promInt);

        if (promInt > promNac) {
            System.out.println("👉 Mejor desempeño en entrenamientos internacionales.");
        } else if (promNac > promInt) {
            System.out.println("👉 Mejor desempeño en entrenamientos nacionales.");
        } else {
            System.out.println("👉 Desempeño equilibrado en ambas condiciones.");
        }
    }
}
