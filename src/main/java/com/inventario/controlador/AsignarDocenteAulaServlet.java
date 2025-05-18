package com.inventario.controlador;

import com.inventario.modelo.DocenteAula;
import com.inventario.modelo.Usuario;
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

            con.setAutoCommit(false);

            if ("asignarDocenteAula".equals(action)) {
                asignarDocenteAula(request, response, con);
            } else {
                request.setAttribute("mensaje", "Acción no válida. Por favor, intente nuevamente.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
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
        boolean redirigir = false; // Evita múltiples reenvíos
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
            docenteAula.setIdAula(idAula);
            docenteAula.setDia(diaSemana);
            docenteAula.setHoraInicio(horaInicio);
            docenteAula.setHoraFin(horaFin);

            DocenteAulaDao docenteAulaDao = new DocenteAulaDao();
            boolean asignado = docenteAulaDao.asignarAulaADocente(docenteAula);

            if (asignado) {
                con.commit();
                request.setAttribute("mensaje", "Aula asignada correctamente.");
                request.setAttribute("tipoMensaje", "exito");
            } else {
                con.rollback();
                request.setAttribute("mensaje", "No se pudo asignar el aula. Intente nuevamente.");
                request.setAttribute("tipoMensaje", "error");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Error de integridad de datos. Verifique que el usuario y aula existan.");
            request.setAttribute("tipoMensaje", "error");
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Error en la base de datos: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "El ID del usuario o del aula no es válido.");
            request.setAttribute("tipoMensaje", "error");
        } finally {
            if (!redirigir) {
                // Redirige al menú después de asignar
                request.getRequestDispatcher("/Vistas/Usuario/actualizarUsuario.jsp").forward(request, response);
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

}