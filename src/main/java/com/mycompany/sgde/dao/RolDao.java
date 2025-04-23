package com.mycompany.sgde.dao;

import com.inventario.modelo.Rol;
import com.mycompany.sgde.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDao {

    private final Connection conexion;

    // CONSTRUCTOR
    public RolDao(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR ROL
    public int insertarRol(Rol rol) {
        String sql = "INSERT INTO rol(nombre_rol) VALUES (?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, rol.getNombreRol());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // ID generado
                }
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    // OBTENER POR ID
    public Rol obtenerPorId(int idRol) throws SQLException {
        String sql = "SELECT * FROM rol WHERE id_rol = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"));
                }
            }
        }
        return null;
    }

    // OBTENERTODOS
    public List<Rol> obtenerTodos() throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM rol";
        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol")));
            }
        }
        return roles;
    }

    // ACTUALIZAR ROL
    public boolean actualizarRol(Rol rol) throws SQLException {
        String sql = "UPDATE rol SET nombre_rol = ? WHERE id_rol = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, rol.getNombreRol());
            ps.setInt(2, rol.getIdRol());
            return ps.executeUpdate() > 0;
        }
    }

    // ELIMINAR ROL
    public boolean eliminarRol(int idRol) throws SQLException {
        String sql = "DELETE FROM rol WHERE id_rol = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            return ps.executeUpdate() > 0;
        }
    }
}
