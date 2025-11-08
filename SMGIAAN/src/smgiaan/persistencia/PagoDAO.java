/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smgiaan.persistencia;

import smgiaan.modelo.Pago;
import java.sql.*; 
import java.util.List;
import java.util.ArrayList;
import java.io.*;



public class PagoDAO {

    private Connection getConnection() throws SQLException {
        // Ajusta la URL, usuario y clave según tu DB
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atletas",
                "fidiantest",
                "9n9jCHb9"
        );
    }

    public void insertar(Pago p) throws SQLException {
        String sql = "INSERT INTO pagos (atleta_id, mes, periodo, monto, fechaGenerado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getAtletaId());
            ps.setInt(2, p.getMes());
            ps.setString(3, p.getPeriodo());
            ps.setDouble(4, p.getMonto());
            ps.setDate(5, java.sql.Date.valueOf(p.getFechaGenerado()));

            ps.executeUpdate();

            // obtener ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));  // asigna el ID autogenerado
                }
            }
        }
    }
    
    public List<Pago> listarPorAtleta(int atletaId) throws SQLException {
    List<Pago> lista = new ArrayList<>();
    String sql = "SELECT * FROM pagos WHERE atleta_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, atletaId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pago p = new Pago();
                p.setId(rs.getInt("id"));
                p.setAtletaId(rs.getInt("atleta_id"));
                p.setMes(rs.getInt("mes"));
                p.setPeriodo(rs.getString("periodo"));
                p.setMonto(rs.getDouble("monto"));
                p.setFechaGenerado(rs.getDate("fechaGenerado").toLocalDate());
                lista.add(p);
            }
        }
    }
    return lista;
}

 public void exportarPagosCSV(String rutaArchivo) throws IOException, SQLException {
    var lista = listarTodos();
    try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
        writer.println("ID,AtletaID,Mes,Periodo,Monto,FechaGenerado");
        for (var p : lista) {
            writer.printf("%d,%d,%d,%s,%.2f,%s%n",
                p.getId(), p.getAtletaId(), p.getMes(), p.getPeriodo(),
                p.getMonto(), p.getFechaGenerado());
        }
    }
}
public List<Pago> listarTodos() throws SQLException {
    List<Pago> lista = new ArrayList<>();
    String sql = "SELECT * FROM pagos";
    try (Connection conn = getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            Pago p = new Pago();
            p.setId(rs.getInt("id"));
            p.setAtletaId(rs.getInt("atleta_id"));
            p.setMes(rs.getInt("mes"));
            p.setPeriodo(rs.getString("periodo"));
            p.setMonto(rs.getDouble("monto"));
            p.setFechaGenerado(rs.getDate("fechaGenerado").toLocalDate());
            lista.add(p);
        }
    }
    return lista;
}



}

