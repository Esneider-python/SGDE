package com.mycompany.sgde.dao;

import com.inventario.modelo.ElementosMobiliarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementoMobiliarioDao {

    private final Connection conexion;

    public ElementoMobiliarioDao(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarMobiliario(int idElemento) throws SQLException {
        String sql = "INSERT INTO elementos_mobiliarios(elemento_id) VALUES (?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            stmt.executeUpdate();
        }
    }

    public boolean existeMobiliario(int idElemento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM elementos_mobiliarios WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    // 🔽 MÉTODO NUEVO PARA LISTAR ELEMENTOS MOBILIARIOS

    public List<ElementosMobiliarios> listarElementos() throws SQLException {
        List<ElementosMobiliarios> elementos = new ArrayList<>();

        String sql = """
        SELECT e.id_elemento, e.nombre, e.estado, e.usuario_registra,
               e.aula_id, e.identificador_unico, e.tipo_identificador, e.fecha_creacion
        FROM elementos_mobiliarios em
        JOIN elementos e ON em.elemento_id = e.id_elemento
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ElementosMobiliarios elemento = new ElementosMobiliarios();

                // Datos comunes heredados de Elemento
                elemento.setIdElemento(rs.getInt("id_elemento"));
                elemento.setNombre(rs.getString("nombre"));
                elemento.setEstado(rs.getString("estado"));
                elemento.setUsuarioRegistra(rs.getInt("usuario_registra"));
                elemento.setAulaId(rs.getInt("aula_id"));
                elemento.setIdentificadorUnico(rs.getString("identificador_unico"));
                elemento.setTipoIdentificador(rs.getString("tipo_identificador"));
                elemento.setFechaCreacion(rs.getTimestamp("fecha_creacion"));

                elementos.add(elemento);
            }
        }

        return elementos;
    }

    public ElementosMobiliarios obtenerPorId(int idElemento) throws SQLException {
        String sql = "SELECT e.id_elemento, e.nombre, e.estado, e.usuario_registra, e.aula_id, "
                + "e.identificador_unico, e.tipo_identificador, e.fecha_creacion "
                + "FROM elementos e "
                + "INNER JOIN elementos_mobiliarios m ON e.id_elemento = m.elemento_id "
                + "WHERE e.id_elemento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ElementosMobiliarios elemento = new ElementosMobiliarios();
                elemento.setIdElemento(rs.getInt("id_elemento"));
                elemento.setNombre(rs.getString("nombre"));
                elemento.setEstado(rs.getString("estado"));
                elemento.setUsuarioRegistra(rs.getInt("usuario_registra"));
                elemento.setAulaId(rs.getInt("aula_id"));
                elemento.setIdentificadorUnico(rs.getString("identificador_unico"));
                elemento.setTipoIdentificador(rs.getString("tipo_identificador"));
                elemento.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                return elemento;
            }
        }
        return null; // Si no se encuentra
    }
}
