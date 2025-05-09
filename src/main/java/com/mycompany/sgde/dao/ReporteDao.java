package com.mycompany.sgde.dao;

import com.inventario.modelo.Reporte;
import com.mycompany.sgde.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDao {

    // OBTENER POR ID
    public Reporte obtenerPorId(int id) {
        String sql = "SELECT * FROM reporte WHERE id_reporte = ?";
        Reporte reporte = null;

        try (Connection conexion = Conexion.getConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reporte = new Reporte(
                            rs.getString("descripcion"),
                            rs.getInt("elemento_reportado"),
                            rs.getInt("usuario_reporta"));
                    reporte.setIdReporte(rs.getInt("id_reporte"));
                    reporte.setFechaHoraReporte(rs.getTimestamp("fecha_reporte"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reporte;
    }

    // ELIMINAR REPORTE
    public boolean eliminarReporte(int idElemento) {
        String sql = "DELETE FROM reporte WHERE elemento_reportado = ?";

        try (Connection conexion = Conexion.getConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idElemento);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // OBTENER POR USUARIO
    public List<Reporte> obtenerPorUsuario(int idUsuario) {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM reporte WHERE usuario_reporta = ?";

        try (Connection conexion = Conexion.getConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reporte reporte = new Reporte(
                            rs.getString("descripcion"),
                            rs.getInt("elemento_reportado"),
                            rs.getInt("usuario_reporta"));
                    reporte.setIdReporte(rs.getInt("id_reporte"));
                    reporte.setFechaHoraReporte(rs.getTimestamp("fecha_reporte"));
                    lista.add(reporte);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ACTUALIZAR REPORTE
    public boolean actualizarReporte(Reporte reporte) {
        String sql = "UPDATE reporte SET descripcion = ?, elemento_reportado = ?, usuario_reporta = ? WHERE id_reporte = ?";

        try (Connection conexion = Conexion.getConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, reporte.getDescripcion());
            ps.setInt(2, reporte.getElementoReportado());
            ps.setInt(3, reporte.getUsuarioReporta());
            ps.setInt(4, reporte.getIdReporte());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // REGISTRAR REPORTE
    public boolean registrarReporte(Connection conexion, String descripcion, int elementoId, int usuarioId) throws SQLException {
        String sql = "INSERT INTO reporte (descripcion, elemento_reportado, usuario_reporta) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            ps.setInt(2, elementoId);
            ps.setInt(3, usuarioId);
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<Reporte> obtenerTodos() {
    String sql = "SELECT * FROM reporte";
    List<Reporte> reportes = new ArrayList<>();

    try (Connection conn = Conexion.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            // Crear objeto Reporte usando los datos del ResultSet
            Reporte reporte = new Reporte(
                rs.getInt("id_reporte"),
                rs.getTimestamp("fecha_hora_reporte"),
                rs.getString("descripcion"),
                rs.getInt("elemento_reportado"),
                rs.getInt("usuario_reporta")
            );
            reportes.add(reporte);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return reportes;
}

}
