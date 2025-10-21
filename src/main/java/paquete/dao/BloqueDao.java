package paquete.dao;

import paquete.modelo.Bloque;
import paquete.modelo.Sede;
import paquete.modelo.Usuario;
import paquete.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloqueDao {

    // Insertar bloque
    public boolean insertar(Bloque bloque) {
        String sql = "INSERT INTO bloques (numero_bloque, sede_id, usuario_id) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bloque.getNumeroBloque());
            stmt.setInt(2, bloque.getSede().getId());
            stmt.setInt(3, bloque.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bloque.setId(rs.getInt(1));
                    }
                    return true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener bloque por ID
    public Bloque obtenerPorId(int id) {
        String sql = "SELECT * FROM bloques WHERE id_bloque = ?";
        Bloque bloque = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int numeroBloque = rs.getInt("numero_bloque");
                    Sede sede = new SedeDao().obtenerPorId(rs.getInt("sede_id"));
                    Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));

                    bloque = new Bloque(rs.getInt("id_bloque"), numeroBloque, sede, usuario);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bloque;
    }

    // Obtener todos los bloques
    public List<Bloque> obtenerTodos() {
        String sql = "SELECT * FROM bloques";
        List<Bloque> bloques = new ArrayList<>();

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idBloque = rs.getInt("id_bloque");
                int numeroBloque = rs.getInt("numero_bloque");
                Sede sede = new SedeDao().obtenerPorId(rs.getInt("sede_id"));
                Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));

                Bloque bloque = new Bloque(idBloque, numeroBloque, sede, usuario);
                bloques.add(bloque);
                // se obtiene:  id, numero bloque, id sede, id usuario
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bloques;
    }

    // Actualizar bloque
    public boolean actualizar(Bloque bloque) {
        String sql = "UPDATE bloques SET numero_bloque = ?, sede_id = ?, usuario_id = ? WHERE id_bloque = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bloque.getNumeroBloque());
            stmt.setInt(2, bloque.getSede().getId());
            stmt.setInt(3, bloque.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, bloque.getId());
            stmt.executeUpdate();
            int filas = stmt.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Eliminar bloque
    public boolean eliminar(int id) {
        String sql = "DELETE FROM bloques WHERE id_bloque = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();  // Devuelve cuántas filas se eliminaron
            return filas > 0;

        } catch (SQLException e) {
            // Error de integridad referencial (SQLState 23000 en MySQL)
            if ("23000".equals(e.getSQLState())) {
                System.err.println("No se puede eliminar: bloque referenciado en otras tablas.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    // Obtener usuario por ID (interno)
    private Usuario obtenerUsuarioPorId(int usuarioId) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("cedula"),
                            rs.getString("contrasena"),
                            rs.getInt("rol_id"),
                            null
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
