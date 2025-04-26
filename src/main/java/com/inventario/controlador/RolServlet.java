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
                request.getRequestDispatcher("Vistas/Rol/menuRol.jsp").forward(request, response);
                return;
            }

            RolDao rolDao = new RolDao(conexion);

            switch (accion) {
                case "crear":
                    crearRol(request, rolDao);
                    break;

                case "actualizar":
                    actualizarRol(request, rolDao);
                    break;

                case "eliminar":
                    eliminarRol(request, rolDao);
                    break;

                default:
                    request.setAttribute("mensajeError", "Acción no reconocida.");
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error interno: " + e.getMessage());
        }

        request.getRequestDispatcher("Vistas/Rol/menuRol.jsp").forward(request, response);
    }

    private void crearRol(HttpServletRequest request, RolDao rolDao) throws Exception {
        String nombreRol = request.getParameter("nombreRol");

        Rol rolExistente = rolDao.obtenerRolPorNombre(nombreRol);

        if (rolExistente != null) {
            request.setAttribute("mensajeError", "El rol '" + nombreRol + "' ya existe. No se puede crear duplicado.");
        } else {
            Rol rol = new Rol(nombreRol);
            rolDao.insertarRol(rol);
            request.setAttribute("mensajeExito", "Rol creado correctamente.");
        }
    }

    private void actualizarRol(HttpServletRequest request, RolDao rolDao) throws Exception {
        int idRol = Integer.parseInt(request.getParameter("idRol"));
        String nuevoNombreRol = request.getParameter("nuevoNombreRol");

        Rol rolExistente = rolDao.obtenerPorId(idRol);

        if (rolExistente == null) {
            request.setAttribute("mensajeError", "No se encontró el rol con ID: " + idRol);
        } else {
            rolExistente.setNombreRol(nuevoNombreRol);
            rolDao.actualizarRol(rolExistente);
            request.setAttribute("mensajeExito", "Rol actualizado correctamente.");
        }
    }

    private void eliminarRol(HttpServletRequest request, RolDao rolDao) throws Exception {
        String nombreRol = request.getParameter("nombreRol");

        Rol rolExistente = rolDao.obtenerRolPorNombre(nombreRol);

        if (rolExistente == null) {
            request.setAttribute("mensajeError", "No se puede eliminar: el rol '" + nombreRol + "' no existe.");
        } else {
            rolDao.eliminarRol(rolExistente.getIdRol());
            request.setAttribute("mensajeExito", "Rol eliminado correctamente.");
        }
    }
}
