package com.mycompany.sgde.dao;

import java.util.List;
import java.util.ArrayList;
import com.inventario.modelo.Sede;
import com.inventario.modelo.Colegio;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.util.Conexion;

import java.sql.*;

public class SedeDao {

    // INSERTAR SEDE
    public void insertar(Sede sede) {
        String sql = "INSERT INTO sede (nombre_sede, colegio_id, usuario_id) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sede.getNombre());
            stmt.setInt(2, sede.getColegio().getId());
            stmt.setInt(3, sede.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sede.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // OBTENER SEDE POR ID
    public Sede obtenerPorId(int id) {
        String sql = "SELECT * FROM sede WHERE id_sede = ?";
        Sede sede = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Pasamos la conexión a los DAOs
                    ColegioDao colegioDao = new ColegioDao(conn);
                    UsuarioDao usuarioDao = new UsuarioDao(conn);

                    Colegio colegio = colegioDao.obtenerPorId(rs.getInt("colegio_id"));
                    Usuario usuario = usuarioDao.obtenerUsuarioPorId(rs.getInt("usuario_id"));

                    if (colegio != null && usuario != null) {
                        sede = new Sede(
                                rs.getInt("id_sede"),
                                rs.getString("nombre_sede"),
                                colegio,
                                usuario);
                    } else {
                        // Manejar el caso en que no se encuentra el colegio o usuario
                        System.err.println("No se encontró el colegio o usuario relacionado.");
                    }
                }
            }
        } catch (SQLException e) {
            // Usar un logger para registrar el error
            System.err.println("Error al obtener la sede: " + e.getMessage());
        }

        return sede;
    }

    // ACTUALIZAR SEDE
    public boolean actualizar(Sede sede) {
        String sql = "UPDATE sede SET nombre_sede = ?, colegio_id = ?, usuario_id = ? WHERE id_sede = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sede.getNombre());
            stmt.setInt(2, sede.getColegio().getId());
            stmt.setInt(3, sede.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, sede.getId());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR SEDE
    public boolean eliminar(int id) {
        String sql = "DELETE FROM sede WHERE id_sede = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // OBTENER TODAS LAS SEDES

    public List<Sede> obtenerTodos() {
        List<Sede> sedes = new ArrayList<>();
        String sql = "SELECT * FROM sede";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ColegioDao colegioDao = new ColegioDao(conn); // Se pasa la conexión al DAO
                UsuarioDao usuarioDao = new UsuarioDao(conn);

                Colegio colegio = colegioDao.obtenerPorId(rs.getInt("colegio_id"));
                Usuario usuario = usuarioDao.obtenerUsuarioPorId(rs.getInt("usuario_id"));

                Sede sede = new Sede(
                        rs.getInt("id_sede"),
                        rs.getString("nombre_sede"),
                        colegio,
                        usuario
                );

                sedes.add(sede);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sedes;
    }

    public boolean existeSede(int idSede) {
        String sql = "SELECT COUNT(*) FROM sede WHERE id_sede = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSede);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
