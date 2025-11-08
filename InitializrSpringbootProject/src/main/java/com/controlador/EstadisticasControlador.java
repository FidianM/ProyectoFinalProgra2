/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.controlador;

import com.smgiaan.modelo.Entrenamiento;
import com.smgiaan.servicio.EstadisticasServicio;
import com.smgiaan.persistencia.EntrenamientoDAO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasControlador {

    private final EstadisticasServicio estadisticasServicio = new EstadisticasServicio();
    private final EntrenamientoDAO entrenamientoDAO = new EntrenamientoDAO();

    // 🔹 Obtener promedio de rendimiento de un atleta
    @GetMapping("/promedio/{atletaId}")
    public ResponseEntity<?> obtenerPromedio(@PathVariable int atletaId) {
        try {
            List<Entrenamiento> lista = entrenamientoDAO.listarPorAtleta(atletaId);
            double promedio = estadisticasServicio.calcularPromedioRendimiento(lista);
            return ResponseEntity.ok("Promedio de rendimiento: " + promedio);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // 🔹 Obtener mejor marca de un atleta
    @GetMapping("/mejorMarca/{atletaId}")
    public ResponseEntity<?> obtenerMejorMarca(@PathVariable int atletaId) {
        try {
            List<Entrenamiento> lista = entrenamientoDAO.listarPorAtleta(atletaId);
            double mejor = estadisticasServicio.obtenerMejorMarca(lista);
            return ResponseEntity.ok("Mejor marca registrada: " + mejor);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // 🔹 Obtener evolución del rendimiento (ordenado por fecha)
    @GetMapping("/evolucion/{atletaId}")
    public ResponseEntity<?> obtenerEvolucion(@PathVariable int atletaId) {
        try {
            List<Entrenamiento> lista = entrenamientoDAO.listarPorAtleta(atletaId);
            List<Entrenamiento> evolucion = estadisticasServicio.obtenerEvolucionPorFecha(lista);
            return ResponseEntity.ok(evolucion);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // 🔹 Comparar rendimiento nacional vs internacional
    @GetMapping("/comparar/{atletaId}")
    public ResponseEntity<?> compararRendimiento(@PathVariable int atletaId) {
        try {
            List<Entrenamiento> lista = entrenamientoDAO.listarPorAtleta(atletaId);
            estadisticasServicio.compararRendimientoNacionalVsInternacional(lista);
            return ResponseEntity.ok("Comparación mostrada en consola.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}