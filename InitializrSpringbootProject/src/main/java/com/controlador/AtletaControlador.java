package com.smgiaan.controlador;

import com.smgiaan.dto.AtletaDTO;
import com.smgiaan.modelo.Atleta;
import com.smgiaan.servicio.AtletaServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atletas")
public class AtletaControlador {

    private final AtletaServicio atletaServicio = new AtletaServicio();

    // 🔹 Listar todos los atletas
    @GetMapping
    public ResponseEntity<List<Atleta>> listarAtletas() {
        try {
            List<Atleta> atletas = atletaServicio.listarAtletas();
            return ResponseEntity.ok(atletas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔹 Obtener un atleta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Atleta> obtenerPorId(@PathVariable int id) {
        try {
            Atleta atleta = atletaServicio.obtenerAtletaPorId(id);
            if (atleta != null) {
                return ResponseEntity.ok(atleta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔹 Registrar un nuevo atleta (usando DTO)
    @PostMapping
    public ResponseEntity<?> registrarAtleta(@Valid @RequestBody AtletaDTO atletaDTO) {
        try {
            Atleta atleta = new Atleta();
            atleta.setNombreCompleto(atletaDTO.getNombreCompleto());
            atleta.setEdad(atletaDTO.getEdad());
            atleta.setDisciplina(atletaDTO.getDisciplina());
            atleta.setDepartamento(atletaDTO.getDepartamento());
            atleta.setNacionalidad(atletaDTO.getNacionalidad());
            atleta.setFechaIngreso(atletaDTO.getFechaIngreso());

            atletaServicio.registrarAtleta(atleta);
            return ResponseEntity.status(201).body(atleta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar atleta: " + e.getMessage());
        }
    }

    // 🔹 Actualizar un atleta existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAtleta(@PathVariable int id, @RequestBody Atleta atleta) {
        try {
            atleta.setId(id);
            atletaServicio.actualizarAtleta(atleta);
            return ResponseEntity.ok("Atleta actualizado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 Eliminar atleta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAtleta(@PathVariable int id) {
        try {
            atletaServicio.eliminarAtleta(id);
            return ResponseEntity.ok("Atleta eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/exportar")
public ResponseEntity<?> exportar(@RequestParam String tipo, @RequestParam String ruta) {
    try {
        if (tipo.equalsIgnoreCase("json")) {
            atletaServicio.exportarJSON(ruta);
        } else if (tipo.equalsIgnoreCase("csv")) {
            atletaServicio.exportarCSV(ruta);
        } else {
            return ResponseEntity.badRequest().body("Tipo inválido. Use 'json' o 'csv'.");
        }
        return ResponseEntity.ok("Datos exportados correctamente en: " + ruta);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}

@PostMapping("/importar")
public ResponseEntity<?> importar(@RequestParam String tipo, @RequestParam String ruta) {
    try {
        if (tipo.equalsIgnoreCase("json")) {
            atletaServicio.importarJSON(ruta);
        } else if (tipo.equalsIgnoreCase("csv")) {
            atletaServicio.importarCSV(ruta);
        } else {
            return ResponseEntity.badRequest().body("Tipo inválido. Use 'json' o 'csv'.");
        }
        return ResponseEntity.ok("Datos importados correctamente desde: " + ruta);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}

}
