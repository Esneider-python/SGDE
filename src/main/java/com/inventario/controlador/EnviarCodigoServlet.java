package com.inventario.controlador;

import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;
import com.mycompany.sgde.util.CorreoUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Random;

@WebServlet("/EnviarCodigoServlet")
public class EnviarCodigoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");

        try (Connection conexion = Conexion.getConexion()) {
            UsuarioDao usuarioDao = new UsuarioDao(conexion);

            if (!usuarioDao.existeCorreo(correo)) {
                request.setAttribute("mensajeError", "No existe una cuenta con ese correo.");
                request.getRequestDispatcher("Vistas/Login/solicitarCodigo.jsp").forward(request, response);
                return;
            }

            // Generar código de 6 dígitos
            String codigo = String.format("%06d", new Random().nextInt(1000000));

            // Guardar en sesión
            HttpSession sesion = request.getSession();
            sesion.setAttribute("codigoRecuperacion", codigo);
            sesion.setAttribute("correoRecuperacion", correo);

            // Enviar correo
            CorreoUtil.enviarCodigo(correo, codigo);

            response.sendRedirect("Vistas/Login/verificarCodigo.jsp");

        } catch (Exception e) {
             e.printStackTrace();
            request.setAttribute("mensajeError", "Error al procesar la solicitud.");
            request.getRequestDispatcher("Vistas/Login/solicitarCodigo.jsp").forward(request, response);
            // Agrega esta línea

        }
    }
}
