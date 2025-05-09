package com.mycompany.sgde.dao;

import com.inventario.modelo.Aula;
import com.inventario.modelo.Piso;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDao {

    private Connection conexion;

    public AulaDao(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para insertar un aula y recuperar su ID autogenerado
    public void insertar(Aula aula) {
        String sql = "INSERT INTO aulas (numero_aula, piso_id, usuario_id) VALUES (?,?, ?)";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, aula.getNumeroAula());
            stmt.setInt(2, aula.getPiso().getId());
            stmt.setInt(3, aula.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aula.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener un aula por su ID
    public Aula obtenerPorId(int id) {
        String sql = "SELECT * FROM aulas WHERE id_aula = ?";
        Aula aula = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Piso piso = new PisoDao().obtenerPorId(rs.getInt("piso_id"));
                    // Solo asignamos el ID de usuario, sin cargar datos adicionales
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("usuario_id"));

                    aula = new Aula(
                            rs.getInt("id_aula"),
                            rs.getInt("numero_aula"),
                            piso,
                            usuario
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aula;
    }

    // Método para obtener todas las aulas
    public List<Aula> obtenerTodos() {
        String sql = "SELECT * FROM aulas";
        List<Aula> aulas = new ArrayList<>();

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Piso piso = new PisoDao().obtenerPorId(rs.getInt("piso_id"));
                // Solo asignamos el ID de usuario
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("usuario_id"));

                Aula aula = new Aula(
                        rs.getInt("id_aula"),
                        rs.getInt("numero_aula"), // Obtener el numero_aula
                        piso,
                        usuario
                );
                aulas.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aulas;
    }

    // Método para actualizar un aula
    public void actualizar(Aula aula) {
        String sql = "UPDATE aulas SET numero_aula = ?, piso_id = ?, usuario_id = ? WHERE id_aula = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, aula.getNumeroAula());  // Actualizar numero_aula
            stmt.setInt(2, aula.getPiso().getId());
            stmt.setInt(3, aula.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, aula.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un aula por su ID
    public void eliminar(int id) {
        String sql = "DELETE FROM aulas WHERE id_aula = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int obtenerIdPorNumero(int numeroAula) {
        String sql = "SELECT id_aula FROM aulas WHERE numero_aula = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, numeroAula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_aula");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Si no se encuentra
    }

}
