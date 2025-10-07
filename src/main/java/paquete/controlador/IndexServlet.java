package paquete.controlador;
import paquete.dao.RolDao;
import paquete.modelo.Rol;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import paquete.util.Conexion;

@WebServlet(name = "IndexServlet", urlPatterns = {"/IndexServlet"})
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection con = null;
        try {
            // Obtener conexión
            con = Conexion.getConexion();

            if (con == null) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Usuario/menuUsuario.jsp").forward(request, response);
                return;
            }

            // Desactivar autocommit si vas a manejar transacciones manuales
            con.setAutoCommit(false);

            // Instanciar DAO con la conexión
            RolDao rolDao = new RolDao(con);

            // Obtener lista de roles
            List<Rol> listaRoles = rolDao.obtenerTodos();

            // Enviar lista al JSP
            request.setAttribute("listaRoles", listaRoles);

            // Confirmar la transacción 
            con.commit();

            // Redirigir al JSP de registro
            request.getRequestDispatcher("/Vistas/Login/registro.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); 
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            request.setAttribute("mensaje", "Ocurrió un error al cargar los roles.");
            request.getRequestDispatcher("/Vistas/Login/registro.jsp").forward(request, response);

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
