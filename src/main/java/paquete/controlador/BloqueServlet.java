package paquete.controlador;

import paquete.modelo.Bloque;
import paquete.modelo.Sede;
import paquete.modelo.Usuario;
import paquete.dao.BloqueDao;
import paquete.dao.SedeDao;
import paquete.dao.UsuarioDao;
import paquete.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/BloqueServlet")
public class BloqueServlet extends HttpServlet {

    //DO POST
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
                    registrarBloque(request, response, usuarioDao, sedeDao, bloqueDao);
                    break;

                case "actualizar":
                    actualizarBloque(request, response, usuarioDao, sedeDao, bloqueDao);
                    break;

                case "eliminar":
                    try {
                        eliminarBloque(request, response, usuarioDao, bloqueDao);
                    } catch (SQLException ex) {
                        Logger.getLogger(BloqueServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;

                default:
                    request.setAttribute("error", "Acción no reconocida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
    }

    // Mostrar formulario para registrar bloques
    private void showFormRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SedeDao sedeDao = new SedeDao();
        try {
            //lista de sedes para mostrar en el form
            List<Sede> listaSedes = sedeDao.obtenerTodos();
            request.setAttribute("listaSedes", listaSedes);
            request.getRequestDispatcher("/Vistas/Bloque/registrarBloque.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void showFormUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SedeDao sedeDao = new SedeDao();
        try {
            //lista de sedes para mostrar en el form
            List<Sede> listaSedes = sedeDao.obtenerTodos();
            request.setAttribute("listaSedes", listaSedes);
            request.getRequestDispatcher("/Vistas/Bloque/actualizarBloque.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void registrarBloque(HttpServletRequest request, HttpServletResponse response, UsuarioDao usuarioDao, SedeDao sedeDao, BloqueDao bloqueDao) throws ServletException, IOException, SQLException {
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
        boolean exito = bloqueDao.insertar(bloque);

        if (exito) {
            listarBloques(request, response, "Bloque registrado correctamente", true);
        } else {
            listarBloques(request, response, "Error al registrar bloque", false);
        }
    }

    private void actualizarBloque(HttpServletRequest request, HttpServletResponse response, UsuarioDao usuarioDao, SedeDao sedeDao, BloqueDao bloqueDao) throws ServletException, IOException, SQLException {
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
        boolean exito = bloqueDao.actualizar(bloque);
        if (exito) {
            listarBloques(request, response, "Bloque actualizado correctamente", true);
        } else {
            listarBloques(request, response, "Error al actualizar bloque", true);
        }

    }

    private void eliminarBloque(HttpServletRequest request, HttpServletResponse response,
            UsuarioDao usuarioDao, BloqueDao bloqueDao)
            throws ServletException, IOException, SQLException {

        int numeroBloque = Integer.parseInt(request.getParameter("numeroBloque"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            listarBloques(request, response, "La cédula ingresada no corresponde a ningún usuario.", false);
            return;
        }

        List<Bloque> bloques = bloqueDao.obtenerTodos();
        Bloque bloqueAEliminar = bloques.stream()
                .filter(b -> b.getNumeroBloque() == numeroBloque)
                .findFirst()
                .orElse(null);

        if (bloqueAEliminar == null) {
            listarBloques(request, response, "No se encontró ningún bloque con el número especificado.", false);
            return;
        }

        boolean eliminado = bloqueDao.eliminar(bloqueAEliminar.getId());
        if (eliminado) {
            listarBloques(request, response, "Bloque eliminado.", true);
        } else {
            listarBloques(request, response, "No se pudo eliminar el bloque. Puede estar referenciado en otra tabla.", false);
        }
    }

    // DO GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            switch (accion) {
                case "showFormRegister":
                    showFormRegister(request, response);
                    break;
                case "showFormUpdate":
                    showFormUpdate(request, response);
                    break;
                case "listar":
                    try {
                        listarBloques(request, response, "", true);
                    } catch (SQLException ex) {
                        Logger.getLogger(BloqueServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    response.sendRedirect("Vistas/Bloque/menuBloque.jsp");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Bloque/menuBloque.jsp").forward(request, response);
        }
    }

    // Metodo para listar bloques 
    private void listarBloques(HttpServletRequest request, HttpServletResponse response, String mensaje, boolean exito) throws ServletException, IOException, SQLException {
        System.out.println("Entró a listar Bloques");
        //Conexion 
        Connection con = Conexion.getConexion();
        try {
            // Instancias de clases 
            BloqueDao bloqueDao = new BloqueDao();
            SedeDao sedeDao = new SedeDao();
            UsuarioDao usuarioDao = new UsuarioDao(con);

            List<Bloque> lista = bloqueDao.obtenerTodos();
            if (lista == null || lista.isEmpty()) {
                request.setAttribute("mensajeVacio", "No hay bloques registrados en el sistema.");
            } else {
                System.out.println("cantidad de registros recuperados:  " + lista.size());

                for (Bloque bloque : lista) {
                    int idSede = bloque.getSede().getId();
                    int idUsuario = bloque.getUsuarioRegistra().getIdUsuario();
                    System.out.println("id sede: " + idSede);
                    System.out.println("id sede: " + idUsuario);

                    bloque.setSede(sedeDao.obtenerPorId(idSede));
                    bloque.setUsuarioRegistra(usuarioDao.obtenerUsuarioPorId(idUsuario));
                }
                request.setAttribute("listaBloques", lista);
            }
            if (mensaje != null) {
                if (exito) {
                    request.setAttribute("mensaje", mensaje);
                } else {
                    request.setAttribute("error", mensaje);
                }
            } else {

            }

            request.getRequestDispatcher("/Vistas/Bloque/listarBloques.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al listar bloques", e);
        }

    }

}
