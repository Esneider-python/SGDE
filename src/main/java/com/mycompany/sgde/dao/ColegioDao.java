package com.mycompany.sgde.dao;

import com.inventario.modelo.Colegio;
import com.inventario.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColegioDao {

    private Connection conn;

    public ColegioDao(Connection conn) {
        this.conn = conn;
    }

    // INSERTAR UN NUEVO COLEGIO
    public void insertar(Colegio colegio) {
        String sql = "INSERT INTO colegio (nombre_colegio, usuario_registra) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, colegio.getNombre());
            stmt.setInt(2, colegio.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    colegio.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar el colegio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // CONSULTAR COLEGIO POR ID
    public Colegio obtenerPorId(int id) {
        String sql = "SELECT * FROM colegio WHERE id_colegio = ?";
        Colegio colegio = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("usuario_registra"));

                    colegio = new Colegio(
                            rs.getInt("id_colegio"),
                            rs.getString("nombre_colegio"),
                            usuario
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colegio;
    }

    public List<Colegio> obtenerTodos() {
        String sql = "SELECT * FROM colegio";
        List<Colegio> colegios = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("usuario_registra"));

                Colegio colegio = new Colegio(
                        rs.getInt("id_colegio"),
                        rs.getString("nombre_colegio"),
                        usuario
                );
                colegios.add(colegio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colegios;
    }

    public void actualizar(Colegio colegio) {
        String sql = "UPDATE colegio SET nombre_colegio = ?, usuario_registra = ? WHERE id_colegio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colegio.getNombre());
            stmt.setInt(2, colegio.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(3, colegio.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM colegio WHERE id_colegio = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate(); // Devuelve cuántas filas eliminó
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Si hay error, devuelve 0
        }
    }

    // NUEVO MÉTODO PARA VERIFICAR SI UN COLEGIO EXISTE
    public boolean existeColegio(String nombreColegio) {
        String sql = "SELECT COUNT(*) FROM colegio WHERE nombre_colegio = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreColegio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si hay al menos un registro
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si no se encontró el colegio
    }

    public Integer obtenerIdPorCedula(String cedula) {
        String sql = "SELECT id_usuario FROM usuarios WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario"); // Retorna el ID del usuario
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encontró el usuario
    }

}
