package com.inventario.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/VerificarCodigoServlet")
public class VerificarCodigoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigoIngresado = request.getParameter("codigo");
        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("codigoRecuperacion") == null) {
            request.setAttribute("mensajeError", "Sesión expirada. Solicita un nuevo código.");
            request.getRequestDispatcher("solicitarCodigo.jsp").forward(request, response);
            return;
        }

        String codigoCorrecto = (String) sesion.getAttribute("codigoRecuperacion");

        if (codigoCorrecto.equals(codigoIngresado)) {
            // Código correcto, continuar al cambio de contraseña
            response.sendRedirect("Vistas/Login/cambiarContrasena.jsp");
        } else {
            // Código incorrecto
            request.setAttribute("mensajeError", "Código incorrecto. Inténtalo nuevamente.");
            request.getRequestDispatcher("verificarCodigo.jsp").forward(request, response);
        }
    }
}
