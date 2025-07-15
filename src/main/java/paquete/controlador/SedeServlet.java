package paquete.controlador;

import paquete.modelo.Sede;
import paquete.modelo.Usuario;
import paquete.dao.ColegioDao;
import paquete.dao.SedeDao;
import paquete.dao.UsuarioDao;
import paquete.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import paquete.modelo.Colegio;

@WebServlet("/SedeServlet")
public class SedeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "registrar":
                registrarSede(request, response);
                break;
            case "actualizar":
                actualizarSede(request, response);
                break;
            case "eliminar":
                eliminarSede(request, response);
                break;
            case "cargarFormularioActualizarSede":
                cargarFormularioActualizarSede(request, response);
                break;
            case "cargarFormularioEliminarSede":
                cargarFormularioEliminarSede(request,response);
            default:
                response.sendRedirect("Vistas/Sede/menuSede.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listarSedes(request, response);
    }

    // Método para registrar una sede
    private void registrarSede(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nombreSede = request.getParameter("nombre");
        String nombreColegio = request.getParameter("colegioNombre");
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        try (Connection conn = Conexion.getConexion()) {
            UsuarioDao usuarioDao = new UsuarioDao(conn);
            ColegioDao colegioDao = new ColegioDao(conn);
            SedeDao sedeDao = new SedeDao();

            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
            Integer idColegio = colegioDao.obtenerIdPorNombre(nombreColegio);

            if (idUsuario != null && idColegio != null) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);

                Colegio colegio = new Colegio();
                colegio.setId(idColegio);

                Sede nuevaSede = new Sede(nombreSede, colegio, usuario);
                sedeDao.insertar(nuevaSede);

                request.setAttribute("mensaje", "Registro exitoso de la sede.");
            } else {
                request.setAttribute("mensaje", "Error: Usuario o Colegio no encontrados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar sede.");
        }

        request.getRequestDispatcher("Vistas/Sede/menuSede.jsp").forward(request, response);
    }

    // Método para actualizar una sede
   private void actualizarSede(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int idSede = Integer.parseInt(request.getParameter("idSede"));
    String nuevoNombre = request.getParameter("nombre");
    String idColegio = request.getParameter("colegio_id");
    String cedulaUsuario = request.getParameter("cedulaUsuario");

    try (Connection conn = Conexion.getConexion()) {
        UsuarioDao usuarioDao = new UsuarioDao(conn);
        ColegioDao colegioDao = new ColegioDao(conn);
        SedeDao sedeDao = new SedeDao();

        if (sedeDao.existeSede(idSede)) {
            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

            if (idUsuario != null && idColegio != null) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);

                Colegio colegio = new Colegio();
                colegio.setId(Integer.parseInt(idColegio)); // ✅ importante

                Sede sedeActualizada = new Sede();
                sedeActualizada.setId(idSede);
                sedeActualizada.setNombre(nuevoNombre);
                sedeActualizada.setColegio(colegio);
                sedeActualizada.setUsuarioRegistra(usuario);

                sedeDao.actualizar(sedeActualizada); 

                request.setAttribute("mensaje", "Sede actualizada exitosamente.");
            } else {
                request.setAttribute("mensaje", "Error: Usuario o Colegio no encontrados para actualizar la sede.");
            }
        } else {
            request.setAttribute("mensaje", "Error: La sede con ID " + idSede + " no existe.");
        }

        request.getRequestDispatcher("Vistas/Sede/menuSede.jsp").forward(request, response);

    } catch (Exception e) {
        request.setAttribute("mensaje", "Error inesperado: " + e.getMessage());
        request.getRequestDispatcher("Vistas/Sede/menuSede.jsp").forward(request, response);
    }
}

    // Método para eliminar una sede
    private void eliminarSede(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int idSede = Integer.parseInt(request.getParameter("idSede"));

        try (Connection conn = Conexion.getConexion()) {
            SedeDao sedeDao = new SedeDao();
            Sede sedeExistente = sedeDao.obtenerPorId(idSede);

            if (sedeExistente != null) {
                boolean eliminado = sedeDao.eliminar(idSede);

                if (eliminado) {
                    request.setAttribute("mensaje", "Sede eliminada exitosamente.");
                } else {
                    request.setAttribute("mensaje", "No se pudo eliminar la sede.");
                }
            } else {
                request.setAttribute("mensaje", "Error: La sede no existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar sede.");
        }

        request.getRequestDispatcher("Vistas/Sede/menuSede.jsp").forward(request, response);
    }

        //mostrar formulario actualizar
    private void cargarFormularioActualizarSede(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id_sede");

        if (idParam == null || idParam.trim().isEmpty()) {
            enviarMensaje(request, response, "ID de la sede requerida.", "Vistas/Sede/menuSede.jsp");
            return;
        }

        try {
            int idSede = Integer.parseInt(idParam);
            SedeDao sedeDao = new SedeDao();
            Sede sede = sedeDao.obtenerPorId(idSede);

            if (sede == null) {
                enviarMensaje(request, response, "Colegio no encontrado.", "Vistas/Sede/menuSede.jsp");
                return;
            }

            request.setAttribute("sede", sede);
            request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            enviarMensaje(request, response, "ID inválido.", "Vistas/Sede/menuSede.jsp");
        }
    }
    
    //Mostrar fomulario para eliminar
     private void cargarFormularioEliminarSede(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id_sede");

        if (idParam == null || idParam.trim().isEmpty()) {
            enviarMensaje(request, response, "ID de la sede requerida.", "Vistas/Sede/menuSede.jsp");
            return;
        }

        try {
            int idSede = Integer.parseInt(idParam);
            SedeDao sedeDao = new SedeDao();
            Sede sede = sedeDao.obtenerPorId(idSede);

            request.setAttribute("sede", sede);
            request.getRequestDispatcher("Vistas/Sede/eliminarSede.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            enviarMensaje(request, response, "ID inválido.", "Vistas/Sede/menuSede.jsp");
        }
    }

    // Método para listar todas las sedes
    private void listarSedes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = Conexion.getConexion()) {
            SedeDao sedeDao = new SedeDao();
            List<Sede> listaSedes = sedeDao.obtenerTodos();

            request.setAttribute("listaSedes", listaSedes);
            request.getRequestDispatcher("Vistas/Sede/listarSedes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al listar sedes.");
            request.getRequestDispatcher("Vistas/Sede/listarSedes.jsp").forward(request, response);
        }
    }

    private void enviarMensaje(HttpServletRequest request, HttpServletResponse response, String id_del_colegio_requerido, String vistasColegiomenuColegiosjsp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
