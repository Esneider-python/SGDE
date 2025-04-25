package com.mycompany.sgde.dao;

import com.inventario.modelo.DocenteAula;
import com.mycompany.sgde.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteAulaDao {

    // Asigna un aula a un docente con horario
    public boolean asignarAulaADocente(DocenteAula da) {
        String sql = "INSERT INTO docente_aula (id_usuario, id_aula, dia_semana, hora_inicio, hora_fin, fecha_asignacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, da.getIdUsuario());
            stmt.setInt(2, da.getIdAula());
            stmt.setString(3, da.getDia());
            stmt.setString(4, da.getHoraInicio());
            stmt.setString(5, da.getHoraFin());
            stmt.setTimestamp(6, da.getFechaAsignacion());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al asignar aula al docente: " + e.getMessage());
            return false;
        }
    }

    // Lista todas las asignaciones
    public List<DocenteAula> listarTodasLasAsignaciones() {
        List<DocenteAula> lista = new ArrayList<>();
        String sql = "SELECT * FROM docente_aula";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DocenteAula da = new DocenteAula();
                da.setId(rs.getInt("id_docente_aula"));
                da.setIdUsuario(rs.getInt("id_usuario"));
                da.setIdAula(rs.getInt("id_aula"));
                da.setDia(rs.getString("dia_semana"));
                da.setHoraInicio(rs.getString("hora_inicio"));
                da.setHoraFin(rs.getString("hora_fin"));
                da.setFechaAsignacion(rs.getTimestamp("fecha_asignacion"));
                lista.add(da);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar asignaciones: " + e.getMessage());
        }

        return lista;
    }

    // Elimina una asignación específica
    public boolean eliminarAsignacion(int idDocenteAula) {
        String sql = "DELETE FROM docente_aula WHERE id_docente_aula = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDocenteAula);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar asignación: " + e.getMessage());
            return false;
        }
    }

    // Lista asignaciones por usuario
    public List<DocenteAula> obtenerAulasPorUsuario(int idUsuario) {
        List<DocenteAula> lista = new ArrayList<>();
        String sql = "SELECT * FROM docente_aula WHERE id_usuario = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DocenteAula da = new DocenteAula();
                    da.setId(rs.getInt("id_docente_aula"));
                    da.setIdUsuario(rs.getInt("id_usuario"));
                    da.setIdAula(rs.getInt("id_aula"));
                    da.setDia(rs.getString("dia_semana"));
                    da.setHoraInicio(rs.getString("hora_inicio"));
                    da.setHoraFin(rs.getString("hora_fin"));
                    da.setFechaAsignacion(rs.getTimestamp("fecha_asignacion"));
                    lista.add(da);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener asignaciones del usuario: " + e.getMessage());
        }

        return lista;
    }
}
