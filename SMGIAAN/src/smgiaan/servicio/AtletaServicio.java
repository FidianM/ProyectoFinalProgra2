/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smgiaan.servicio;

import smgiaan.modelo.Atleta;
import smgiaan.persistencia.AtletaDAO;
import smgiaan.persistencia.JSONManager;
import smgiaan.persistencia.CSVManager; 
import java.util.List;


public class AtletaServicio {
    private AtletaDAO dao = new AtletaDAO();

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
 public void guardarEnJSON(String fileName) throws Exception {
    List<Atleta> lista = dao.listar();
    JSONManager.guardarListaEnJSON(lista, fileName);
}

public void guardarEnCSV(String fileName) throws Exception {
    List<Atleta> lista = dao.listar();
    CSVManager.guardarListaEnCSV(lista, fileName);
}


}


