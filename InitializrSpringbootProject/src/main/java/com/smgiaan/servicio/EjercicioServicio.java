/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// smgiaan/servicio/EjercicioServicio.java

package com.smgiaan.servicio;
import java.util.List;
import com.smgiaan.modelo.Ejercicio;
import com.smgiaan.persistencia.EjercicioDAO;

public class EjercicioServicio {
    private EjercicioDAO dao = new EjercicioDAO();

    public List<Ejercicio> listarEjercicios() {
        return dao.listar();
    }

    public void agregarEjercicio(Ejercicio e) {
        dao.agregar(e);
    }
}
