package com.mycompany.sgde.dao;

import com.inventario.modelo.CambioIdentificador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CambioIdentificadorDAO {

    private final Connection conexion;

    public CambioIdentificadorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Inserta un nuevo registro en la tabla cambios_identificador
    public void insertar(int idElemento, String identificadorAnterior, String tipoIdentificadorAnterior,
            String identificadorNuevo, String tipoIdentificadorNuevo, int usuarioModifica) throws SQLException {

        String sql = "INSERT INTO cambios_identificador "
                + "(id_elemento, identificador_anterior, tipo_identificador_anterior, "
                + "identificador_nuevo, tipo_identificador_nuevo, usuario_modifica) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            stmt.setString(2, identificadorAnterior);
            stmt.setString(3, tipoIdentificadorAnterior);
            stmt.setString(4, identificadorNuevo);
            stmt.setString(5, tipoIdentificadorNuevo);
            stmt.setInt(6, usuarioModifica);
            stmt.executeUpdate();
        }
    }

    // Obtiene todos los registros de cambios_identificador
    public List<CambioIdentificador> obtenerTodos() throws SQLException {
        List<CambioIdentificador> cambios = new ArrayList<>();
        String sql = "SELECT * FROM cambios_identificador";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CambioIdentificador cambio = new CambioIdentificador(
                        rs.getInt("id_cambio"),
                        rs.getInt("id_elemento"),
                        rs.getString("identificador_anterior"),
                        rs.getString("tipo_identificador_anterior"),
                        rs.getString("identificador_nuevo"),
                        rs.getString("tipo_identificador_nuevo"),
                        rs.getInt("usuario_modifica"),
                        rs.getTimestamp("fecha_modificacion")
                );
                cambios.add(cambio);
            }
        }
        return cambios;
    }
}
