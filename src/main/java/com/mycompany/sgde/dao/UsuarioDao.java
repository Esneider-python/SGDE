package com.mycompany.sgde.dao;

import com.inventario.modelo.Usuario;
import java.sql.*;

public class UsuarioDao {

    private final Connection conexion;

    public UsuarioDao(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR USUARIO
    // INSERTAR USUARIO Y OBTENER ID GENERADO
    public boolean insertarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombres, apellidos, telefono, correo, cedula, contrasena, rol_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getCedula());
            ps.setString(6, usuario.getContrasena());
            ps.setInt(7, usuario.getRolId());

            System.out.println("Insertando usuario: " + usuario.getCorreo());

            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                // Obtener el ID generado automáticamente
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        usuario.setIdUsuario(idGenerado); // Guarda el ID en el modelo si es necesario
                        System.out.println("ID generado del nuevo usuario: " + idGenerado);
                    }
                }
                return true;
            }

            return false;
        }
    }

    // OBTENER USUARIO POR ID
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u JOIN rol r ON u.rol = r.id WHERE u.id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirUsuarioDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    // ACTUALIZAR USUARIO
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        boolean tieneContrasena = usuario.getContrasena() != null && !usuario.getContrasena().isEmpty();

        String sql = tieneContrasena
                ? "UPDATE usuarios SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, cedula = ?, contrasena = ?, rol = ? WHERE id = ?"
                : "UPDATE usuarios SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, cedula = ?, rol = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getCedula());

            if (tieneContrasena) {
                ps.setString(6, usuario.getContrasena());
                ps.setInt(7, usuario.getRolId());
            } else {
                ps.setInt(6, usuario.getRolId());
            }

            return ps.executeUpdate() > 0;
        }
    }

    // ELIMIANR USUARIO POR ID
    public boolean eliminarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // CAMBIAR CONTRASEÑA POR ID DE USUARIO
    public boolean cambiarContrasena(int idUsuario, String nuevaContrasena) throws SQLException {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevaContrasena);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // MODIFICAR CONTRASEÑA DESDE PERFIL
    public boolean cambiarContrasenaUsuario(String correo, String contrasenaActual, String nuevaContrasena)
            throws SQLException {
        String sqlVerificar = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try (PreparedStatement psVerificar = conexion.prepareStatement(sqlVerificar)) {
            psVerificar.setString(1, correo);
            psVerificar.setString(2, contrasenaActual);

            try (ResultSet rs = psVerificar.executeQuery()) {
                if (rs.next()) {
                    String sqlActualizar = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
                    try (PreparedStatement psActualizar = conexion.prepareStatement(sqlActualizar)) {
                        psActualizar.setString(1, nuevaContrasena);
                        psActualizar.setString(2, correo);
                        return psActualizar.executeUpdate() > 0;
                    }
                }
            }
        }
        return false;
    }

    //VALIDAR CREDENCIALES
    public Usuario validarCredenciales(String correo, String contrasena) throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol AS nombreRol FROM usuarios u JOIN rol r ON u.rol_id = r.id_rol WHERE u.correo = ? AND u.contrasena = ?";

              try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirUsuarioDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    // Método auxiliar para construir un objeto Usuario desde un ResultSet
    private Usuario construirUsuarioDesdeResultSet(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("cedula"),
                rs.getString("contrasena"),
                rs.getInt("rol_id"),
                rs.getString("nombreRol") // Solo si haces JOIN con la tabla rol
        );
    }

    //BUSCAR USUARIO POR CORREO
    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean existeCedula(String cedula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE cedula = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

}
