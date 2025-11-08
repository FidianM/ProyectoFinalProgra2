/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.servicio;
import com.smgiaan.modelo.Entrenamiento;
import com.smgiaan.persistencia.EntrenamientoDAO;

import java.util.List;

public class EntrenamientoServicio {
    private EntrenamientoDAO dao = new EntrenamientoDAO();

    // Registrar entrenamiento en BD
    public void registrarEntrenamiento(Entrenamiento e) throws Exception {
        dao.insertar(e);
    }

    // Listar entrenamientos de un atleta
    public List<Entrenamiento> listarEntrenamientosPorAtleta(int atletaId) throws Exception {
        return dao.listarPorAtleta(atletaId);
    }

    // Promedio de rendimiento
    public double promedioRendimiento(int atletaId) throws Exception {
        List<Entrenamiento> lista = listarEntrenamientosPorAtleta(atletaId);
        if (lista.isEmpty()) return 0.0;
        double suma = 0.0;
        for (Entrenamiento e : lista) {
            suma += e.getValorRendimiento();
        }
        return suma / lista.size();
    }

    // Mejor marca
    public double mejorMarca(int atletaId) throws Exception {
        List<Entrenamiento> lista = listarEntrenamientosPorAtleta(atletaId);
        if (lista.isEmpty()) return 0.0;
        return lista.stream()
                .mapToDouble(Entrenamiento::getValorRendimiento)
                .max()
                .orElse(0.0);
    }

    // Contar entrenamientos nacionales
    public long contarEntrenamientosNacionales(int atletaId) throws Exception {
        return listarEntrenamientosPorAtleta(atletaId).stream()
                .filter(Entrenamiento::isNacional)
                .count();
    }

    // Contar entrenamientos internacionales
    public long contarEntrenamientosInternacionales(int atletaId) throws Exception {
        return listarEntrenamientosPorAtleta(atletaId).stream()
                .filter(e -> !e.isNacional())
                .count();
    }
        // ===============================
    // 🔹 NUEVOS MÉTODOS PARA EXPORTAR Y LISTAR
    // ===============================

    // Listar todos los entrenamientos
    public List<Entrenamiento> listarTodos() throws Exception {
        return dao.listarTodos();
    }

    // Exportar a CSV
    public void exportarEntrenamientosCSV(String rutaArchivo) throws Exception {
        List<Entrenamiento> entrenamientos = listarTodos();
        try (java.io.FileWriter writer = new java.io.FileWriter(rutaArchivo)) {
            writer.write("ID,AtletaID,Fecha,TipoEntrenamiento,ValorRendimiento,Ubicacion,Pais\n");
            for (Entrenamiento e : entrenamientos) {
                writer.write(e.getId() + "," + e.getAtletaId() + "," + e.getFecha() + "," +
                             e.getTipoEntrenamiento() + "," + e.getValorRendimiento() + "," +
                             e.getUbicacion() + "," + e.getPais() + "\n");
            }
        }
    }

    // Exportar a JSON
    public void exportarEntrenamientosJSON(String rutaArchivo) throws Exception {
        List<Entrenamiento> entrenamientos = listarTodos();
        try (java.io.FileWriter writer = new java.io.FileWriter(rutaArchivo)) {
            writer.write("[\n");
            for (int i = 0; i < entrenamientos.size(); i++) {
                Entrenamiento e = entrenamientos.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": " + e.getId() + ",\n");
                writer.write("    \"atletaId\": " + e.getAtletaId() + ",\n");
                writer.write("    \"fecha\": \"" + e.getFecha() + "\",\n");
                writer.write("    \"tipoEntrenamiento\": \"" + e.getTipoEntrenamiento() + "\",\n");
                writer.write("    \"valorRendimiento\": " + e.getValorRendimiento() + ",\n");
                writer.write("    \"ubicacion\": \"" + e.getUbicacion() + "\",\n");
                writer.write("    \"pais\": \"" + e.getPais() + "\"\n");
                writer.write("  }" + (i < entrenamientos.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]");
        }
    }

}
