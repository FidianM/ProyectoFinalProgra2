/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// smgiaan/persistencia/EjercicioDAO.java
package smgiaan.persistencia;

import java.util.ArrayList;
import java.util.List;
import smgiaan.modelo.Ejercicio;

public class EjercicioDAO {
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public EjercicioDAO() {
        ejercicios.add(new Ejercicio(1, "Calentamiento", "10 min trote"));
        ejercicios.add(new Ejercicio(2, "Estiramiento", "Rutina básica"));
    }

    public List<Ejercicio> listar() {
        return ejercicios;
    }

    public void agregar(Ejercicio e) {
        ejercicios.add(e);
    }
}
