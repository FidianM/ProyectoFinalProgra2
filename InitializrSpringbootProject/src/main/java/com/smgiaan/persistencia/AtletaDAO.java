package com.smgiaan.persistencia;

import com.smgiaan.modelo.Atleta;
import java.sql.*;
import java.util.*;

public class AtletaDAO {

    // INSERTAR nuevo atleta
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

    // LISTAR todos los atletas
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

    // OBTENER atleta por ID
    public Atleta obtenerAtletaPorId(int id) throws SQLException {
        Atleta atleta = null;
        String sql = "SELECT * FROM atletas WHERE id = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    atleta = new Atleta();
                    atleta.setId(rs.getInt("id"));
                    atleta.setNombreCompleto(rs.getString("nombreCompleto"));
                    atleta.setEdad(rs.getInt("edad"));
                    atleta.setDisciplina(rs.getString("disciplina"));
                    atleta.setDepartamento(rs.getString("departamento"));
                    atleta.setNacionalidad(rs.getString("nacionalidad"));
                    atleta.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());
                }
            }
        }
        return atleta;
    }

    // ✅ ACTUALIZAR atleta existente
    public void actualizar(Atleta a) throws SQLException {
        String sql = "UPDATE atletas SET nombreCompleto=?, edad=?, disciplina=?, departamento=?, nacionalidad=?, fechaIngreso=? WHERE id=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombreCompleto());
            ps.setInt(2, a.getEdad());
            ps.setString(3, a.getDisciplina());
            ps.setString(4, a.getDepartamento());
            ps.setString(5, a.getNacionalidad());
            ps.setDate(6, java.sql.Date.valueOf(a.getFechaIngreso()));
            ps.setInt(7, a.getId());
            ps.executeUpdate();
        }
    }

    // ✅ ELIMINAR atleta por ID
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM atletas WHERE id=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
