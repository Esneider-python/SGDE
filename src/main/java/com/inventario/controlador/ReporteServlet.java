/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.inventario.controlador;

import com.inventario.modelo.Reporte;
import com.mycompany.sgde.dao.ReporteDao;
import com.mycompany.sgde.util.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/ReporteServlet"})
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el parámetro de acción
        String accion = request.getParameter("accion");
        
        // Validar si la acción es nula o vacía
        if (accion == null || accion.trim().isEmpty()) {
            request.setAttribute("error", "No se especificó ninguna acción.");
            request.getRequestDispatcher("/Vistas/Reportes/listarReportes.jsp").forward(request, response);
            return;
        }

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/MenuPrincipal/menuPrincipal.jsp").forward(request, response);
                return;
            }

            try {
                // Manejar la acción listarReporte solo con GET
                if ("listarReporte".equals(accion)) {
                    ReporteDao reporteDao = new ReporteDao();
                    List<Reporte> lista = reporteDao.obtenerTodos();
                    request.setAttribute("listaReportes", lista);
                    request.getRequestDispatcher("/Vistas/Reportes/listarReportes.jsp").forward(request, response);
                    return;
                } else {
                    throw new IllegalArgumentException("Acción no válida: " + accion);
                }

            } catch (Exception e) {
                // Rollback de la conexión en caso de error
                conexion.rollback();
                e.printStackTrace();
                request.setAttribute("error", "Error al procesar la acción '" + accion + "': " + e.getMessage());
                request.getRequestDispatcher("/Vistas/Reportes/listarReportes.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Reportes/listarReportes.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Rechazar solicitudes POST para listar reportes
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "El método POST no está permitido para listar reportes.");
    }
}
