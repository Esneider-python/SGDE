package com.inventario.controlador;

import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarUsuarioServlet", urlPatterns = {"/RegistrarUsuarioServlet"})
public class RegistrarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String cedula = request.getParameter("cedula");
        String contrasena = request.getParameter("contrasena");
        int rolId = 2; // valor por defecto

        try {
            rolId = Integer.parseInt(request.getParameter("rol_id"));
        } catch (NumberFormatException e) {
            System.out.println("Rol inválido");
            request.setAttribute("errorRegistro", "Rol inválido");
            request.getRequestDispatcher("Vistas/Login/registro.jsp").forward(request, response);
            return;
        }

        Usuario usuario = new Usuario(idUsuarioRegistra);
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
        usuario.setCedula(cedula);
        usuario.setContrasena(contrasena);
        usuario.setRolId(rolId);

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                System.out.println("Conexión fallida.");
                request.setAttribute("errorRegistro", "Error de conexión.");
                request.getRequestDispatcher("Vistas/Login/registro.jsp").forward(request, response);
                return;
            }

            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            boolean existeCorreo = usuarioDao.existeCorreo(correo);
            boolean existeCedula = usuarioDao.existeCedula(cedula);

            if (existeCorreo || existeCedula) {
                String mensaje = "Ya existe un usuario con ";
                if (existeCorreo) {
                    mensaje += "ese correo. ";
                }
                if (existeCedula) {
                    mensaje += "esa cédula.";
                }
                System.out.println("Usuario ya existe: " + mensaje);
                request.setAttribute("errorRegistro", mensaje);
                request.getRequestDispatcher("Vistas/Login/registro.jsp").forward(request, response);
            } else {
                boolean registrado = usuarioDao.insertarUsuario(usuario);
                if (registrado) {
                    System.out.println("Usuario registrado con éxito.");
                    response.sendRedirect(request.getContextPath() + "/Vistas/Login/login.jsp");
                } else {
                    System.out.println("Error al registrar el usuario.");
                    request.setAttribute("errorRegistro", "No se pudo registrar el usuario.");
                    request.getRequestDispatcher("Vistas/Login/registro.jsp").forward(request, response);
                }
            }

        } catch (Exception e) {
            request.setAttribute("errorRegistro", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("Vistas/Login/registro.jsp").forward(request, response);
        }
    }
}
