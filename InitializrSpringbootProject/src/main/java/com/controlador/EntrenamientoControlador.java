package com.smgiaan.controlador;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smgiaan.modelo.Entrenamiento;
import com.smgiaan.persistencia.EntrenamientoDAO;
import com.smgiaan.servicio.EntrenamientoServicio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/entrenamientos")
@CrossOrigin(origins = "*")
public class EntrenamientoControlador {

    private final EntrenamientoServicio servicio = new EntrenamientoServicio();
    private final EntrenamientoDAO dao = new EntrenamientoDAO();

    // ✅ Crear nuevo entrenamiento
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Entrenamiento e) {
        try {
            servicio.registrarEntrenamiento(e);
            return ResponseEntity.ok("Entrenamiento registrado correctamente");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al registrar: " + ex.getMessage());
        }
    }

    // ✅ Listar por atleta
    @GetMapping("/atleta/{id}")
    public ResponseEntity<List<Entrenamiento>> listarPorAtleta(@PathVariable int id) {
        try {
            List<Entrenamiento> lista = servicio.listarEntrenamientosPorAtleta(id);
            return ResponseEntity.ok(lista);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Actualizar entrenamiento
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable int id, @RequestBody Entrenamiento e) {
        try {
            e.setId(id);
            dao.actualizar(e);
            return ResponseEntity.ok("Entrenamiento actualizado correctamente");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al actualizar: " + ex.getMessage());
        }
    }

    // ✅ Eliminar entrenamiento
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        try {
            dao.eliminar(id);
            return ResponseEntity.ok("Entrenamiento eliminado correctamente");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al eliminar: " + ex.getMessage());
        }
    }

    // ✅ Estadísticas por atleta
    @GetMapping("/estadisticas/{atletaId}")
    public ResponseEntity<String> estadisticas(@PathVariable int atletaId) {
        try {
            double promedio = servicio.promedioRendimiento(atletaId);
            double mejor = servicio.mejorMarca(atletaId);
            long nNac = servicio.contarEntrenamientosNacionales(atletaId);
            long nInt = servicio.contarEntrenamientosInternacionales(atletaId);

            String resumen = String.format(
                "Atleta %d → Promedio: %.2f | Mejor marca: %.2f | Nacionales: %d | Internacionales: %d",
                atletaId, promedio, mejor, nNac, nInt
            );

            return ResponseEntity.ok(resumen);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al calcular estadísticas: " + ex.getMessage());
        }
    }

    // ✅ Exportar entrenamientos a JSON
    @GetMapping("/exportar/json")
    public ResponseEntity<byte[]> exportarJSON() {
        try {
            List<Entrenamiento> lista = dao.listar();
            ObjectMapper mapper = new ObjectMapper();
            byte[] data = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(lista);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entrenamientos.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(data);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Importar entrenamientos desde JSON
    @PostMapping("/importar/json")
    public ResponseEntity<String> importarJSON(@RequestParam("file") MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Entrenamiento> lista = mapper.readValue(file.getInputStream(), new TypeReference<>() {});
            for (Entrenamiento e : lista) {
                servicio.registrarEntrenamiento(e);
            }
            return ResponseEntity.ok("Importación JSON completada. Entrenamientos cargados: " + lista.size());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error al importar JSON: " + ex.getMessage());
        }
    }

    // ✅ Exportar entrenamientos a CSV
    @GetMapping("/exportar/csv")
    public ResponseEntity<byte[]> exportarCSV() {
        try {
            List<Entrenamiento> lista = dao.listar();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));

            try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("id", "atleta_id", "fecha", "tipoEntrenamiento", "valorRendimiento", "ubicacion", "pais"))) {
                for (Entrenamiento e : lista) {
                    csvPrinter.printRecord(
                            e.getId(),
                            e.getAtletaId(),
                            e.getFecha(),
                            e.getTipoEntrenamiento(),
                            e.getValorRendimiento(),
                            e.getUbicacion(),
                            e.getPais()
                    );
                }
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entrenamientos.csv")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(out.toByteArray());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
