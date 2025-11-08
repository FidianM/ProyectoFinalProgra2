package com.smgiaan.servicio;

import com.smgiaan.modelo.Atleta;
import com.smgiaan.persistencia.AtletaDAO;
import com.smgiaan.persistencia.JSONManager;
import com.smgiaan.persistencia.CSVManager;
import java.sql.SQLException;
import java.util.List;

public class AtletaServicio {

    private final AtletaDAO dao = new AtletaDAO(); // ✅ solo una instancia

    // Registra un atleta en la base de datos
    public void registrarAtleta(Atleta a) throws Exception {
        dao.insertar(a);
    }

    // Devuelve la lista completa de atletas
    public List<Atleta> listarAtletas() throws Exception {
        return dao.listar();
    }

    // Busca un atleta por su ID
    public Atleta obtenerAtletaPorId(int id) throws Exception {
        List<Atleta> lista = dao.listar(); // obtenemos todos los atletas
        for (Atleta a : lista) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null; // si no lo encuentra
    }

    // Guarda lista en JSON
    public void guardarEnJSON(String fileName) throws Exception {
        List<Atleta> lista = dao.listar();
        JSONManager.guardarListaEnJSON(lista, fileName);
    }

    // Guarda lista en CSV
    public void guardarEnCSV(String fileName) throws Exception {
        List<Atleta> lista = dao.listar();
        CSVManager.guardarListaEnCSV(lista, fileName);
    }

    // Actualiza un atleta existente
    public void actualizarAtleta(Atleta atleta) {
        try {
            dao.actualizar(atleta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Exportar a JSON usando JSONManager (Gson)
public void exportarJSON(String rutaArchivo) throws Exception {
    List<Atleta> lista = dao.listar();
    JSONManager.guardarListaEnJSON(lista, rutaArchivo);
}

// Importar desde JSON
public void importarJSON(String rutaArchivo) throws Exception {
    List<Atleta> atletas = JSONManager.leerListaDesdeJSON(rutaArchivo);
    for (Atleta a : atletas) {
        dao.insertar(a);
    }
}
// Exportar a CSV
public void exportarCSV(String rutaArchivo) throws Exception {
    List<Atleta> lista = dao.listar();
    CSVManager.guardarListaEnCSV(lista, rutaArchivo);
}

// Importar desde CSV
public void importarCSV(String rutaArchivo) throws Exception {
    List<Atleta> atletas = CSVManager.leerListaDesdeCSV(rutaArchivo);
    for (Atleta a : atletas) {
        dao.insertar(a);
    }
}


    // Elimina un atleta por ID
    public void eliminarAtleta(int id) {
        try {
            dao.eliminar(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Alias para compatibilidad con el controlador


}


