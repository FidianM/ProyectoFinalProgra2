package smgiaan.persistencia;

import smgiaan.modelo.Atleta;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {

    // Guardar lista de atletas en CSV
    public static void guardarListaEnCSV(List<Atleta> lista, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Encabezados
            writer.write("id,nombreCompleto,edad,disciplina,departamento,nacionalidad,fechaIngreso");
            writer.newLine();

            for (Atleta a : lista) {
                writer.write(a.getId() + "," +
                        a.getNombreCompleto() + "," +
                        a.getEdad() + "," +
                        a.getDisciplina() + "," +
                        a.getDepartamento() + "," +
                        a.getNacionalidad() + "," +
                        a.getFechaIngreso().toString()); // YYYY-MM-DD
                writer.newLine();
            }
        }
    }

    // Leer lista de atletas desde CSV
    public static List<Atleta> leerListaDesdeCSV(String fileName) throws IOException {
        List<Atleta> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String linea = reader.readLine(); // Saltar encabezado

            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length == 7) {
                    Atleta a = new Atleta();
                    a.setId(Integer.parseInt(campos[0]));
                    a.setNombreCompleto(campos[1]);
                    a.setEdad(Integer.parseInt(campos[2]));
                    a.setDisciplina(campos[3]);
                    a.setDepartamento(campos[4]);
                    a.setNacionalidad(campos[5]);
                    a.setFechaIngreso(LocalDate.parse(campos[6])); // YYYY-MM-DD

                    lista.add(a);
                }
            }
        }

        return lista;
    }
}
