/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// smgiaan/servicio/DepartamentoServicio.java

package com.smgiaan.servicio;
import java.util.List;
import com.smgiaan.modelo.Departamento;
import com.smgiaan.persistencia.DepartamentoDAO;

public class DepartamentoServicio {
    private DepartamentoDAO dao = new DepartamentoDAO();

    public List<Departamento> listarDepartamentos() {
        return dao.listar();
    }

    public void agregarDepartamento(Departamento d) {
        dao.agregar(d);
    }
}
