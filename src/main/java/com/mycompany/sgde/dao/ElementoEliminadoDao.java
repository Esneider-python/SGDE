package com.mycompany.sgde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElementoEliminadoDao {

    private final Connection conexion;

    public ElementoEliminadoDao(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean eliminarElemento(int idElemento) throws SQLException {
        String sql = "DELETE FROM elementos WHERE id_elemento = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idElemento);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean registrarElementoEliminado(int idElemento, String motivo, int idUsuario) throws SQLException {
        if (idElemento <= 0 || idUsuario <= 0 || motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Los datos para registrar la eliminación no son válidos.");
        }

        String sql = "INSERT INTO elementos_eliminados (elemento_id, motivo_eliminacion, usuario_elimino) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idElemento);
            ps.setString(2, motivo.trim());
            ps.setInt(3, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneHijos(int idElemento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM elementos_tecnologicos WHERE id_elemento = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idElemento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
