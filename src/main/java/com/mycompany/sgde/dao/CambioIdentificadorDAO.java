package com.mycompany.sgde.dao;


import java.sql.*;

public class CambioIdentificadorDAO {

    private final Connection conexion;

    public CambioIdentificadorDAO(Connection conexion) {
        this.conexion = conexion;

    }

    public void insertar(int IdElemento, String IdentificadorAnterior, String TipoIdentificadorAnterior, String IdentificadorNuevo,
            String TipoIdentificadorNuevo, int UsuarioModifica) throws SQLException {
        String sql = "INSERT INTO cambios_identificador (id_elemento, identificador_anterior,tipo_identificador_anterior, identificador_nuevo,tipo_identificador_nuevo, usuario_modifica) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, IdElemento);
            stmt.setString(2, IdentificadorAnterior);
            stmt.setString(3, TipoIdentificadorAnterior);
            stmt.setString(4, IdentificadorNuevo);
            stmt.setString(5, TipoIdentificadorNuevo);
            stmt.setInt(6,UsuarioModifica);
            stmt.executeUpdate();
        }
    }

}
