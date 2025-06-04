package paquete.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import paquete.modelo.ElementoEliminado;
import paquete.util.Conexion;

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

    public List<ElementoEliminado> obtenerElementosEliminados(String cedula, String fechaInicio, String fechaFin) throws SQLException {
        List<ElementoEliminado> elementos = new ArrayList<>();
        String sql = """
        SELECT ee.id_elemento_eliminado, ee.elemento_id, ee.motivo_eliminacion, ee.fecha_hora_eliminacion
        FROM elementos_eliminados ee
        JOIN usuarios u ON ee.usuario_elimino = u.id_usuario
        WHERE u.cedula = ? AND ee.fecha_hora_eliminacion BETWEEN ? AND ?
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            stmt.setString(2, fechaInicio);
            stmt.setString(3, fechaFin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ElementoEliminado e = new ElementoEliminado();
                e.setIdElementoEliminado(rs.getInt("id_elemento_eliminado"));
                e.setElementoId(rs.getInt("elemento_id"));
                e.setMotivoEliminacion(rs.getString("motivo_eliminacion"));
                e.setFechaHoraEliminacion(rs.getTimestamp("fecha_hora_eliminacion"));
                elementos.add(e);
            }
        }

        return elementos;
    }

    public List<ElementoEliminado> obtenerTodos() {
        List<ElementoEliminado> lista = new ArrayList<>();
        String sql = """
        SELECT ee.id_elemento_eliminado, el.id_elemento, ee.motivo_eliminacion, 
               ee.fecha_hora_eliminacion, ee.usuario_elimino
        FROM elementos_eliminados ee
        JOIN elementos el ON ee.elemento_id = el.id_elemento
        ORDER BY ee.fecha_hora_eliminacion DESC
    """;

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ElementoEliminado e = new ElementoEliminado();
                e.setIdElementoEliminado(rs.getInt("id_elemento_eliminado"));
                e.setElementoId(rs.getInt("id_elemento"));
                e.setMotivoEliminacion(rs.getString("motivo_eliminacion"));
                e.setFechaHoraEliminacion(rs.getTimestamp("fecha_hora_eliminacion"));
                e.setUsuarioElimino(rs.getInt("usuario_elimino")); // ⚠️ aquí se mantiene como int
                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

}
