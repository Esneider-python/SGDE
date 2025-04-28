package com.inventario.controlador;

import com.inventario.modelo.Colegio;
import com.inventario.modelo.Sede;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.ColegioDao;
import com.mycompany.sgde.dao.SedeDao;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

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
                Usuario usuario = new Usuario(idUsuarioRegistra);
                usuario.setIdUsuario(idUsuario);

                Colegio colegio = new Colegio(idColegio);
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("actualizar".equals(accion)) {
            String idSedeParam = request.getParameter("idSede");
            String nuevoNombre = request.getParameter("nombre");
            String colegioNombre = request.getParameter("colegioNombre");
            String cedulaUsuario = request.getParameter("cedulaUsuario");

            // Validar los parámetros
            if (idSedeParam == null || idSedeParam.trim().isEmpty()) {
                request.setAttribute("mensaje", "El ID de la sede no puede estar vacío.");
                request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                return;
            }

            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                request.setAttribute("mensaje", "El nombre de la sede no puede estar vacío.");
                request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                return;
            }

            if (colegioNombre == null || colegioNombre.trim().isEmpty()) {
                request.setAttribute("mensaje", "El nombre del colegio no puede estar vacío.");
                request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                return;
            }

            if (cedulaUsuario == null || cedulaUsuario.trim().isEmpty()) {
                request.setAttribute("mensaje", "La cédula del usuario no puede estar vacía.");
                request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                return;
            }

            try (Connection conn = Conexion.getConexion()) {
                SedeDao sedeDao = new SedeDao(conn);
                ColegioDao colegioDao = new ColegioDao(conn);
                UsuarioDao usuarioDao = new UsuarioDao(conn);

                // Obtener el ID de la sede
                int idSede = Integer.parseInt(idSedeParam);
                Sede sede = sedeDao.obtenerPorId(idSede);
                if (sede == null) {
                    request.setAttribute("mensaje", "La sede no existe.");
                    request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                    return;
                }

                // Obtener el ID del colegio por nombre
                Integer idColegio = colegioDao.obtenerIdPorNombre(colegioNombre);
                if (idColegio == null) {
                    request.setAttribute("mensaje", "El colegio no existe.");
                    request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                    return;
                }

                // Obtener el ID del usuario a partir de la cédula
                Integer idUsuarioRegistra = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
                if (idUsuarioRegistra == null) {
                    request.setAttribute("mensaje", "El usuario no existe.");
                    request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
                    return;
                }

                // Actualizar la sede
                sede.setNombre(nuevoNombre);
                sede.setColegio(new Colegio(idColegio));
                sede.setUsuario(new Usuario(idUsuarioRegistra)); // Asignar el usuario que registra

                sedeDao.actualizar(sede); // Llamar al método de actualización en el DAO

                request.setAttribute("mensaje", "Sede actualizada exitosamente.");
            } catch (Exception e) {
                request.setAttribute("mensaje", "Ocurrió un error al actualizar la sede: " + e.getMessage());
                // Aquí podrías registrar el error en un log
            }

            // Redirigir a la vista
            request.getRequestDispatcher("Vistas/Sede/actualizarSede.jsp").forward(request, response);
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

    // Método para listar todas las sedes
    private void listarSedes(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try (Connection conn = Conexion.getConexion()) {
            SedeDao sedeDao = new SedeDao();

            List<Sede> listaSedes = sedeDao.obtenerTodos(); // Este método debe existir en tu SedeDao

            request.setAttribute("listaSedes", listaSedes);
            request.getRequestDispatcher("Vistas/Sede/listarSedes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Vistas/Sede/menuSede.jsp");
        }
    }
}
