package paquete.dao;

import paquete.modelo.DocenteAula;
import paquete.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteAulaDao {

    // Asigna un aula a un docente con horario
    public boolean asignarAulaADocente(DocenteAula da) {
        String sql = "INSERT INTO docente_aula (id_usuario, id_aula, dia_semana, hora_inicio, hora_fin, estado) VALUES ( ?, ?, ?, ?, ?,?)";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, da.getIdUsuario());
            stmt.setInt(2, da.getIdAula());
            stmt.setString(3, da.getDia());

            stmt.setTime(4, Time.valueOf(da.getHoraInicio()));
            stmt.setTime(5, Time.valueOf(da.getHoraFin()));
            stmt.setString(6, da.getEstado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al asignar aula al docente: " + e.getMessage());
            return false;
        }
    }

    // Obtiene las asignaciones de un usuario específico
    public List<DocenteAula> obtenerAsignacionesPorUsuario(int idUsuario) throws SQLException {
        List<DocenteAula> asignaciones = new ArrayList<>();
        String sql = "SELECT * FROM docente_aula WHERE id_usuario = ? AND estado = 'activo' ";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DocenteAula asignacion = new DocenteAula();
                    asignacion.setId(rs.getInt("id"));
                    asignacion.setIdUsuario(rs.getInt("id_usuario"));
                    asignacion.setIdAula(rs.getInt("id_aula"));
                    asignacion.setDia(rs.getString("dia_semana"));
                    asignacion.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    asignacion.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                    asignaciones.add(asignacion);
                }
            }
        }
        return asignaciones;
    }

    public boolean actualizarEstadoAsignacion(int asignacionId, String nuevoEstado, Connection con) throws SQLException {
        String sql = "UPDATE docente_aula SET estado = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, asignacionId);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }
}
