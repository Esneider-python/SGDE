package com.mycompany.sgde.dao;

import com.inventario.modelo.Elemento;
import com.inventario.modelo.ElementoTecnologico;
import com.inventario.modelo.ElementosMobiliarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementoDao {

    private final Connection conexion;
    private final ElementoTecnologicoDao elementoTecnologicoDao;
    private final ElementoMobiliarioDao elementoMobiliarioDao;

    public ElementoDao(Connection conexion) {
        this.conexion = conexion;
        this.elementoTecnologicoDao = new ElementoTecnologicoDao(conexion);
        this.elementoMobiliarioDao = new ElementoMobiliarioDao(conexion);
    }

    public int insertarElemento(Elemento elemento) {
        String sql = "INSERT INTO elementos(nombre, estado, usuario_registra, aula_id, identificador_unico, tipo_identificador) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, elemento.getNombre());
            stmt.setString(2, elemento.getEstado());
            stmt.setInt(3, elemento.getUsuarioRegistra());
            stmt.setInt(4, elemento.getAulaId());
            stmt.setString(5, elemento.getIdentificadorUnico());
            stmt.setString(6, elemento.getTipoIdentificador());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Elemento obtenerPorId(int idElemento) {
        Elemento elemento = null;
        String sql = "SELECT * FROM elementos WHERE id_elemento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                elemento = new Elemento();
                elemento.setIdElemento(rs.getInt("id_elemento"));
                elemento.setNombre(rs.getString("nombre"));
                elemento.setEstado(rs.getString("estado"));
                elemento.setUsuarioRegistra(rs.getInt("usuario_registra"));
                elemento.setAulaId(rs.getInt("aula_id"));
                elemento.setIdentificadorUnico(rs.getString("identificador_unico"));
                elemento.setTipoIdentificador(rs.getString("tipo_identificador"));
                elemento.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elemento;
    }

    public List<Elemento> obtenerPorTipo(String tipo) {
        List<Elemento> lista = new ArrayList<>();
        String sql;
        final String TIPO_TECNOLOGICO = "tecnologico";
        final String TIPO_MOBILIARIO = "mobiliario";

        if (tipo.equalsIgnoreCase(TIPO_TECNOLOGICO)) {
            sql = "SELECT e.*, t.marca, t.serie FROM elementos e INNER JOIN elementos_tecnologicos t ON e.id_elemento = t.elemento_id";
        } else if (tipo.equalsIgnoreCase(TIPO_MOBILIARIO)) {
            sql = "SELECT e.* FROM elementos e INNER JOIN elementos_mobiliarios m ON e.id_elemento = m.elemento_id";
        } else {
            System.out.println("Tipo de elemento no válido. Solo se permite 'tecnologico' o 'mobiliario'.");
            return lista;
        }

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                if (tipo.equalsIgnoreCase(TIPO_TECNOLOGICO)) {
                    ElementoTecnologico tec = new ElementoTecnologico();
                    llenarDatosComunes(rs, tec);
                    tec.setMarca(rs.getString("marca"));
                    tec.setSerie(rs.getString("serie"));
                    lista.add(tec);
                } else {
                    ElementosMobiliarios mob = new ElementosMobiliarios();
                    llenarDatosComunes(rs, mob);
                    lista.add(mob);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void llenarDatosComunes(ResultSet rs, Elemento e) throws SQLException {
        e.setIdElemento(rs.getInt("id_elemento"));
        e.setNombre(rs.getString("nombre"));
        e.setEstado(rs.getString("estado"));
        e.setUsuarioRegistra(rs.getInt("usuario_registra"));
        e.setAulaId(rs.getInt("aula_id"));
        e.setIdentificadorUnico(rs.getString("identificador_unico"));
        e.setTipoIdentificador(rs.getString("tipo_identificador"));
        e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
    }

    public List<Elemento> obtenerPorUsuario(int idUsuario) {
        List<Elemento> lista = new ArrayList<>();
        String sql = "SELECT * FROM elementos WHERE usuario_registra = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idElemento = rs.getInt("id_elemento");
                if (esTecnologico(idElemento)) {
                    ElementoTecnologico t = new ElementoTecnologico();
                    llenarDatosComunes(rs, t);
                    try (PreparedStatement stmtTec = conexion.prepareStatement("SELECT marca, serie FROM elementos_tecnologicos WHERE elemento_id = ?")) {
                        stmtTec.setInt(1, idElemento);
                        ResultSet rsTec = stmtTec.executeQuery();
                        if (rsTec.next()) {
                            t.setMarca(rsTec.getString("marca"));
                            t.setSerie(rsTec.getString("serie"));
                        }
                    }
                    lista.add(t);
                } else if (esMobiliario(idElemento)) {
                    ElementosMobiliarios m = new ElementosMobiliarios();
                    llenarDatosComunes(rs, m);
                    lista.add(m);
                } else {
                    Elemento e = new Elemento();
                    llenarDatosComunes(rs, e);
                    lista.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private boolean esTecnologico(int idElemento) throws SQLException {
        String sql = "SELECT 1 FROM elementos_tecnologicos WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private boolean esMobiliario(int idElemento) throws SQLException {
        String sql = "SELECT 1 FROM elementos_mobiliarios WHERE elemento_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public boolean actualizarElemento(Elemento elemento) {
        String sqlUpdateElemento = "UPDATE elementos SET nombre = ?, usuario_registra = ?, aula_id = ? WHERE id_elemento = ?";

        try (PreparedStatement stmtUpdate = conexion.prepareStatement(sqlUpdateElemento)) {
            boolean esTecnologico = elementoTecnologicoDao.existe(elemento.getIdElemento());
            boolean esMobiliario = elementoMobiliarioDao.existeMobiliario(elemento.getIdElemento());

            if (!esTecnologico && !esMobiliario) {
                System.out.println("El elemento no pertenece a ninguna categoría (ni tecnológico ni mobiliario).");
                return false;
            }

            stmtUpdate.setString(1, elemento.getNombre());
            stmtUpdate.setInt(2, elemento.getUsuarioRegistra());
            stmtUpdate.setInt(3, elemento.getAulaId());
            stmtUpdate.setInt(4, elemento.getIdElemento());

            System.out.println("✅ DATOS ACTUALIZADOS: "
                    + "id = " + elemento.getIdElemento()
                    + ", nombre = " + elemento.getNombre()
                    + ", aula = " + elemento.getAulaId()
                    + ", usuario = " + elemento.getUsuarioRegistra());

            int filas = stmtUpdate.executeUpdate();
            System.out.println("✅ Filas actualizadas en tabla elementos: " + filas);

            if (esTecnologico && elemento instanceof ElementoTecnologico) {
                ElementoTecnologico tecnologico = (ElementoTecnologico) elemento;
                return elementoTecnologicoDao.actualizar(tecnologico);
            }

            return true;
        } catch (SQLException e) {
            System.out.println("error al actualizar " + (elemento instanceof ElementoTecnologico));
            return false;
        }
    }

    public boolean eliminarElemento(int idElemento) {
        String eliminarElemento = "DELETE FROM elementos WHERE id_elemento = ?";
        try (PreparedStatement ps = conexion.prepareStatement(eliminarElemento)) {
            ps.setInt(1, idElemento);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarIdentificador(int idElemento, String nuevoIdentificador, String nuevoTipo) {
        String sqlUpdate = "UPDATE elementos SET identificador_unico = ?, tipo_identificador = ? WHERE id_elemento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlUpdate)) {
            stmt.setString(1, nuevoIdentificador);
            stmt.setString(2, nuevoTipo);
            stmt.setInt(3, idElemento);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el identificador:");
            e.printStackTrace();
            return false;
        }
    }
    public String obtenerIdentificadorPorId(int idElemento) throws SQLException {
    String sql = "SELECT identificador_unico FROM elementos WHERE id_elemento = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idElemento);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("Retornando identificador: " + rs.getString("identificador_unico"));
                return rs.getString("identificador_unico");
            }
        }
    }
    return null; // Retorna null si no se encuentra
}


    public List<Elemento> listarTodos() throws SQLException {
        List<Elemento> elementos = new ArrayList<>();

        ElementoTecnologicoDao daoTec = new ElementoTecnologicoDao(conexion);
        ElementoMobiliarioDao daoMob = new ElementoMobiliarioDao(conexion);

        elementos.addAll(daoTec.listarElementos());
        elementos.addAll(daoMob.listarElementos());

        return elementos;
    }

    public int obtenerAulaIdPorElemento(int idElemento) throws SQLException {
        String sql = "SELECT aula_id FROM elementos WHERE id_elemento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("aula_id");
            }
        }
        return -1;
    }

    public boolean actualizarAula(int idElemento, int nuevoIdAula) throws SQLException {
        String sql = "UPDATE elementos SET aula_id = ? WHERE id_elemento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, nuevoIdAula);
            stmt.setInt(2, idElemento);
            int filasAfectadas = stmt.executeUpdate();
            System.out.println("Se actualizaron " + filasAfectadas + " filas en la tabla elementos.");
            return filasAfectadas > 0;
        }
    }

    public boolean existeAula(int idAula) throws SQLException {
        String sql = "SELECT 1 FROM aulas WHERE id_aula = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idAula);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si hay resultado, el aula existe
            }
        }
    }

    public boolean existeElemento(int idElemento, String tipoElemento) throws SQLException {
        String sql;

        if ("tecnologico".equalsIgnoreCase(tipoElemento)) {
            sql = "SELECT 1 FROM elementos_tecnologicos WHERE elemento_id = ?";
        } else if ("mobiliario".equalsIgnoreCase(tipoElemento)) {
            sql = "SELECT 1 FROM elementos_mobiliarios WHERE elemento_id = ?";
        } else {
            return false; // Tipo inválido
        }

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true si existe al menos un resultado
            }
        }
    }

}
