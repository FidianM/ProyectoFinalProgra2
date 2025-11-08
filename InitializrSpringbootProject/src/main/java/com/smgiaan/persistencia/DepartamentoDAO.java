/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// smgiaan/persistencia/DepartamentoDAO.java

package com.smgiaan.persistencia;
import java.util.ArrayList;
import java.util.List;
import com.smgiaan.modelo.Departamento;

public class DepartamentoDAO {
    private List<Departamento> departamentos = new ArrayList<>();

    public DepartamentoDAO() {
        // Simulación inicial
        departamentos.add(new Departamento(1, "Atletismo"));
        departamentos.add(new Departamento(2, "Natación"));
    }

    public List<Departamento> listar() {
        return departamentos;
    }

    public void agregar(Departamento d) {
        departamentos.add(d);
    }
}

