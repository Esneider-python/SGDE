package com.inventario.controlador;

import com.inventario.modelo.HistorialMovimiento;
import com.mycompany.sgde.dao.HistorialMovimientoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/HistorialMovimientoServlet")
public class HistorialMovimientoServlet extends HttpServlet {

    private Connection conexion;

    @Override
    public void init() throws ServletException {
        // Obtenemos la conexión desde el contexto
        conexion = (Connection) getServletContext().getAttribute("conexion");
        if (conexion == null) {
            throw new ServletException(" No se pudo obtener la conexión desde el contexto.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HistorialMovimientoDao dao = new HistorialMovimientoDao(conexion);
        List<HistorialMovimiento> lista = dao.obtenerTodos();

        request.setAttribute("movimientos", lista);
        request.getRequestDispatcher("/Vistas/HistorialMovimientos/verHistorialMovimientos.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // No cerramos la conexión aquí porque la gestiona el listener
    }
}
