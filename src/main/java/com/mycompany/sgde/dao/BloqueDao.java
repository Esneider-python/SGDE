package com.mycompany.sgde.dao;

import com.inventario.modelo.Bloque;
import com.inventario.modelo.Sede;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloqueDao {

    // Insertar bloque
    public void insertar(Bloque bloque) {
        String sql = "INSERT INTO bloques (numero_bloque, sede_id, usuario_id) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bloque.getNumeroBloque());
            stmt.setInt(2, bloque.getSede().getId());
            stmt.setInt(3, bloque.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    bloque.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtener bloque por ID
    public Bloque obtenerPorId(int id) {
        String sql = "SELECT * FROM bloques WHERE id_bloque = ?";
        Bloque bloque = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idBloque = rs.getInt("id_bloque");
                int numeroBloque = rs.getInt("numero_bloque");
                Sede sede = new SedeDao().obtenerPorId(rs.getInt("sede_id"));
                Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));

                Bloque bloque = new Bloque(idBloque, numeroBloque, sede, usuario);
                bloques.add(bloque);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bloques;
    }

    // Actualizar bloque (incluye número de bloque)
    public void actualizar(Bloque bloque) {
        String sql = "UPDATE bloques SET numero_bloque = ?, sede_id = ?, usuario_id = ? WHERE id_bloque = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bloque.getNumeroBloque());
            stmt.setInt(2, bloque.getSede().getId());
            stmt.setInt(3, bloque.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, bloque.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar bloque
    public void eliminar(int id) {
        String sql = "DELETE FROM bloques WHERE id_bloque = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
             stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtener usuario por ID (interno)
    private Usuario obtenerUsuarioPorId(int usuarioId) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
