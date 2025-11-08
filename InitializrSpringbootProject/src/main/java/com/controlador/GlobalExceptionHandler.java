/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.smgiaan.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ❌ Error genérico (cualquier excepción no controlada)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "internal_error");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // ⚠️ Error por validaciones @Valid en el body del request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "validation_error");
        body.put("message", "Error de validación de campos");
        body.put("detalles", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> Map.of("campo", err.getField(), "mensaje", err.getDefaultMessage()))
                .toList());
        return ResponseEntity.badRequest().body(body);
    }

    // ⚠️ Error por parámetros incorrectos (por ejemplo, /api/atletas/x en vez de /api/atletas/1)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "type_mismatch");
        body.put("message", "Parámetro inválido: " + ex.getName());
        return ResponseEntity.badRequest().body(body);
    }

    // ⚠️ Error por @Validated en parámetros del controlador
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "constraint_violation");
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
}
