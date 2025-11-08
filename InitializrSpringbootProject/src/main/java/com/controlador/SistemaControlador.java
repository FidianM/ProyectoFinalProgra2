package com.smgiaan.controlador;

import com.smgiaan.modelo.Pago;
import com.smgiaan.modelo.Entrenamiento;
import com.smgiaan.modelo.Atleta;
import com.smgiaan.servicio.AtletaServicio;
import com.smgiaan.servicio.EntrenamientoServicio;
import com.smgiaan.servicio.PagoServicio;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/sistema")
public class SistemaControlador {

    private final AtletaServicio atletaServicio = new AtletaServicio();
    private final EntrenamientoServicio entrenamientoServicio = new EntrenamientoServicio();
    private final PagoServicio pagoServicio = new PagoServicio();

    // 1️⃣ Resumen general del sistema
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> obtenerResumen() {
        Map<String, Object> resumen = new HashMap<>();
        try {
            List<Atleta> atletas = atletaServicio.listarAtletas();
            List<Entrenamiento> entrenamientos = entrenamientoServicio.listarEntrenamientosPorAtleta(1); // ejemplo para probar
            List<Pago> pagos = pagoServicio.listarTodos();

            resumen.put("fecha", LocalDate.now());
            resumen.put("totalAtletas", atletas.size());
            resumen.put("totalPagos", pagos.size());
            resumen.put("totalEntrenamientos", entrenamientos.size());

            double totalMonto = pagos.stream().mapToDouble(Pago::getMonto).sum();
            resumen.put("montoTotalPagado", totalMonto);

            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            e.printStackTrace();
            resumen.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(resumen);
        }
    }

    // 2️⃣ Exportar resumen general a CSV
    @GetMapping("/exportar")
    public ResponseEntity<String> exportarSistemaCSV(@RequestParam String rutaArchivo) {
        try {
            pagoServicio.exportarPagosCSV(rutaArchivo);
            return ResponseEntity.ok("✅ Resumen del sistema exportado correctamente a: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al exportar: " + e.getMessage());
        }
    }
}
