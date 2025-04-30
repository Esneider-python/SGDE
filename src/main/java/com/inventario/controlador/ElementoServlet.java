package com.inventario.controlador;

import com.inventario.modelo.*;
import com.mycompany.sgde.dao.*;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ElementoServlet")
public class ElementoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", "❌ No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            conexion.setAutoCommit(false);

            try {
                switch (accion) {
                    case "registrarTecnologico":
                        registrarTecnologico(request, conexion);
                        request.setAttribute("mensaje", "✅ Elemento tecnológico registrado con éxito.");
                        break;
                    case "registrarMobiliario":
                        registrarMobiliario(request, conexion);
                        request.setAttribute("mensaje", "✅ Elemento mobiliario registrado con éxito.");
                        break;
                    case "listarTodos":
                        listarTodosLosElementos(request, response);
                        return;

                    default:
                        throw new IllegalArgumentException("Acción no válida.");
                }

                conexion.commit();

            } catch (Exception e) {
                conexion.rollback();
                e.printStackTrace();
                request.setAttribute("error", "❌ Error al registrar el elemento: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Error interno: " + e.getMessage());
        }

        request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
    }

    private void registrarTecnologico(HttpServletRequest request, Connection conexion) throws Exception {
        String nombre = request.getParameter("nombre");
        String estado = request.getParameter("estado");
        String identificador = request.getParameter("identificadorUnico");
        String tipoIdentificador = request.getParameter("tipoIdentificador");
        String marca = request.getParameter("marca");
        String serie = request.getParameter("serie");
        int idAula = Integer.parseInt(request.getParameter("idAula"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        UsuarioDao usuarioDao = new UsuarioDao(conexion);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no existe.");
        }

        ElementoTecnologico tecnologico = new ElementoTecnologico();
        tecnologico.setNombre(nombre);
        tecnologico.setEstado(estado);
        tecnologico.setIdentificadorUnico(identificador);
        tecnologico.setTipoIdentificador(tipoIdentificador);
        tecnologico.setAulaId(idAula);
        tecnologico.setUsuarioRegistra(idUsuario);
        tecnologico.setMarca(marca);
        tecnologico.setSerie(serie);

        ElementoDao elementoDao = new ElementoDao(conexion);
        int idElemento = elementoDao.insertarElemento(tecnologico);
        if (idElemento <= 0) {
            throw new Exception("No se pudo insertar el elemento base.");
        }

        tecnologico.setIdElemento(idElemento);
        ElementoTecnologicoDao tecnologicoDao = new ElementoTecnologicoDao(conexion);
        tecnologicoDao.insertar(tecnologico);
    }

    private void registrarMobiliario(HttpServletRequest request, Connection conexion) throws Exception {
        String nombre = request.getParameter("nombre");
        String estado = request.getParameter("estado");
        String identificador = request.getParameter("identificadorUnico");
        String tipoIdentificador = request.getParameter("tipoIdentificador");
        int idAula = Integer.parseInt(request.getParameter("idAula"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        UsuarioDao usuarioDao = new UsuarioDao(conexion);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no existe.");
        }

        ElementosMobiliarios mobiliario = new ElementosMobiliarios();
        mobiliario.setNombre(nombre);
        mobiliario.setEstado(estado);
        mobiliario.setIdentificadorUnico(identificador);
        mobiliario.setTipoIdentificador(tipoIdentificador);
        mobiliario.setAulaId(idAula);
        mobiliario.setUsuarioRegistra(idUsuario);

        ElementoDao elementoDao = new ElementoDao(conexion);
        int idElemento = elementoDao.insertarElemento(mobiliario);
        if (idElemento <= 0) {
            throw new Exception("No se pudo insertar el elemento base.");
        }

        mobiliario.setIdElemento(idElemento);
        ElementoMobiliarioDao mobiliarioDao = new ElementoMobiliarioDao(conexion);
        mobiliarioDao.insertarMobiliario(idElemento);
    }

    private void listarTodosLosElementos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", " No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            ElementoTecnologicoDao tecnologicoDao = new ElementoTecnologicoDao(conexion);
            ElementoMobiliarioDao mobiliarioDao = new ElementoMobiliarioDao(conexion);

            List<Elemento> elementos = new ArrayList<>();
            elementos.addAll(tecnologicoDao.listarElementos());
            elementos.addAll(mobiliarioDao.listarElementos());

            request.setAttribute("elementos", elementos);
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Error al listar todos los elementos: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

}
