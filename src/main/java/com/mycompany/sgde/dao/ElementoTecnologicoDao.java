package com.mycompany.sgde.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.inventario.modelo.ElementoTecnologico;

public class ElementoTecnologicoDao {

    private final Connection conexion;

    public ElementoTecnologicoDao(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(ElementoTecnologico elementoTecnologico) throws SQLException {
        String sql = "INSERT INTO elementos_tecnologicos(elemento_id, marca, serie) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, elementoTecnologico.getIdElemento());
            stmt.setString(2, elementoTecnologico.getMarca());
            stmt.setString(3, elementoTecnologico.getSerie());
            stmt.executeUpdate();
        }
    }

    public boolean actualizar(ElementoTecnologico tecnologico) throws SQLException {
        String sql = "UPDATE elementos_tecnologicos SET marca = ?, serie = ? WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tecnologico.getMarca());
            stmt.setString(2, tecnologico.getSerie());
            stmt.setInt(3, tecnologico.getIdElemento());
            int filas = stmt.executeUpdate();
            System.out.println("🔄 filas actualizadas en elementos_tecnologicos: " + filas);
            return filas > 0;
        }
    }

    public boolean existe(int idElemento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM elementos_tecnologicos WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    // Método para listar todos los elementos tecnológicos

    // Método para listar todos los elementos tecnológicos
    public List<ElementoTecnologico> listarElementos() throws SQLException {
        List<ElementoTecnologico> elementos = new ArrayList<>();

        String sql = """
        SELECT e.id_elemento, e.nombre, e.estado, e.usuario_registra,
               e.aula_id, e.identificador_unico, e.tipo_identificador, e.fecha_creacion,
               et.marca, et.serie
        FROM elementos_tecnologicos et
        JOIN elementos e ON et.elemento_id = e.id_elemento
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ElementoTecnologico elemento = new ElementoTecnologico();
                elemento.setIdElemento(rs.getInt("id_elemento"));
                elemento.setNombre(rs.getString("nombre"));
                elemento.setEstado(rs.getString("estado"));
                elemento.setUsuarioRegistra(rs.getInt("usuario_registra"));
                elemento.setAulaId(rs.getInt("aula_id"));
                elemento.setIdentificadorUnico(rs.getString("identificador_unico"));
                elemento.setTipoIdentificador(rs.getString("tipo_identificador"));
                elemento.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                elemento.setMarca(rs.getString("marca"));
                elemento.setSerie(rs.getString("serie"));

                elementos.add(elemento);
            }
        }

        return elementos;
    }

    public ElementoTecnologico obtenerPorId(int idElemento) throws SQLException {
        String sql = "SELECT e.id_elemento, e.nombre, e.estado, e.usuario_registra, e.aula_id, "
                + "e.identificador_unico, e.tipo_identificador, e.fecha_creacion, "
                + "t.marca, t.serie "
                + "FROM elementos e "
                + "INNER JOIN elementos_tecnologicos t ON e.id_elemento = t.elemento_id "
                + "WHERE e.id_elemento = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ElementoTecnologico elemento = new ElementoTecnologico();
                elemento.setIdElemento(rs.getInt("id_elemento"));
                elemento.setNombre(rs.getString("nombre"));
                elemento.setEstado(rs.getString("estado"));
                elemento.setUsuarioRegistra(rs.getInt("usuario_registra"));
                elemento.setAulaId(rs.getInt("aula_id"));
                elemento.setIdentificadorUnico(rs.getString("identificador_unico"));
                elemento.setTipoIdentificador(rs.getString("tipo_identificador"));
                elemento.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                elemento.setMarca(rs.getString("marca"));
                elemento.setSerie(rs.getString("serie"));
                return elemento;
            }
        }
        return null;
    }

}


// =====================
