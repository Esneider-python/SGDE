package com.inventario.controlador;

import com.inventario.modelo.Aula;
import com.inventario.modelo.Piso;
import com.inventario.modelo.Usuario;
import com.mycompany.sgde.dao.AulaDao;
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

@WebServlet("/AulaServlet")
public class AulaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        Connection conn = Conexion.getConexion();

        if (conn == null) {
            request.setAttribute("error", "Error de conexión a la base de datos.");
            request.getRequestDispatcher("/Vistas/Aula/menuAula.jsp").forward(request, response);
            return;
        }

        UsuarioDao usuarioDao = new UsuarioDao(conn);
        PisoDao pisoDao = new PisoDao();
        AulaDao aulaDao = new AulaDao();

        try {
            switch (accion) {
                case "registrar":
                    registrarAula(request, usuarioDao, pisoDao, aulaDao);
                    request.setAttribute("mensaje", "Aula registrada correctamente.");
                    break;
                case "actualizar":
                    actualizarAula(request, usuarioDao, pisoDao, aulaDao);
                    request.setAttribute("mensaje", "Aula actualizada correctamente.");
                    break;
                case "eliminar":
                    eliminarAula(request, usuarioDao, aulaDao);
                    request.setAttribute("mensaje", "Aula eliminada correctamente.");
                    break;
                case "listar":
                    List<Aula> lista = aulaDao.obtenerTodos();
                    request.setAttribute("listaAulas", lista);
                    request.getRequestDispatcher("/Vistas/Aula/listarAulas.jsp").forward(request, response);
                    return;
                default:
                    request.setAttribute("error", "Acción no reconocida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("/Vistas/Aula/menuAula.jsp").forward(request, response);
    }

    private void registrarAula(HttpServletRequest request, UsuarioDao usuarioDao, PisoDao pisoDao, AulaDao aulaDao) {
        try {
            String idPisoStr = request.getParameter("idPiso");
            String numeroAulaStr = request.getParameter("numeroAula");
            String cedulaUsuario = request.getParameter("cedulaUsuario");

            if (idPisoStr == null || numeroAulaStr == null || cedulaUsuario == null
                    || idPisoStr.trim().isEmpty() || numeroAulaStr.trim().isEmpty() || cedulaUsuario.trim().isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            int idPiso = Integer.parseInt(idPisoStr);
            int numeroAula = Integer.parseInt(numeroAulaStr);

            Piso piso = pisoDao.obtenerPorId(idPiso);
            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

            if (piso == null) {
                throw new IllegalArgumentException("El piso no existe.");
            }
            if (idUsuario == null) {
                throw new IllegalArgumentException("La cédula del usuario no existe.");
            }

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);

            Aula aula = new Aula(numeroAula, piso, usuario);
            aulaDao.insertar(aula);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID del piso y número de aula deben ser numéricos.");
        }
    }

    private void actualizarAula(HttpServletRequest request, UsuarioDao usuarioDao, PisoDao pisoDao, AulaDao aulaDao) {
        try {
            String idAulaStr = request.getParameter("idAula");
            String numeroAulaStr = request.getParameter("numeroAula");
            String idPisoStr = request.getParameter("idPiso");
            String cedulaUsuario = request.getParameter("cedulaUsuario");

            if (idAulaStr == null || numeroAulaStr == null || idPisoStr == null || cedulaUsuario == null
                    || idAulaStr.trim().isEmpty() || numeroAulaStr.trim().isEmpty() || idPisoStr.trim().isEmpty() || cedulaUsuario.trim().isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            int idAula = Integer.parseInt(idAulaStr);
            int numeroAula = Integer.parseInt(numeroAulaStr);
            int idPiso = Integer.parseInt(idPisoStr);

            Piso piso = pisoDao.obtenerPorId(idPiso);
            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);

            if (piso == null) {
                throw new IllegalArgumentException("El piso no existe.");
            }
            if (idUsuario == null) {
                throw new IllegalArgumentException("La cédula del usuario no existe.");
            }

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);

            Aula aula = new Aula(idAula, numeroAula, piso, usuario);
            aulaDao.actualizar(aula);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los campos numéricos deben contener números válidos.");
        }
    }

    private void eliminarAula(HttpServletRequest request, UsuarioDao usuarioDao, AulaDao aulaDao) {
        try {
            String idAulaStr = request.getParameter("idAula");
            String cedulaUsuario = request.getParameter("cedulaUsuario");

            if (idAulaStr == null || cedulaUsuario == null || idAulaStr.trim().isEmpty() || cedulaUsuario.trim().isEmpty()) {
                throw new IllegalArgumentException("ID del aula y cédula del usuario son obligatorios.");
            }

            int idAula = Integer.parseInt(idAulaStr);
            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
            if (idUsuario == null) {
                throw new IllegalArgumentException("La cédula del usuario no existe.");
            }

            Aula aula = aulaDao.obtenerPorId(idAula);
            if (aula == null) {
                throw new IllegalArgumentException("El aula no existe.");
            }

            aulaDao.eliminar(idAula);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID del aula debe ser un número válido.");
        }
    }
}
