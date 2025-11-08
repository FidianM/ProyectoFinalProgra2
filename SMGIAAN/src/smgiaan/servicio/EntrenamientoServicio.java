/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smgiaan.servicio;

import smgiaan.modelo.Entrenamiento;
import smgiaan.persistencia.EntrenamientoDAO;

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
}
