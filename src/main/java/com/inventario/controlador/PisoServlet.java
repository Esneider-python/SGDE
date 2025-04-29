package com.mycompany.sgde.controlador;

import com.inventario.modelo.Bloque;
import com.inventario.modelo.Piso;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.BloqueDao;
import com.mycompany.sgde.dao.PisoDao;
import com.mycompany.sgde.dao.UsuarioDao;
import com.mycompany.sgde.util.Conexion;
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
                case "eliminar":
                    eliminarPiso(request, usuarioDao, pisoDao);
                    request.setAttribute("mensaje", "Piso eliminado correctamente.");
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

        request.getRequestDispatcher("/Vistas/Piso/menuPiso.jsp").forward(request, response);
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
        int idPiso = Integer.parseInt(request.getParameter("idPiso"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula del usuario no existe.");
        }

        Piso piso = pisoDao.obtenerPorId(idPiso);
        if (piso == null) {
            throw new IllegalArgumentException("El piso no existe.");
        }

        pisoDao.eliminar(idPiso);
    }
}
