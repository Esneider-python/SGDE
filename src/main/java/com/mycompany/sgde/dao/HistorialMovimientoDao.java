package com.mycompany.sgde.dao;

import com.inventario.modelo.HistorialMovimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistorialMovimientoDao {

    private final Connection conexion;

    public HistorialMovimientoDao(Connection conexion) {
        this.conexion = conexion;
    }

    // REGISTRAR MOVIMIENTO
    public void insertarMovimiento(int idElemento, String tipoElemento, int aulaOrigenId, int aulaDestinoId, int usuarioId) throws SQLException {
        String sql = "INSERT INTO historial_movimientos (elemento_id, tipo_elemento, aula_origen, aula_destino, usuario_movio) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            stmt.setString(2, tipoElemento);
            stmt.setInt(3, aulaOrigenId);
            stmt.setInt(4, aulaDestinoId);
            stmt.setInt(5, usuarioId);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("❌ No se pudo registrar el movimiento en el historial.");
            }
        }
    }

    // OBTENER TODOS LOS MOVIMIENTOS
    public List<HistorialMovimiento> obtenerTodos() {
        List<HistorialMovimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM historial_movimientos";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HistorialMovimiento mov = new HistorialMovimiento(
                        rs.getInt("id_historial"),
                        rs.getInt("elemento_id"),
                        rs.getString("tipo_elemento"),
                        rs.getInt("aula_origen"),
                        rs.getInt("aula_destino"),
                        rs.getInt("usuario_movio"),
                        rs.getTimestamp("fecha_movimiento")
                );
                movimientos.add(mov);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los movimientos: " + e.getMessage());
        }

        return movimientos;
    }

    // OBTENER MOVIMIENTO POR ID
    public Optional<HistorialMovimiento> obtenerPorId(int id) {
        String sql = "SELECT * FROM historial_movimientos WHERE id_historial = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HistorialMovimiento movimiento = new HistorialMovimiento(
                            rs.getInt("id_historial"),
                            rs.getInt("elemento_id"),
                            rs.getString("tipo_elemento"),
                            rs.getInt("aula_origen"),
                            rs.getInt("aula_destino"),
                            rs.getInt("usuario_movio"),
                            rs.getTimestamp("fecha_movimiento")
                    );
                    return Optional.of(movimiento);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener movimiento por ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    // ACTUALIZAR MOVIMIENTO
    public boolean actualizarMovimiento(HistorialMovimiento movimiento) {
        String sql = "UPDATE historial_movimientos SET tipo_elemento = ?, aula_origen = ?, aula_destino = ?, usuario_movio = ?, fecha_movimiento = NOW() WHERE id_historial = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, movimiento.getTipoElemento());
            stmt.setInt(2, movimiento.getAulaOrigen());
            stmt.setInt(3, movimiento.getAulaDestino());
            stmt.setInt(4, movimiento.getUsuarioMovio());
            stmt.setInt(5, movimiento.getIdHistorial());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar movimiento: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR MOVIMIENTO
    public boolean eliminarMovimiento(int id) {
        String sql = "DELETE FROM historial_movimientos WHERE id_historial = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar movimiento: " + e.getMessage());
            return false;
        }
    }
}
