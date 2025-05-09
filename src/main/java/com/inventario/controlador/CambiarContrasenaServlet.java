package com.inventario.controlador;

import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "CambiarContrasenaServlet", urlPatterns = {"/CambiarContrasenaServlet"})
public class CambiarContrasenaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nuevaContrasena = request.getParameter("nuevaContrasena");
        String confirmarContrasena = request.getParameter("confirmarContrasena");

        HttpSession session = request.getSession();
        String correoRecuperacion = (String) session.getAttribute("correoRecuperacion");

        if (correoRecuperacion == null) {
            request.setAttribute("mensajeError", "Sesión expirada. Intenta recuperar la contraseña nuevamente.");
            request.getRequestDispatcher("Vistas/Login/solicitarCorreo.jsp").forward(request, response);
            return;
        }

        if (nuevaContrasena == null || !nuevaContrasena.equals(confirmarContrasena)) {
            request.setAttribute("mensajeError", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("Vistas/Login/cambiarContrasena.jsp").forward(request, response);
            return;
        }

        try (Connection conexion = Conexion.getConexion()) {
            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            usuarioDao.actualizarContrasenaPorCorreo(correoRecuperacion, nuevaContrasena);

            // Limpiar atributos de sesión
            session.removeAttribute("correoRecuperacion");
            session.removeAttribute("codigoRecuperacion");

            // Mostrar mensaje de éxito y redirigir después desde el JSP
            request.setAttribute("mensajeExito", "Contraseña actualizada correctamente. Serás redirigido al inicio de sesión...");
            request.getRequestDispatcher("Vistas/Login/cambiarContrasena.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al actualizar la contraseña: " + e.getMessage());
            request.getRequestDispatcher("Vistas/Login/cambiarContrasena.jsp").forward(request, response);
        }
    }
}
