package com.smgiaan.servicio;

import com.smgiaan.modelo.Pago;
import com.smgiaan.modelo.Entrenamiento;
import com.smgiaan.persistencia.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class PagoServicio {

    private static final double SUELDO_BASE = 7000.0;
    private static final double BONO_ENTRENAMIENTO = 200.0;
    private static final double BONO_ENTR_EXTRANJERO = 50.0;
    private static final double BONO_MEJOR_MARCA = 250.0;

    // 🔹 Método que calcula y registra el pago automáticamente
    public Pago calcularYRegistrarPago(int atletaId, int mes, String periodo) throws SQLException {
        List<Entrenamiento> entrenamientos = obtenerEntrenamientosPorAtletaYMes(atletaId, mes);

        int total = entrenamientos.size();
        int extranjeros = (int) entrenamientos.stream().filter(e -> !e.isNacional()).count();
        boolean superoMarca = entrenamientos.stream().anyMatch(e -> e.getValorRendimiento() > 90); // ejemplo de regla

        double monto = calcularPago(total, extranjeros, superoMarca);

        Pago pago = new Pago();
        pago.setAtletaId(atletaId);
        pago.setMes(mes);
        pago.setPeriodo(periodo);
        pago.setMonto(monto);
        pago.setFechaGenerado(LocalDate.now());

        registrarPago(pago);

        return pago;
    }

    // 🔹 Método para registrar manualmente un pago
    public void registrarPago(Pago pago) throws SQLException {
        String sql = "INSERT INTO pagos (atleta_id, mes, periodo, monto, fechaGenerado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pago.getAtletaId());
            ps.setInt(2, pago.getMes());
            ps.setString(3, pago.getPeriodo());
            ps.setDouble(4, pago.getMonto());
            ps.setDate(5, Date.valueOf(pago.getFechaGenerado()));
            ps.executeUpdate();
        }
    }

    // 🔹 Obtener todos los pagos
    public List<Pago> listarTodos() throws SQLException {
        String sql = "SELECT * FROM pagos";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Pago> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapPago(rs));
            }
            return lista;
        }
    }

    // 🔹 Obtener pagos por atleta
    public List<Pago> listarPagosPorAtleta(int atletaId) throws SQLException {
        String sql = "SELECT * FROM pagos WHERE atleta_id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, atletaId);
            ResultSet rs = ps.executeQuery();
            List<Pago> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapPago(rs));
            }
            return lista;
        }
    }

    // 🔹 Exportar pagos a CSV
    public void exportarPagosCSV(String rutaArchivo) throws Exception {
        List<Pago> pagos = listarTodos();
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write("ID,AtletaID,Mes,Periodo,Monto,FechaGenerado\n");
            for (Pago p : pagos) {
                writer.write(p.getId() + "," + p.getAtletaId() + "," + p.getMes() + "," +
                             p.getPeriodo() + "," + p.getMonto() + "," + p.getFechaGenerado() + "\n");
            }
        }
    }

    // 🔹 Cálculo del pago base
    public double calcularPago(int entrenamientos, int entrenamientosExtranjero, boolean superoMarca) {
        double pago = SUELDO_BASE;
        pago += entrenamientos * BONO_ENTRENAMIENTO;
        pago += entrenamientosExtranjero * BONO_ENTR_EXTRANJERO;
        if (superoMarca) pago += BONO_MEJOR_MARCA;
        return pago;
    }

    // 🔹 Obtener entrenamientos del atleta para el mes dado
    private List<Entrenamiento> obtenerEntrenamientosPorAtletaYMes(int atletaId, int mes) throws SQLException {
        String sql = "SELECT * FROM entrenamientos WHERE atleta_id = ? AND MONTH(fecha) = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, atletaId);
            ps.setInt(2, mes);
            ResultSet rs = ps.executeQuery();
            List<Entrenamiento> lista = new ArrayList<>();
            while (rs.next()) {
                Entrenamiento e = new Entrenamiento();
                e.setId(rs.getInt("id"));
                e.setAtletaId(rs.getInt("atleta_id"));
                e.setFecha(rs.getDate("fecha").toLocalDate());
                e.setTipoEntrenamiento(rs.getString("tipoEntrenamiento"));
                e.setValorRendimiento(rs.getDouble("valorRendimiento"));
                e.setPais(rs.getString("pais"));
                e.setUbicacion(rs.getString("ubicacion"));
                lista.add(e);
            }
            return lista;
        }
    }

    // 🔹 Mapear un registro de ResultSet a un objeto Pago
    private Pago mapPago(ResultSet rs) throws SQLException {
        Pago p = new Pago();
        p.setId(rs.getInt("id"));
        p.setAtletaId(rs.getInt("atleta_id"));
        p.setMes(rs.getInt("mes"));
        p.setPeriodo(rs.getString("periodo"));
        p.setMonto(rs.getDouble("monto"));
        p.setFechaGenerado(rs.getDate("fechaGenerado").toLocalDate());
        return p;
    }
}
