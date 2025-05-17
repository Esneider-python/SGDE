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
        if (!usuarioTienePermiso(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a esta acción");
            return;
        }

        String action = request.getParameter("action");
        Connection con = null;
        try {
            con = Conexion.getConexion();
            if (con == null) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            con.setAutoCommit(false);

            switch (action) {
                case "registrar":
                    registrarUsuario(request, response, con);
                    break;
                case "actualizarUsuario":
                    actualizarUsuario(request, response, con);
                    break;
                case "eliminarUsuario":
                    eliminarUsuario(request, response, con);
                    break;
                default:
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

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        try {
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

            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);

        } catch (SQLIntegrityConstraintViolationException e) {
            String errorMsg = "La cédula o el correo ya están en uso.";
            if (e.getMessage().contains("cedula_UNIQUE")) {
                errorMsg = "La cédula ya está en uso.";
            } else if (e.getMessage().contains("correo_UNIQUE")) {
                errorMsg = "El correo ya está en uso.";
            }
            e.printStackTrace();
            request.setAttribute("mensaje", errorMsg);
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error en la base de datos: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        try {
            // Obtener parámetros del formulario
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String cedula = request.getParameter("cedula");

            // Validación de campos obligatorios
            if (nombres == null || nombres.trim().isEmpty()
                    || apellidos == null || apellidos.trim().isEmpty()
                    || telefono == null || telefono.trim().isEmpty()
                    || correo == null || correo.trim().isEmpty()
                    || cedula == null || cedula.trim().isEmpty()) {

                request.setAttribute("mensaje", "Por favor, complete todos los campos obligatorios.");
                request.getRequestDispatcher("/Vistas/Usuario/actualizarUsuario.jsp?id=" + idUsuario).forward(request, response);
                return;
            }

            UsuarioDao usuarioDao = new UsuarioDao(con);
            Usuario usuarioActual = usuarioDao.obtenerUsuarioPorId(idUsuario);

            // Verificar que el usuario exista
            if (usuarioActual == null) {
                request.setAttribute("mensaje", "El usuario no existe.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            // Actualización de datos
            usuarioActual.setIdUsuario(idUsuario);
            usuarioActual.setNombres(nombres);
            usuarioActual.setApellidos(apellidos);
            usuarioActual.setTelefono(telefono);
            usuarioActual.setCorreo(correo);
            usuarioActual.setCedula(cedula);

            try {
                // Intentar actualizar el usuario
                boolean actualizado = usuarioDao.actualizarUsuario(usuarioActual);

                if (actualizado) {
                    con.commit();
                    request.setAttribute("mensaje", "Usuario actualizado correctamente.");
                } else {
                    con.rollback();
                    request.setAttribute("mensaje", "No se pudo actualizar el usuario. Intente nuevamente.");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                // Manejo de errores de integridad referencial
                con.rollback();
                String errorMsg = "Error de integridad de datos.";
                if (e.getMessage().contains("correo_UNIQUE")) {
                    errorMsg = "El correo ya está en uso.";
                } else if (e.getMessage().contains("cedula_UNIQUE")) {
                    errorMsg = "La cédula ya está en uso.";
                }
                request.setAttribute("mensaje", errorMsg);
                request.getRequestDispatcher("/Vistas/Usuario/actualizarUsuario.jsp?id=" + idUsuario).forward(request, response);
                return;
            }

            // Redirigir al menú después de actualizar
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);

        } catch (SQLException e) {
            // Manejo de errores de base de datos
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Error en la base de datos: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Manejo de errores de formato de ID
            request.setAttribute("mensaje", "El ID del usuario no es válido.");
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }

    }
//Metodo Eliminar usuario

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        try {
            String idUsuarioParam = request.getParameter("idUsuario");

            // Validar que no sea nulo o vacío antes de convertir
            if (idUsuarioParam == null || idUsuarioParam.trim().isEmpty()) {
                request.setAttribute("mensaje", "El ID del usuario no es válido.");
                request.setAttribute("tipoMensaje", "error");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            int idUsuario = Integer.parseInt(idUsuarioParam);

            UsuarioDao usuarioDao = new UsuarioDao(con);
            boolean eliminado = usuarioDao.eliminarUsuario(idUsuario);

            if (eliminado) {
                con.commit();
                request.setAttribute("mensaje", "Usuario eliminado correctamente.");
                request.setAttribute("tipoMensaje", "exito");

            } else {
                con.rollback();
                request.setAttribute("mensaje", "No se pudo eliminar el usuario. Intente nuevamente.");
                request.setAttribute("tipoMensaje", "error");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Mensaje genérico para cualquier error de clave foránea
            String errorMsg = "El usuario no se puede eliminar porque tiene registros asignados en otras tablas. Elimine o reasigne estos registros primero.";
            request.setAttribute("mensaje", errorMsg);
            request.setAttribute("tipoMensaje", "error");

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Error en la base de datos: " + e.getMessage());
            request.setAttribute("tipoMensaje", "error");
        }

        // Redirige al menú de usuarios después de eliminar
        request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!usuarioTienePermiso(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a esta acción");
            return;
        }
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
                case "listarUsuarios":
                    listarUsuarios(request, response, con);
                    break;
                case "mostrarFormularioActualizar":
                    mostrarFormularioActualizar(request, response, con);
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

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        try {
            UsuarioDao usuarioDao = new UsuarioDao(con);
            List<Usuario> usuarios = usuarioDao.obtenerTodosLosUsuarios();

            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/Vistas/Usuario/VerUsuarios.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al recuperar los usuarios.");
            request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
        }
    }
// MOSTRAR FORMULARIO ACTUALIZAR USUARIO

    private void mostrarFormularioActualizar(HttpServletRequest request, HttpServletResponse response, Connection con) throws ServletException, IOException {
        String idStr = request.getParameter("id");
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

            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/Vistas/Usuario/menuUsuario.jsp?error=Usuario no encontrado.");
                return;
            }

            // Pasar el objeto usuario a la JSP
            request.setAttribute("usuario", usuario);

            // Forward al JSP del formulario actualizarUsuario.jsp
            request.getRequestDispatcher("/Vistas/Usuario/actualizarUsuario.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Vistas/Usuario/menuUsuario.jsp?error=ID de usuario inválido.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al acceder a los datos del usuario.");
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

// Actualizar usuario
// Eliminar usuario
// Asignar rol
// Asignar aula
// Quitar asignacion
// ver asignaciones
