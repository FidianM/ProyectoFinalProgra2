package com.smgiaan.persistencia;

import com.smgiaan.modelo.Entrenamiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrenamientoDAO {

    public void insertar(Entrenamiento e) throws SQLException {
        String sql = "INSERT INTO entrenamientos (atleta_id, fecha, tipoEntrenamiento, valorRendimiento, ubicacion, pais) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, e.getAtletaId());
            ps.setDate(2, Date.valueOf(e.getFecha()));
            ps.setString(3, e.getTipoEntrenamiento());
            ps.setDouble(4, e.getValorRendimiento());
            ps.setString(5, e.getUbicacion());
            ps.setString(6, e.getPais());
            ps.executeUpdate();
        }
    }

    public List<Entrenamiento> listar() throws SQLException {
        List<Entrenamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenamientos";
        
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Entrenamiento e = new Entrenamiento();
                e.setId(rs.getInt("id"));
                e.setAtletaId(rs.getInt("atleta_id"));
                e.setFecha(rs.getDate("fecha").toLocalDate());
                e.setTipoEntrenamiento(rs.getString("tipoEntrenamiento"));
                e.setValorRendimiento(rs.getDouble("valorRendimiento"));
                e.setUbicacion(rs.getString("ubicacion"));
                e.setPais(rs.getString("pais"));
                lista.add(e);
            }
        }
        return lista;
    }

    public void actualizar(Entrenamiento e) throws SQLException {
        String sql = "UPDATE entrenamientos SET atleta_id=?, fecha=?, tipoEntrenamiento=?, valorRendimiento=?, ubicacion=?, pais=? WHERE id=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, e.getAtletaId());
            ps.setDate(2, Date.valueOf(e.getFecha()));
            ps.setString(3, e.getTipoEntrenamiento());
            ps.setDouble(4, e.getValorRendimiento());
            ps.setString(5, e.getUbicacion());
            ps.setString(6, e.getPais());
            ps.setInt(7, e.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM entrenamientos WHERE id=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Entrenamiento> listarPorAtleta(int atletaId) throws SQLException {
        List<Entrenamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenamientos WHERE atleta_id=?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, atletaId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Entrenamiento e = new Entrenamiento();
                e.setId(rs.getInt("id"));
                e.setAtletaId(rs.getInt("atleta_id"));
                e.setFecha(rs.getDate("fecha").toLocalDate());
                e.setTipoEntrenamiento(rs.getString("tipoEntrenamiento"));
                e.setValorRendimiento(rs.getDouble("valorRendimiento"));
                e.setUbicacion(rs.getString("ubicacion"));
                e.setPais(rs.getString("pais"));
                lista.add(e);
            }
        }
        return lista;
        
    }
     public List<Entrenamiento> listarTodos() throws SQLException {
        return listar();
    }
}
