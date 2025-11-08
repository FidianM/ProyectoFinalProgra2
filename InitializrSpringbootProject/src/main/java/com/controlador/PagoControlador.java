/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.controlador;

import com.smgiaan.modelo.Pago;
import com.smgiaan.servicio.PagoServicio;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoControlador {

    private final PagoServicio pagoServicio = new PagoServicio();

    // 1️⃣ Registrar pago manual (enviar JSON)
    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody Pago pago) {
        try {
            pagoServicio.registrarPago(pago);
            return ResponseEntity.status(201).body(pago);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 2️⃣ Calcular y registrar pago automáticamente según entrenamientos
    @PostMapping("/calcular")
    public ResponseEntity<?> calcularYRegistrarPago(
            @RequestParam int atletaId,
            @RequestParam int mes,
            @RequestParam String periodo
    ) {
        try {
            Pago pago = pagoServicio.calcularYRegistrarPago(atletaId, mes, periodo);
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // 3️⃣ Listar todos los pagos
    @GetMapping
    public ResponseEntity<List<Pago>> listarTodos() {
        try {
            return ResponseEntity.ok(pagoServicio.listarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 4️⃣ Listar pagos por atleta
    @GetMapping("/atleta/{atletaId}")
    public ResponseEntity<List<Pago>> listarPorAtleta(@PathVariable int atletaId) {
        try {
            return ResponseEntity.ok(pagoServicio.listarPagosPorAtleta(atletaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 5️⃣ Exportar pagos a CSV
    @GetMapping("/exportar")
    public ResponseEntity<String> exportarPagos(@RequestParam String rutaArchivo) {
        try {
            pagoServicio.exportarPagosCSV(rutaArchivo);
            return ResponseEntity.ok("Pagos exportados correctamente a: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
