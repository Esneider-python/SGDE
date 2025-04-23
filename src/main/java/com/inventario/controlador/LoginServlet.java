package com.inventario.controlador;

import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {
            Connection conn = Conexion.getConexion(); 
            UsuarioDao usuarioDao = new UsuarioDao(conn);
            Usuario usuario = usuarioDao.validarCredenciales(correo, contrasena);

            if (usuario != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuarioLogueado", usuario);
                response.sendRedirect("Vistas/MenuPrincipal/menuPrincipal.jsp");
            } else {
                // Si las credenciales son incorrectas, mostramos un mensaje
                request.setAttribute("errorLogin", "Correo o contraseña incorrectos.");
                request.setAttribute("correoIngresado", correo); // Para mantener el correo en el input
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (ServletException | IOException | SQLException e) {
            throw new ServletException("Error al intentar iniciar sesión", e);
        }
    }
}
