package com.mycompany.sgde.dao;

import java.sql.*;

public class ElementoMobiliarioDao {
    private final Connection conexion;

    public ElementoMobiliarioDao(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarMobiliario(int idElemento) throws SQLException {
        String sql = "INSERT INTO elementos_mobiliarios(elemento_id) VALUES (?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            stmt.executeUpdate();
        }
    }

    public boolean existeMobiliario(int idElemento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM elementos_mobiliarios WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
