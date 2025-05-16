package com.inventario.controlador;

import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Validar sesión y rol antes de continuar
        if (!usuarioTienePermiso(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a esta acción");
            return;
        }
        String action = request.getParameter("action");
        switch (action) {
            case "registrar":
                registrarUsuario(request, response);
                break;
            default:
                request.setAttribute("mensaje", "Acción no válida. Por favor, intente nuevamente.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        try {
            // Validación de campos
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String cedula = request.getParameter("cedula");
            String contrasena = request.getParameter("contrasena");
            String rolIdStr = request.getParameter("rol_id");

            if (nombres == null || nombres.trim().isEmpty()
                    || apellidos == null || apellidos.trim().isEmpty()
                    || telefono == null || telefono.trim().isEmpty()
                    || correo == null || correo.trim().isEmpty()
                    || cedula == null || cedula.trim().isEmpty()
                    || contrasena == null || contrasena.trim().isEmpty()
                    || rolIdStr == null || rolIdStr.trim().isEmpty()) {

                request.setAttribute("mensaje", "Por favor, complete todos los campos antes de enviar.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            int rolId;
            try {
                rolId = Integer.parseInt(rolIdStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "El rol ingresado no es válido. Debe ser un número.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            con = Conexion.getConexion();
            if (con == null) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            con.setAutoCommit(false);

            Usuario usuario = new Usuario();
            usuario.setNombres(nombres);
            usuario.setApellidos(apellidos);
            usuario.setTelefono(telefono);
            usuario.setCorreo(correo);
            usuario.setCedula(cedula);
            usuario.setContrasena(contrasena);
            usuario.setRolId(rolId);

            UsuarioDao usuarioDao = new UsuarioDao(con);
            boolean registrado = usuarioDao.insertarUsuario(usuario);

            if (registrado) {
                con.commit();
                request.setAttribute("mensaje", "Usuario registrado correctamente.");
            } else {
                con.rollback();
                request.setAttribute("mensaje", "No se pudo registrar el usuario. Intente nuevamente.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Manejo específico para claves duplicadas (cedula, correo)
            String errorMsg = "La cédula o el correo ya están en uso.";
            if (e.getMessage().contains("cedula_UNIQUE")) {
                errorMsg = "La cédula ya está en uso.";
            } else if (e.getMessage().contains("correo_UNIQUE")) {
                errorMsg = "El correo ya está en uso.";
            }
            e.printStackTrace();
            request.setAttribute("mensaje", errorMsg);
        } catch (SQLException e) {
            // Detalle del error SQL para diagnósticos precisos
            String sqlError = "Error en la base de datos: " + e.getMessage();
            e.printStackTrace();
            request.setAttribute("mensaje", sqlError);
        } catch (Exception e) {
            // Error inesperado
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error inesperado. Intente nuevamente.");
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "Error al cerrar la conexión con la base de datos.");
            }
            // Enviar mensaje final a la vista
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }
    }
    //METODO GET...........
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Validar sesión y rol antes de continuar
        if (!usuarioTienePermiso(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a esta acción");
            return;
        }
        String action = request.getParameter("action");
        switch (action) {
            case "listarUsuarios":
                listarUsuarios(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return; 
            }

            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            List<Usuario> usuarios = usuarioDao.obtenerTodosLosUsuarios();

            // Enviar la lista de usuarios al JSP
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/Vistas/Usuario/VerUsuarios.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al recuperar los usuarios.");
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }
    }

    // METODO PARA VALIDAR PERMISOS PARA EL USUARIO
    private boolean usuarioTienePermiso(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        String rol = (String) session.getAttribute("rol");
        if (rol == null) {
            return false;
        }

        return rol.equalsIgnoreCase("administrador")
                || rol.equalsIgnoreCase("director")
                || rol.equalsIgnoreCase("rector");
    }

}
