package paquete.controlador;

import paquete.dao.UsuarioDao;
import paquete.util.Conexion;
import paquete.util.CorreoUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Random;

@WebServlet("/RecuperarContrasenaServlet")
public class RecuperarContrasenaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            switch (action) {
                case "enviarCodigo":
                    enviarCodigo(request, response);
                    break;
                case "verificarCodigo":
                    verificarCodigo(request, response);
                    break;
                case "cambiarContrasena":
                    cambiarContrasena(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/Index.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    //  Enviar código
    private void enviarCodigo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        HttpSession session = request.getSession();

        try (Connection conexion = Conexion.getConexion()) {
            UsuarioDao usuarioDao = new UsuarioDao(conexion);

            if (correo == null || correo.trim().isEmpty()) {
                request.getSession().setAttribute("mensajeError", "Debes ingresar un correo válido");
                response.sendRedirect(request.getContextPath() + "/Vistas/Login/solicitarCorreo.jsp");
                return;
            }

            if (!usuarioDao.existeCorreo(correo)) {
                request.getSession().setAttribute("mensajeError", "No existe una cuenta con ese correo.");
                response.sendRedirect(request.getContextPath() + "/Vistas/Login/solicitarCorreo.jsp");
                return;
            }

            // Generar código de 6 dígitos
            String codigo = String.format("%06d", new Random().nextInt(1000000));

            // Guardar en sesión
            session.setAttribute("codigoRecuperacion", codigo);
            session.setAttribute("correoRecuperacion", correo);

            // Enviar correo
            CorreoUtil.enviarCodigo(correo, codigo);

            response.sendRedirect("Vistas/Login/verificarCodigo.jsp");

        } catch (Exception e) {
            e.printStackTrace();

            // programando mensaje 
            request.getSession().setAttribute("mensajeError", "Error al procesar la solicitud");
            response.sendRedirect(request.getContextPath() + "/Vistas/Login/solicitarCorreo.jsp");
        }
    }

    //  Verificar código
    private void verificarCodigo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String codigoIngresado = request.getParameter("codigo");

        if (session == null || session.getAttribute("codigoRecuperacion") == null) {
            request.setAttribute("mensajeError", "Sesión expirada. Solicita un nuevo código.");
            request.getRequestDispatcher("Vistas/Login/solicitarCorreo.jsp").forward(request, response);
            return;
        }

        String codigoCorrecto = (String) session.getAttribute("codigoRecuperacion");

        if (codigoCorrecto.equals(codigoIngresado)) {
            response.sendRedirect("Vistas/Login/cambiarContrasena.jsp");
        } else {
            request.setAttribute("mensajeError", "Código incorrecto. Inténtalo nuevamente.");
            request.getRequestDispatcher("Vistas/Login/verificarCodigo.jsp").forward(request, response);
        }
    }

    // 🔹 Método 3: Cambiar contraseña
    private void cambiarContrasena(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nuevaContrasena = request.getParameter("nuevaContrasena");
        String confirmarContrasena = request.getParameter("confirmarContrasena");
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

            request.setAttribute("mensajeExito", "Contraseña actualizada correctamente. Serás redirigido al inicio de sesión...");
            request.getRequestDispatcher("Vistas/Login/cambiarContrasena.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al actualizar la contraseña: " + e.getMessage());
            request.getRequestDispatcher("Vistas/Login/cambiarContrasena.jsp").forward(request, response);
        }
    }
}
