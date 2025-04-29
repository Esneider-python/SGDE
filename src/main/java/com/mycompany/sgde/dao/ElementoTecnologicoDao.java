package com.mycompany.sgde.dao;

import com.inventario.modelo.ElementoTecnologico;
import java.sql.*;


public class ElementoTecnologicoDao {
    private final Connection conexion;

    public ElementoTecnologicoDao(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(ElementoTecnologico elementoTecnologico) throws SQLException {
        String sql = "INSERT INTO elementos_tecnologicos(elemento_id, marca, serie) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, elementoTecnologico.getIdElemento());
            stmt.setString(2, elementoTecnologico.getMarca());
            stmt.setString(3, elementoTecnologico.getSerie());
            stmt.executeUpdate();
        }
    }

    public boolean actualizar(ElementoTecnologico tecnologico) throws SQLException {
        String sql = "UPDATE elementos_tecnologicos SET marca = ?, serie = ? WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tecnologico.getMarca());
            stmt.setString(2, tecnologico.getSerie());
            stmt.setInt(3, tecnologico.getIdElemento());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean existe(int idElemento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM elementos_tecnologicos WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}

// =====================
