package com.inventario.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "CerrarSesionServlet", urlPatterns = {"/CerrarSesionServlet"})
public class CerrarSesionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession(false); 
        if (sesion != null) {
            sesion.invalidate();
        }
        response.sendRedirect("Vistas/Login/login.jsp"); 
    }
}
