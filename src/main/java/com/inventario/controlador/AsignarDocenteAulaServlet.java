package com.inventario.controlador;

import com.inventario.modelo.DocenteAula;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.AulaDao;
import com.mycompany.sgde.dao.DocenteAulaDao;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "AsignarDocenteAulaServlet", urlPatterns = {"/AsignarDocenteAulaServlet"})
public class AsignarDocenteAulaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection con = null;
        try {
            con = Conexion.getConexion();
            if (con == null || con.isClosed()) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La acción es requerida.");
                return;
            }

            con.setAutoCommit(false);

            switch (action) {
                case "asignarDocenteAula":
                    asignarDocenteAula(request, response, con);
                    break;
                case "quitarAsignacion":
                    quitarAsignacion(request, response, con);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error inesperado. Intente nuevamente.");
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void asignarDocenteAula(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        try {
            // Obtener parámetros del formulario
            String idUsuarioParam = request.getParameter("idUsuario");
            String idAulaParam = request.getParameter("id_aula");
            String diaSemana = request.getParameter("dia_semana");
            String horaInicioParam = request.getParameter("hora_inicio");
            String horaFinParam = request.getParameter("hora_fin");

            // Verificación de campos vacíos
            if (idUsuarioParam == null || idUsuarioParam.trim().isEmpty()
                    || idAulaParam == null || idAulaParam.trim().isEmpty()
                    || diaSemana == null || diaSemana.trim().isEmpty()
                    || horaInicioParam == null || horaInicioParam.trim().isEmpty()
                    || horaFinParam == null || horaFinParam.trim().isEmpty()) {

                request.setAttribute("mensaje", "Por favor, complete todos los campos obligatorios.");
                request.setAttribute("tipoMensaje", "error");
                request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp?idUsuario=" + idUsuarioParam).forward(request, response);
                return;
            }

            int idUsuario = Integer.parseInt(idUsuarioParam);
            int idAula = Integer.parseInt(idAulaParam);
            LocalTime horaInicio = LocalTime.parse(horaInicioParam);
            LocalTime horaFin = LocalTime.parse(horaFinParam);

            // Verificar si el aula existe (usando el número de aula si es necesario)
            AulaDao aulaDAO = new AulaDao(con);
            int idAulas = aulaDAO.obtenerIdPorNumero(idAula); // <- esto parece incorrecto

            if (idAulas == -1) {
                request.setAttribute("mensaje", "El aula ingresada no existe.");
                request.setAttribute("tipoMensaje", "error");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp?idUsuario=" + idUsuarioParam).forward(request, response);
                return;
            }

            // Validación de rango de horas
            if (horaFin.isBefore(horaInicio)) {
                request.setAttribute("mensaje", "La hora de fin debe ser posterior a la hora de inicio.");
                request.setAttribute("tipoMensaje", "error");
                request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp?idUsuario=" + idUsuarioParam).forward(request, response);
                return;
            }

            // Crear objeto DocenteAula
            DocenteAula docenteAula = new DocenteAula();
            docenteAula.setIdUsuario(idUsuario);
            docenteAula.setIdAula(idAulas); 
            docenteAula.setDia(diaSemana);
            docenteAula.setHoraInicio(horaInicio);
            docenteAula.setHoraFin(horaFin);
            docenteAula.setEstado("activo");

            DocenteAulaDao docenteAulaDao = new DocenteAulaDao();
            boolean asignado = docenteAulaDao.asignarAulaADocente(docenteAula);

            if (asignado) {
                con.commit();
                request.setAttribute("mensaje", "Aula asignada correctamente.");
                request.setAttribute("tipoMensaje", "exito");

                // Redirigimos al listado con redirect + mensaje en sesión si quieres mostrarlo allá
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp?idUsuario=" + idUsuarioParam).forward(request, response);
            } else {
                con.rollback();
                request.setAttribute("mensaje", "No se pudo asignar el aula. Intente nuevamente.");
                request.setAttribute("tipoMensaje", "error");
                request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp?idUsuario=" + idUsuarioParam).forward(request, response);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Error de integridad de datos. Verifique que el usuario y aula existan.");
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp").forward(request, response);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Error en la base de datos: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "El ID del usuario o del aula no es válido.");
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp").forward(request, response);
        }
    }

    //QUITAR ASIGNACION 
    private void quitarAsignacion(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        String tipoMensaje = "";
        String mensaje = "";
        boolean redirigir = false;
        try {
            // Obtener parámetros del formulario
            String asignacionIdParam = request.getParameter("idAsignacion");

            System.out.println("id usuario " + asignacionIdParam);
            if (asignacionIdParam == null || asignacionIdParam.trim().isEmpty()) {
                tipoMensaje = "error";
                mensaje = "El ID de la asignación es requerido.";
                request.setAttribute("tipoMensaje", tipoMensaje);
                request.setAttribute("mensaje", mensaje);
                request.getRequestDispatcher("/Vistas/Asignar/VerAsignacion.jsp").forward(request, response);
                return;
            }

            int asignacionId = Integer.parseInt(asignacionIdParam);

            // Intentar eliminar la asignación
            DocenteAulaDao docenteAulaDao = new DocenteAulaDao();
            boolean actualizado = docenteAulaDao.actualizarEstadoAsignacion(asignacionId, "eliminado", con);

            if (actualizado) {
                con.commit();
                tipoMensaje = "exito";
                mensaje = "Asignación eliminada correctamente.";
            } else {
                con.rollback();
                tipoMensaje = "error";
                mensaje = "No se pudo eliminar la asignación. Intente nuevamente.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tipoMensaje = "error";
            mensaje = "Error de integridad de datos. Verifique que la asignación exista.";
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tipoMensaje = "error";
            mensaje = "Error en la base de datos: " + e.getMessage();
        } catch (NumberFormatException e) {
            tipoMensaje = "error";
            mensaje = "El ID de la asignación no es válido.";
        } finally {
            if (!redirigir) {
                // Redirige después de eliminar
                request.setAttribute("tipoMensaje", tipoMensaje);
                request.setAttribute("mensaje", mensaje);
                request.getRequestDispatcher("/Vistas/Asignar/VerAsignacion.jsp").forward(request, response);
            }
        }
    }

    //metodo get
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection con = null;
        try {
            con = Conexion.getConexion();
            if (con == null) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            switch (action) {
                case "formularioAsignar":
                    formularioAsignar(request, response, con);
                    break;
                case "verAsignaciones":
                    verAsignaciones(request, response, con);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error inesperado. Intente nuevamente.");
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void formularioAsignar(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        String idStr = request.getParameter("idUsuario");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Vistas/Usuario/menuUsuario.jsp?error=ID de usuario no válido.");
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idStr.trim());

            // Verificar que la conexión no sea nula y esté abierta
            if (con == null || con.isClosed()) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            UsuarioDao usuarioDao = new UsuarioDao(con);
            Usuario usuario = usuarioDao.obtenerUsuarioPorId(idUsuario);
            // Pasar el objeto usuario a la JSP
            request.setAttribute("usuario", usuario);

            // Forward al JSP del formulario actualizarUsuario.jsp
            request.getRequestDispatcher("/Vistas/Usuario/asignarAulaUsuario.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Vistas/Usuario/menuUsuario.jsp?error=ID de usuario inválido.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al acceder a los datos del usuario.");
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }
    }

    private void verAsignaciones(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        String idUsuarioParam = request.getParameter("idUsuario");

        if (idUsuarioParam == null || idUsuarioParam.trim().isEmpty()) {
            request.setAttribute("tipoMensaje", "error");
            request.setAttribute("mensaje", "El ID del usuario es requerido.");
            request.getRequestDispatcher("/Vistas/Asignar/VerAsignacion.jsp").forward(request, response);
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idUsuarioParam.trim());

            System.out.println("id usuario detectado:   " + idUsuarioParam);
            DocenteAulaDao docenteAulaDao = new DocenteAulaDao();
            List<DocenteAula> asignaciones = docenteAulaDao.obtenerAsignacionesPorUsuario(idUsuario);

            request.setAttribute("asignaciones", asignaciones);
            request.getRequestDispatcher("/Vistas/Asignar/VerAsignacion.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "El ID del usuario no es válido.");
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/Vistas/Asignar/VerAsignacion.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al acceder a las asignaciones: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/Vistas/Asignar/VerAsignacion.jsp").forward(request, response);
        }
    }

}
