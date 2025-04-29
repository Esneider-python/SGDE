package com.inventario.controlador;

import com.inventario.modelo.Bloque;
import com.inventario.modelo.Sede;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.BloqueDao;
import com.mycompany.sgde.dao.SedeDao;
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

@WebServlet("/BloqueServlet")
public class BloqueServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        Connection conn = Conexion.getConexion();

        if (conn == null) {
            request.setAttribute("error", "Error de conexión a la base de datos.");
            request.getRequestDispatcher("/Vistas/Bloque/menuBloque.jsp").forward(request, response);
            return;
        }

        UsuarioDao usuarioDao = new UsuarioDao(conn);
        SedeDao sedeDao = new SedeDao();
        BloqueDao bloqueDao = new BloqueDao();

        try {
            switch (accion) {
                case "registrar":
                    registrarBloque(request, usuarioDao, sedeDao, bloqueDao);
                    request.setAttribute("mensaje", "Bloque registrado correctamente.");
                    break;

                case "actualizar":
                    actualizarBloque(request, usuarioDao, sedeDao, bloqueDao);
                    request.setAttribute("mensaje", "Bloque actualizado correctamente.");
                    break;

                case "eliminar":
                    eliminarBloque(request, usuarioDao, bloqueDao);
                    request.setAttribute("mensaje", "Bloque eliminado correctamente.");
                    break;

                case "listar":
                    List<Bloque> lista = bloqueDao.obtenerTodos();
                    request.setAttribute("listaBloques", lista);
                    request.getRequestDispatcher("/Vistas/Bloque/listarBloques.jsp").forward(request, response);
                    return;

                default:
                    request.setAttribute("error", "Acción no reconocida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("/Vistas/Bloque/menuBloque.jsp").forward(request, response);
    }

    private void registrarBloque(HttpServletRequest request, UsuarioDao usuarioDao, SedeDao sedeDao, BloqueDao bloqueDao) {
        int numeroBloque = Integer.parseInt(request.getParameter("numeroBloque"));
        int idSede = Integer.parseInt(request.getParameter("idSede"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Sede sede = sedeDao.obtenerPorId(idSede);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

        if (sede == null) {
            throw new IllegalArgumentException("La sede ingresada no existe.");
        }

        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no corresponde a ningún usuario.");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Bloque bloque = new Bloque(0, numeroBloque, sede, usuario);
        bloqueDao.insertar(bloque);
    }

    private void actualizarBloque(HttpServletRequest request, UsuarioDao usuarioDao, SedeDao sedeDao, BloqueDao bloqueDao) {
        int idBloque = Integer.parseInt(request.getParameter("idBloque"));
        int numeroBloque = Integer.parseInt(request.getParameter("numeroBloque"));
        int idSede = Integer.parseInt(request.getParameter("idSede"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Sede sede = sedeDao.obtenerPorId(idSede);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

        if (sede == null) {
            throw new IllegalArgumentException("La sede ingresada no existe.");
        }

        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no corresponde a ningún usuario.");
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Bloque bloque = new Bloque(idBloque, numeroBloque, sede, usuario);
        bloqueDao.actualizar(bloque);
    }

    private void eliminarBloque(HttpServletRequest request, UsuarioDao usuarioDao, BloqueDao bloqueDao) {
        int numeroBloque = Integer.parseInt(request.getParameter("numeroBloque"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no corresponde a ningún usuario.");
        }

        List<Bloque> bloques = bloqueDao.obtenerTodos();
        Bloque bloqueAEliminar = bloques.stream()
                .filter(b -> b.getNumeroBloque() == numeroBloque)
                .findFirst()
                .orElse(null);

        if (bloqueAEliminar == null) {
            throw new IllegalArgumentException("No se encontró ningún bloque con el número especificado.");
        }

        bloqueDao.eliminar(bloqueAEliminar.getId());
    }
}
