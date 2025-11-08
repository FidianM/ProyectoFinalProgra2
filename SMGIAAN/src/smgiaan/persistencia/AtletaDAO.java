/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smgiaan.persistencia;

/**
 *
 * @author Morales
 */
import smgiaan.modelo.Atleta;
import java.sql.*;
import java.util.*;

public class AtletaDAO {
    public void insertar(Atleta a) throws SQLException {
        String sql = "INSERT INTO atletas(nombreCompleto, edad, disciplina, departamento, nacionalidad, fechaIngreso) VALUES(?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombreCompleto());
            ps.setInt(2, a.getEdad());
            ps.setString(3, a.getDisciplina());
            ps.setString(4, a.getDepartamento());
            ps.setString(5, a.getNacionalidad());
            ps.setDate(6, java.sql.Date.valueOf(a.getFechaIngreso()));
            ps.executeUpdate();
        }
    }

public List<Atleta> listar() throws SQLException {
    List<Atleta> lista = new ArrayList<>();
    
    String sql = "SELECT * FROM atletas";
    try (Connection con = ConexionBD.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            Atleta a = new Atleta();
            a.setId(rs.getInt("id"));
            a.setNombreCompleto(rs.getString("nombreCompleto"));
            a.setEdad(rs.getInt("edad"));          
            a.setDisciplina(rs.getString("disciplina"));
            a.setDepartamento(rs.getString("departamento"));
            a.setNacionalidad(rs.getString("nacionalidad"));
            a.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());

            lista.add(a);  
        }
    }
    return lista;
}

    public Atleta obtenerAtletaPorId(int id) throws Exception {
    List<Atleta> lista = this.listar(); // obtenemos todos los atletas
    for (Atleta a : lista) {
        if (a.getId() == id) return a;
    }
    return null; // si no lo encuentra
}


}
