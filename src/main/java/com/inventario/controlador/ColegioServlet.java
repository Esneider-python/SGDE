package com.inventario.controlador;

import com.inventario.modelo.Colegio;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.ColegioDao;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ColegioServlet")
public class ColegioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null || accion.trim().isEmpty()) {
            enviarMensaje(request, response, "La acción no puede estar vacía.", "Vistas/Colegio/menuColegio.jsp");
            return;
        }

        try (Connection conn = Conexion.getConexion()) {
            ColegioDao colegioDao = new ColegioDao(conn);
            UsuarioDao usuarioDao = new UsuarioDao(conn);

            switch (accion) {
                case "registrar":
                    registrarColegio(request, response, colegioDao, usuarioDao);
                    break;
                case "actualizar":
                    actualizarColegio(request, response, colegioDao, usuarioDao);
                    break;
                case "eliminar":
                    eliminarColegio(request, response, colegioDao, usuarioDao);
                    break;
                default:
                    enviarMensaje(request, response, "Acción no reconocida.", "Vistas/Colegio/menuColegio.jsp");
            }

        } catch (Exception e) {
            enviarMensaje(request, response, "Error en la operación: " + e.getMessage(), "Vistas/Colegio/menuColegio.jsp");
        }
    }

    private void registrarColegio(HttpServletRequest request, HttpServletResponse response, ColegioDao colegioDao, UsuarioDao usuarioDao) throws ServletException, IOException {
        String nombreColegio = request.getParameter("nombre_colegio");
        String cedulaParam = request.getParameter("cedula_usuario");

        if (nombreColegio == null || nombreColegio.trim().isEmpty()) {
            enviarMensaje(request, response, "El nombre del colegio no puede estar vacío.", "Vistas/Colegio/registrarColegio.jsp");
            return;
        }
        if (cedulaParam == null || cedulaParam.trim().isEmpty()) {
            enviarMensaje(request, response, "La cédula del usuario no puede estar vacía.", "Vistas/Colegio/registrarColegio.jsp");
            return;
        }

        Integer idUsuarioRegistra = usuarioDao.obtenerIdPorCedula(cedulaParam);
        if (idUsuarioRegistra == null) {
            enviarMensaje(request, response, "El usuario no existe.", "Vistas/Colegio/registrarColegio.jsp");
            return;
        }

        boolean existeColegio = colegioDao.existeColegio(nombreColegio);
        if (existeColegio) {
            enviarMensaje(request, response, "El colegio ya ha sido creado.", "Vistas/Colegio/registrarColegio.jsp");
        } else {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuarioRegistra);
            Colegio colegio = new Colegio(nombreColegio, usuario);
            colegioDao.insertar(colegio);
            enviarMensaje(request, response, "Colegio registrado exitosamente.", "Vistas/Colegio/registrarColegio.jsp");
        }
    }

    private void actualizarColegio(HttpServletRequest request, HttpServletResponse response, ColegioDao colegioDao, UsuarioDao usuarioDao) throws ServletException, IOException {
        String idColegioParam = request.getParameter("id_colegio");
        String nombreColegio = request.getParameter("nombre_colegio");
        String cedulaParam = request.getParameter("cedula_usuario");

        if (idColegioParam == null || idColegioParam.trim().isEmpty()) {
            enviarMensaje(request, response, "El ID del colegio no puede estar vacío.", "Vistas/Colegio/actualizarColegio.jsp");
            return;
        }
        if (cedulaParam == null || cedulaParam.trim().isEmpty()) {
            enviarMensaje(request, response, "La cédula del usuario no puede estar vacía.", "Vistas/Colegio/actualizarColegio.jsp");
            return;
        }

        Integer idColegio = Integer.valueOf(idColegioParam);
        Colegio colegioExistente = colegioDao.obtenerPorId(idColegio);
        if (colegioExistente == null) {
            enviarMensaje(request, response, "El colegio con ese ID no existe.", "Vistas/Colegio/actualizarColegio.jsp");
            return;
        }

        Integer idUsuarioRegistra = usuarioDao.obtenerIdPorCedula(cedulaParam);
        if (idUsuarioRegistra == null) {
            enviarMensaje(request, response, "El usuario no existe.", "Vistas/Colegio/actualizarColegio.jsp");
            return;
        }

        colegioExistente.setNombre(nombreColegio);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuarioRegistra);
        colegioExistente.setUsuarioRegistra(usuario);

        colegioDao.actualizar(colegioExistente);
        enviarMensaje(request, response, "Colegio actualizado exitosamente.", "Vistas/Colegio/actualizarColegio.jsp");
    }

    private void eliminarColegio(HttpServletRequest request, HttpServletResponse response, ColegioDao colegioDao, UsuarioDao usuarioDao) throws ServletException, IOException {
        String idColegioParam = request.getParameter("id_colegio");
        String cedulaParam = request.getParameter("usuario_elimina");

        if (idColegioParam == null || idColegioParam.trim().isEmpty()) {
            enviarMensaje(request, response, "Debe proporcionar el ID del colegio a eliminar.", "Vistas/Colegio/registrarColegio.jsp");
            return;
        }
        if (cedulaParam == null || cedulaParam.trim().isEmpty()) {
            enviarMensaje(request, response, "Debe proporcionar la cédula del usuario que elimina.", "Vistas/Colegio/registrarColegio.jsp");
            return;
        }

        int idColegio = Integer.parseInt(idColegioParam);
        Colegio colegioExistente = colegioDao.obtenerPorId(idColegio);

        if (colegioExistente == null) {
            enviarMensaje(request, response, "El colegio con el ID proporcionado no existe.", "Vistas/Colegio/eliminarColegio.jsp");
            return;
        }

        Integer idUsuarioElimina = usuarioDao.obtenerIdPorCedula(cedulaParam);
        if (idUsuarioElimina == null) {
            enviarMensaje(request, response, "El usuario que intenta eliminar no existe.", "Vistas/Colegio/menuColegio.jsp");
            return;
        }

        int filasEliminadas = colegioDao.eliminar(idColegio);
        colegioDao.eliminar(idColegio);
        response.sendRedirect("Vistas/Colegio/menuColegios.jsp?mensaje=eliminado");

        if (filasEliminadas > 0) {
            request.setAttribute("mensaje", "Colegio eliminado exitosamente.");
        } else {
            request.setAttribute("mensaje", "No se pudo eliminar el colegio.");
        }

    }

    private void enviarMensaje(HttpServletRequest request, HttpServletResponse response, String mensaje, String vista) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher(vista).forward(request, response);
    }
}
