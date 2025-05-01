package com.inventario.controlador;

import com.inventario.modelo.*;
import com.mycompany.sgde.dao.*;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
                    case "actualizarGuardar":
                        procesarActualizarElemento(request, response, conexion);
                        conexion.commit();
                        return;

                    case "mostrarActualizar":
                        mostrarFormularioActualizar(request, response, conexion);
                        return;

                    default:
                        throw new IllegalArgumentException("Acción no válida.");
                }

                conexion.commit();

            } catch (Exception e) {
                conexion.rollback();
                e.printStackTrace();
                request.setAttribute("error", " Error al registrar el elemento: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Error interno: " + e.getMessage());
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

    private void mostrarFormularioActualizar(HttpServletRequest request, HttpServletResponse response, Connection conexion)
            throws ServletException, IOException {
        int idElemento = Integer.parseInt(request.getParameter("idElemento"));
        String tipo = request.getParameter("tipoElemento");

        try {
            Object elemento;
            if ("tecnologico".equals(tipo)) {
                ElementoTecnologicoDao dao = new ElementoTecnologicoDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            } else {
                ElementoMobiliarioDao dao = new ElementoMobiliarioDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            }

            request.setAttribute("elemento", elemento);
            request.setAttribute("tipo", tipo);
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/actualizarElemento.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al cargar formulario de actualización.");
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void procesarActualizarElemento(HttpServletRequest request, HttpServletResponse response, Connection conexion) throws ServletException, IOException {
        try {
            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            ElementoDao elementoDao = new ElementoDao(conexion);

            int idElemento = Integer.parseInt(request.getParameter("idElemento"));
            String nombre = request.getParameter("nombre");
            String cedula = request.getParameter("cedulaUsuario");
            Integer usuarioRegistra = usuarioDao.obtenerIdPorCedula(cedula);
            int aulaId = Integer.parseInt(request.getParameter("aulaId"));
            String tipoElemento = request.getParameter("tipoElemento");

            if (usuarioRegistra == null) {
                throw new IllegalArgumentException("❌ La cédula ingresada no está registrada.");
            }

            boolean exito = false;

            if ("tecnologico".equals(tipoElemento)) {
                ElementoTecnologicoDao tecnologicoDao = new ElementoTecnologicoDao(conexion);
                ElementoTecnologico tecnologico = tecnologicoDao.obtenerPorId(idElemento);  // ← obtiene datos existentes

                if (tecnologico == null) {
                    throw new IllegalArgumentException("❌ No se encontró el elemento tecnológico con ID: " + idElemento);
                }

                // Solo modificamos lo necesario
                tecnologico.setNombre(nombre);
                tecnologico.setUsuarioRegistra(usuarioRegistra);
                tecnologico.setAulaId(aulaId);
                tecnologico.setMarca(request.getParameter("marca"));
                tecnologico.setSerie(request.getParameter("serie"));

                exito = elementoDao.actualizarElemento(tecnologico);

            } else if ("mobiliario".equals(tipoElemento)) {
                ElementoMobiliarioDao mobiliarioDao = new ElementoMobiliarioDao(conexion);
                ElementosMobiliarios mobiliario = mobiliarioDao.obtenerPorId(idElemento);  // ← obtiene datos existentes

                if (mobiliario == null) {
                    throw new IllegalArgumentException("❌ No se encontró el elemento mobiliario con ID: " + idElemento);
                }

                // Solo modificamos lo necesario
                mobiliario.setNombre(nombre);
                mobiliario.setUsuarioRegistra(usuarioRegistra);
                mobiliario.setAulaId(aulaId);

                exito = elementoDao.actualizarElemento(mobiliario);
            }

            if (exito) {
                request.setAttribute("mensaje", "✅ Elemento actualizado exitosamente.");
            } else {
                request.setAttribute("mensaje", "❌ No se pudo actualizar el elemento.");
            }

            listarTodosLosElementos(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "❌ Error al actualizar: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("listarTodos".equals(accion)) {
            listarTodosLosElementos(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método GET no permitido para esta acción.");
        }
    }

}
