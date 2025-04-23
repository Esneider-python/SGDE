package com.inventario.controlador;

import com.inventario.modelo.Rol;
import com.mycompany.sgde.dao.RolDao;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "RolServlet", urlPatterns = {"/RolServlet"})
public class RolServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("mensajeError", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("menuRol.jsp").forward(request, response);
                return;
            }

            RolDao rolDao = new RolDao(conexion);

            switch (accion) {
                case "crear":
                    crearRol(request, rolDao);
                    request.setAttribute("mensajeExito", "Rol creado correctamente.");
                    break;

                case "actualizar":
                    actualizarRol(request, rolDao);
                    request.setAttribute("mensajeExito", "Rol actualizado correctamente.");
                    break;

                case "eliminar":
                    eliminarRol(request, rolDao);
                    request.setAttribute("mensajeExito", "Rol eliminado correctamente.");
                    break;

                default:
                    request.setAttribute("mensajeError", "Acción no reconocida.");
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error interno: " + e.getMessage());
        }

        request.getRequestDispatcher("menuRol.jsp").forward(request, response);
    }

    private void crearRol(HttpServletRequest request, RolDao rolDao) throws Exception {
        String nombreRol = request.getParameter("nombreRol");
        Rol rol = new Rol(nombreRol);
        rolDao.insertarRol(rol);
    }

    private void actualizarRol(HttpServletRequest request, RolDao rolDao) throws Exception {
        int idRol = Integer.parseInt(request.getParameter("idRol"));
        String nombreRol = request.getParameter("nombreRol");
        Rol rol = new Rol(idRol, nombreRol);
        rolDao.actualizarRol(rol);
    }

    private void eliminarRol(HttpServletRequest request, RolDao rolDao) throws Exception {
        int idRol = Integer.parseInt(request.getParameter("idRol"));
        rolDao.eliminarRol(idRol);
    }
}
