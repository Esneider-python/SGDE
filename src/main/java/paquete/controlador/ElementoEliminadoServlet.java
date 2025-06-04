package paquete.controlador;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import paquete.dao.ElementoEliminadoDao;
import paquete.modelo.ElementoEliminado;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import paquete.util.Conexion;

@WebServlet("/ElementoEliminadoServlet")
public class ElementoEliminadoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("action");
        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            conexion.setAutoCommit(false);

            if ("verEliminados".equals(accion)) {
                ElementoEliminadoDao dao = new ElementoEliminadoDao(conexion);
                List<ElementoEliminado> lista = dao.obtenerTodos();
                request.setAttribute("elementosEliminados", lista);
                request.getRequestDispatcher("/Vistas/ElementosEliminados/ElementosEliminados.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ElementoEliminadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
