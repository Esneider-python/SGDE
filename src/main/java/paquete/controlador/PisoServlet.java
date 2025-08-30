package paquete.controlador;

import paquete.modelo.Bloque;
import paquete.modelo.Piso;
import paquete.modelo.Usuario;
import paquete.dao.BloqueDao;
import paquete.dao.PisoDao;
import paquete.dao.UsuarioDao;
import paquete.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/PisoServlet")
public class PisoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        Connection conn = Conexion.getConexion();

        if (conn == null) {
            request.setAttribute("error", "Error de conexión a la base de datos.");
            request.getRequestDispatcher("/Vistas/Piso/menuPiso.jsp").forward(request, response);
            return;
        }

        PisoDao pisoDao = new PisoDao();
        UsuarioDao usuarioDao = new UsuarioDao(conn);
        BloqueDao bloqueDao = new BloqueDao();

        try {
            switch (accion) {
                case "registrar":
                    registrarPiso(request, usuarioDao, bloqueDao, pisoDao);
                    request.setAttribute("mensaje", "Piso registrado correctamente.");
                    break;
                case "actualizar":
                    actualizarPiso(request, usuarioDao, bloqueDao, pisoDao);
                    request.setAttribute("mensaje", "Piso actualizado correctamente.");
                    break;
                case "mostrarFormularioActualizar":
                    cargarFormularioActualizar(request, response, pisoDao);
                    break;
                case "mostrarFormularioEliminar":
                    mostrarFormularioEliminar(request, response, pisoDao);
                    break;
                case "eliminar":
                    eliminarPiso(request, usuarioDao, pisoDao);
                    request.getRequestDispatcher("/Vistas/Piso/menuPiso.jsp").forward(request, response);
                    break;
                case "listar":
                    List<Piso> lista = pisoDao.obtenerTodos();
                    request.setAttribute("listaPisos", lista);
                    request.getRequestDispatcher("/Vistas/Piso/listarPisos.jsp").forward(request, response);
                    return;
                default:
                    request.setAttribute("error", "Acción no reconocida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
    }

    private void registrarPiso(HttpServletRequest request, UsuarioDao usuarioDao, BloqueDao bloqueDao, PisoDao pisoDao) {
        int numeroPiso = Integer.parseInt(request.getParameter("numeroPiso"));
        int idBloque = Integer.parseInt(request.getParameter("idBloque"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Bloque bloque = bloqueDao.obtenerPorId(idBloque);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

        if (bloque == null) {
            throw new IllegalArgumentException("El bloque no existe.");
        }
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula del usuario no existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Piso piso = new Piso(numeroPiso, bloque, usuario);
        pisoDao.insertar(piso);
    }

    private void actualizarPiso(HttpServletRequest request, UsuarioDao usuarioDao, BloqueDao bloqueDao, PisoDao pisoDao) {
        int idPiso = Integer.parseInt(request.getParameter("idPiso"));
        int numeroPiso = Integer.parseInt(request.getParameter("numeroPiso"));
        int idBloque = Integer.parseInt(request.getParameter("idBloque"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Bloque bloque = bloqueDao.obtenerPorId(idBloque);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

        if (bloque == null) {
            throw new IllegalArgumentException("El bloque no existe.");
        }
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula del usuario no existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Piso piso = new Piso(idPiso, numeroPiso, bloque, usuario);
        pisoDao.actualizar(piso);
    }

    private void eliminarPiso(HttpServletRequest request, UsuarioDao usuarioDao, PisoDao pisoDao) {
        String mensaje;
        try {
            int idPiso = Integer.parseInt(request.getParameter("idPiso"));
            String cedulaUsuario = request.getParameter("cedulaUsuario");

            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
            if (idUsuario == null) {
                mensaje = "La cédula ingresada no existe.";
            } else {
                Piso piso = pisoDao.obtenerPorId(idPiso);
                if (piso == null) {
                    mensaje = "El piso no existe.";
                } else if (pisoDao.estaEnUso(idPiso)) {
                    mensaje = "No se puede eliminar el piso porque está siendo usado por aulas.";
                } else {
                    pisoDao.eliminar(idPiso);
                    mensaje = "Piso eliminado correctamente.";
                }
            }
        } catch (NumberFormatException e) {
            mensaje = "El ID del piso no es válido.";
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al eliminar el piso. Intenta nuevamente.";
        }

        request.setAttribute("mensaje", mensaje);
    }

    private void cargarFormularioActualizar(HttpServletRequest request, HttpServletResponse response, PisoDao pisoDao) throws ServletException, IOException {
        String idParam = request.getParameter("id_piso");

        if (idParam == null || idParam.trim().isEmpty()) {
            enviarMensaje(request, response, "ID del piso requerido.", "Vistas/Piso/menuPiso.jsp");
            return;
        }

        try {
            int idPiso = Integer.parseInt(idParam);
            Piso piso = pisoDao.obtenerPorId(idPiso);

            if (piso == null) {
                enviarMensaje(request, response, "Piso no encontrado.", "Vistas/Piso/menuPiso.jsp");
                return;
            }

            request.setAttribute("piso", piso);
            request.getRequestDispatcher("Vistas/Piso/actualizarPiso.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            enviarMensaje(request, response, "ID inválido.", "Vistas/Piso/menuPiso.jsp");
        }
    }

    private void mostrarFormularioEliminar(HttpServletRequest request, HttpServletResponse response, PisoDao pisoDao) throws ServletException, IOException {
        String idParam = request.getParameter("id_piso");

        if (idParam == null || idParam.trim().isEmpty()) {
            enviarMensaje(request, response, "ID del piso requerido.", "Vistas/Piso/menuPiso.jsp");
            return;
        }

        try {
            int idPiso = Integer.parseInt(idParam);
            Piso piso = pisoDao.obtenerPorId(idPiso);

            if (piso == null) {
                enviarMensaje(request, response, "Piso no encontrado.", "Vistas/Piso/menuPiso.jsp");
                return;
            }

            request.setAttribute("piso", piso);
            request.getRequestDispatcher("Vistas/Piso/eliminarPiso.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            enviarMensaje(request, response, "ID inválido.", "Vistas/Piso/menuPiso.jsp");
        }
    }

    private void enviarMensaje(HttpServletRequest request, HttpServletResponse response, String id_del_piso_requerido, String vistasPisomenuPisojsp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
