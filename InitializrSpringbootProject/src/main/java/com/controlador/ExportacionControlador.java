/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.controlador;

import com.smgiaan.servicio.AtletaServicio;
import com.smgiaan.servicio.EntrenamientoServicio;
import com.smgiaan.servicio.PagoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exportacion")
public class ExportacionControlador{

    private final AtletaServicio atletaServicio = new AtletaServicio();
    private final EntrenamientoServicio entrenamientoServicio = new EntrenamientoServicio();
    private final PagoServicio pagoServicio = new PagoServicio();

    // 🔹 Exportar TODO en JSON o CSV
    @GetMapping("/exportar")
    public ResponseEntity<?> exportarTodo(
            @RequestParam String tipo,
            @RequestParam String ruta
    ) {
        try {
            if (tipo.equalsIgnoreCase("json")) {
                atletaServicio.guardarEnJSON(ruta + "/atletas.json");
                entrenamientoServicio.exportarEntrenamientosJSON(ruta + "/entrenamientos.json");
                pagoServicio.exportarPagosCSV(ruta + "/pagos.json");
            } else if (tipo.equalsIgnoreCase("csv")) {
                atletaServicio.guardarEnCSV(ruta + "/atletas.csv");
                entrenamientoServicio.exportarEntrenamientosCSV(ruta + "/entrenamientos.csv");
                pagoServicio.exportarPagosCSV(ruta + "/pagos.csv");
            } else {
                return ResponseEntity.badRequest().body("Tipo inválido. Usa 'json' o 'csv'.");
            }

            return ResponseEntity.ok("✅ Datos exportados correctamente en la ruta: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al exportar: " + e.getMessage());
        }
    }
}

