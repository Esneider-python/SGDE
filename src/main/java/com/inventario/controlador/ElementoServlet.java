package com.inventario.controlador;

import com.inventario.modelo.*;
import com.mycompany.sgde.dao.*;
import com.mycompany.sgde.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/ElementoServlet")
public class ElementoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        boolean responseForwarded = false; // Variable para controlar el flujo

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            conexion.setAutoCommit(false);

            try {
                switch (accion) {
                    case "registrarTecnologico":
                        registrarTecnologico(request, conexion);
                        request.setAttribute("mensaje", "Elemento tecnológico registrado con éxito.");
                        break;
                    case "registrarMobiliario":
                        registrarMobiliario(request, conexion);
                        request.setAttribute("mensaje", "Elemento mobiliario registrado con éxito.");
                        break;
                    case "listarTodos":
                        listarTodosLosElementos(request, response);
                        responseForwarded = true; // Marca que se ha enviado una respuesta
                        break;
                    case "actualizarGuardar":
                        procesarActualizarElemento(request, response, conexion);
                        conexion.commit();
                        responseForwarded = true; // Marca que se ha enviado una respuesta
                        break;
                    case "mostrarActualizar":
                        mostrarFormularioActualizar(request, response, conexion);
                        responseForwarded = true; // Marca que se ha enviado una respuesta
                        break;
                    case "mostrarFormularioMover":
                        mostrarFormularioMover(request, response, conexion);
                        responseForwarded = true; // Marca que se ha enviado una respuesta
                        break;
                    case "moverElemento":
                        moverElemento(request, response, conexion);
                        responseForwarded = true; // Marca que se ha enviado una respuesta
                        break;
                    case "mostrarFormularioCambioIdentificador":
                        mostrarFormularioCambioIdentificador(request, response, conexion);
                        responseForwarded = true; // Marca que se ha enviado una respuesta
                        break;
                    case "cambiarIdentificador":
                        cambiarIdentificador(request, response, conexion);
                        request.setAttribute("mensaje", "Identificador actualizado exitosamente.");
                        break;

                    case "mostrarFormularioReporte":
                        mostrarFormularioReporte(request, response);
                        break;
                    case "reportarElemento":
                        reportarElemento(request, response);
                        break;
                    case "mostrarFormularioQuitarReporte":
                        mostrarFormularioQuitarReporte(request, response);
                        break;
                    case "quitarReporte":
                        quitarReporte(request, response);

                    case "MostrarFormEliminarElemento":
                        MostrarFormEliminarElemento(request, response);
                        responseForwarded = true;
                        break;
                    case "eliminarElemento":
                        eliminarElemento(request, response);
                        responseForwarded = true;
                        break;

                    default:
                        throw new IllegalArgumentException("Acción no válida.");
                }

                if (!responseForwarded) {
                    conexion.commit();
                }

            } catch (Exception e) {
                conexion.rollback();
                e.printStackTrace();
                request.setAttribute("error", "Error al registrar el elemento: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno: " + e.getMessage());
        }

        // Solo intenta reenviar si no se ha enviado una respuesta
        if (!responseForwarded) {
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void registrarTecnologico(HttpServletRequest request, Connection conexion) throws Exception {
        String nombre = request.getParameter("nombre");
        String estado = request.getParameter("estado");
        String identificador = request.getParameter("identificadorUnico");
        String tipoIdentificador = request.getParameter("tipoIdentificador");
        String marca = request.getParameter("marca");
        String serie = request.getParameter("serie");
        int idAula = Integer.parseInt(request.getParameter("idAula"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        UsuarioDao usuarioDao = new UsuarioDao(conexion);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no existe.");
        }

        ElementoTecnologico tecnologico = new ElementoTecnologico();
        tecnologico.setNombre(nombre);
        tecnologico.setEstado(estado);
        tecnologico.setIdentificadorUnico(identificador);
        tecnologico.setTipoIdentificador(tipoIdentificador);
        tecnologico.setAulaId(idAula);
        tecnologico.setUsuarioRegistra(idUsuario);
        tecnologico.setMarca(marca);
        tecnologico.setSerie(serie);

        ElementoDao elementoDao = new ElementoDao(conexion);
        int idElemento = elementoDao.insertarElemento(tecnologico);
        if (idElemento <= 0) {
            throw new Exception("No se pudo insertar el elemento base.");
        }

        tecnologico.setIdElemento(idElemento);
        ElementoTecnologicoDao tecnologicoDao = new ElementoTecnologicoDao(conexion);
        tecnologicoDao.insertar(tecnologico);
    }

    private void registrarMobiliario(HttpServletRequest request, Connection conexion) throws Exception {
        String nombre = request.getParameter("nombre");
        String estado = request.getParameter("estado");
        String identificador = request.getParameter("identificadorUnico");
        String tipoIdentificador = request.getParameter("tipoIdentificador");
        int idAula = Integer.parseInt(request.getParameter("idAula"));
        String cedulaUsuario = request.getParameter("cedulaUsuario");

        UsuarioDao usuarioDao = new UsuarioDao(conexion);
        Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
        if (idUsuario == null) {
            throw new IllegalArgumentException("La cédula ingresada no existe.");
        }

        ElementosMobiliarios mobiliario = new ElementosMobiliarios();
        mobiliario.setNombre(nombre);
        mobiliario.setEstado(estado);
        mobiliario.setIdentificadorUnico(identificador);
        mobiliario.setTipoIdentificador(tipoIdentificador);
        mobiliario.setAulaId(idAula);
        mobiliario.setUsuarioRegistra(idUsuario);

        ElementoDao elementoDao = new ElementoDao(conexion);
        int idElemento = elementoDao.insertarElemento(mobiliario);
        if (idElemento <= 0) {
            throw new Exception("No se pudo insertar el elemento base.");
        }

        mobiliario.setIdElemento(idElemento);
        ElementoMobiliarioDao mobiliarioDao = new ElementoMobiliarioDao(conexion);
        mobiliarioDao.insertarMobiliario(idElemento);
    }

    private void listarTodosLosElementos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", " No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            ElementoTecnologicoDao tecnologicoDao = new ElementoTecnologicoDao(conexion);
            ElementoMobiliarioDao mobiliarioDao = new ElementoMobiliarioDao(conexion);

            List<Elemento> elementos = new ArrayList<>();
            elementos.addAll(tecnologicoDao.listarElementos());
            elementos.addAll(mobiliarioDao.listarElementos());

            request.setAttribute("elementos", elementos);
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", " Error al listar todos los elementos: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void mostrarFormularioActualizar(HttpServletRequest request, HttpServletResponse response, Connection conexion)
            throws ServletException, IOException {
        int idElemento = Integer.parseInt(request.getParameter("idElemento"));
        String tipo = request.getParameter("tipoElemento");

        try {
            Object elemento;
            if ("tecnologico".equals(tipo)) {
                ElementoTecnologicoDao dao = new ElementoTecnologicoDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            } else {
                ElementoMobiliarioDao dao = new ElementoMobiliarioDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            }

            request.setAttribute("elemento", elemento);
            request.setAttribute("tipo", tipo);
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/actualizarElemento.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al cargar formulario de actualización.");
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void procesarActualizarElemento(HttpServletRequest request, HttpServletResponse response, Connection conexion) throws ServletException, IOException {
        try {
            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            ElementoDao elementoDao = new ElementoDao(conexion);

            int idElemento = Integer.parseInt(request.getParameter("idElemento"));
            String nombre = request.getParameter("nombre");
            String cedula = request.getParameter("cedulaUsuario");
            Integer usuarioRegistra = usuarioDao.obtenerIdPorCedula(cedula);
            int aulaId = Integer.parseInt(request.getParameter("aulaId"));
            String tipoElemento = request.getParameter("tipoElemento");

            if (usuarioRegistra == null) {
                throw new IllegalArgumentException("La cédula ingresada no está registrada.");
            }

            boolean exito = false;

            if ("tecnologico".equals(tipoElemento)) {
                ElementoTecnologicoDao tecnologicoDao = new ElementoTecnologicoDao(conexion);
                ElementoTecnologico tecnologico = tecnologicoDao.obtenerPorId(idElemento);  // ← obtiene datos existentes

                if (tecnologico == null) {
                    throw new IllegalArgumentException(" No se encontró el elemento tecnológico con ID: " + idElemento);
                }

                // Solo modificamos lo necesario
                tecnologico.setNombre(nombre);
                tecnologico.setUsuarioRegistra(usuarioRegistra);
                tecnologico.setAulaId(aulaId);
                tecnologico.setMarca(request.getParameter("marca"));
                tecnologico.setSerie(request.getParameter("serie"));

                exito = elementoDao.actualizarElemento(tecnologico);

            } else if ("mobiliario".equals(tipoElemento)) {
                ElementoMobiliarioDao mobiliarioDao = new ElementoMobiliarioDao(conexion);
                ElementosMobiliarios mobiliario = mobiliarioDao.obtenerPorId(idElemento);  // ← obtiene datos existentes

                if (mobiliario == null) {
                    throw new IllegalArgumentException("No se encontró el elemento mobiliario con ID: " + idElemento);
                }

                // Solo modificamos lo necesario
                mobiliario.setNombre(nombre);
                mobiliario.setUsuarioRegistra(usuarioRegistra);
                mobiliario.setAulaId(aulaId);

                exito = elementoDao.actualizarElemento(mobiliario);
            }

            if (exito) {
                request.setAttribute("mensaje", " Elemento actualizado exitosamente.");
            } else {
                request.setAttribute("mensaje", " No se pudo actualizar el elemento.");
            }

            listarTodosLosElementos(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", " Error al actualizar: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }

    }

    private void mostrarFormularioMover(HttpServletRequest request, HttpServletResponse response, Connection conexion)
            throws ServletException, IOException {
        try {
            int idElemento = Integer.parseInt(request.getParameter("idElemento"));
            String tipo = request.getParameter("tipoElemento");

            Object elemento;

            if ("tecnologico".equals(tipo)) {
                ElementoTecnologicoDao dao = new ElementoTecnologicoDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            } else {
                ElementoMobiliarioDao dao = new ElementoMobiliarioDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            }

            if (elemento == null) {
                throw new Exception("Elemento no encontrado.");
            }

            request.setAttribute("elemento", elemento);
            request.setAttribute("tipoElemento", tipo);
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar el formulario de movimiento: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void moverElemento(HttpServletRequest request, HttpServletResponse response, Connection conexion)
            throws ServletException, IOException {
        String idElementoStr = request.getParameter("idElemento");
        String idAulaDestinoStr = request.getParameter("idAulaDestino");
        String cedulaUsuario = request.getParameter("cedulaUsuario");
        String tipoElemento = request.getParameter("tipoElemento"); // "tecnologico" o "mobiliario"

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (idElementoStr == null || idAulaDestinoStr == null || cedulaUsuario == null || tipoElemento == null
                || idElementoStr.isEmpty() || idAulaDestinoStr.isEmpty() || cedulaUsuario.isEmpty() || tipoElemento.isEmpty()) {
            request.setAttribute("error", " Todos los campos son obligatorios.");
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
            return;
        }

        try {
            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            ElementoDao elementoDao = new ElementoDao(conexion);
            AulaDao aulaDao = new AulaDao(conexion);
            HistorialMovimientoDao historialMovimientoDao = new HistorialMovimientoDao(conexion);

            int idElemento = Integer.parseInt(idElementoStr);
            int numeroAulaDestino = Integer.parseInt(idAulaDestinoStr);

            // Validar que el elemento existe
            if (!elementoDao.existeElemento(idElemento, tipoElemento)) {
                request.setAttribute("error", "❌ El elemento especificado no existe.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
                return;
            }

            // Obtener el ID del aula destino
            int idAulaDestino = aulaDao.obtenerIdPorNumero(numeroAulaDestino);

            if (idAulaDestino == -1) {
                request.setAttribute("error", "❌ El número de aula destino no existe.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
                return;
            }

            if (!elementoDao.existeAula(idAulaDestino)) {
                request.setAttribute("error", "El aula destino no existe.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
                return;
            }

            // Obtener el aula de origen
            int idAulaOrigen = elementoDao.obtenerAulaIdPorElemento(idElemento);
            if (idAulaOrigen == -1) {
                request.setAttribute("error", "No se pudo obtener el aula actual del elemento.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
                return;
            }

            // Obtener ID del usuario
            int idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
            if (idUsuario == -1) {
                request.setAttribute("error", " Cédula no encontrada.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
                return;
            }

            // Actualizar el aula del elemento
            boolean actualizado = elementoDao.actualizarAula(idElemento, idAulaDestino);
            if (!actualizado) {
                throw new SQLException(" No se pudo actualizar el aula del elemento.");
            }

            // Registrar el movimiento
            historialMovimientoDao.insertarMovimiento(idElemento, tipoElemento, idAulaOrigen, idAulaDestino, idUsuario);

            // Confirmar los cambios
            conexion.commit();

            request.setAttribute("numeroAula", numeroAulaDestino);
            request.setAttribute("cedulaUsuario", cedulaUsuario);
            request.setAttribute("mensaje", " Elemento movido correctamente.");
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                conexion.rollback();
                System.out.println("Rollback ejecutado por error: " + e.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // Muestra la excepción real
            request.setAttribute("error", " Error al mover el elemento: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/moverElemento.jsp").forward(request, response);
        }

    }

    private void mostrarFormularioCambioIdentificador(HttpServletRequest request, HttpServletResponse response, Connection conexion)
            throws ServletException, IOException {
        try {
            int idElemento = Integer.parseInt(request.getParameter("idElemento"));
            String tipo = request.getParameter("tipoElemento");

            Object elemento;

            if ("tecnologico".equals(tipo)) {
                ElementoTecnologicoDao dao = new ElementoTecnologicoDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            } else {
                ElementoMobiliarioDao dao = new ElementoMobiliarioDao(conexion);
                elemento = dao.obtenerPorId(idElemento);
            }

            if (elemento == null) {
                throw new Exception("Elemento no encontrado.");
            }

            request.setAttribute("elemento", elemento);
            request.setAttribute("tipoElemento", tipo);
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/cambiarIdentificador.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar el formulario de cambio de identificador: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void cambiarIdentificador(HttpServletRequest request, HttpServletResponse response, Connection conexion) throws Exception {
        try {
            // Obtenemos los datos del formulario JSP
            int idElemento = Integer.parseInt(request.getParameter("idElemento"));
            String tipoElemento = request.getParameter("tipoElemento"); // Puede omitirse si no se usa
            String nuevoIdentificador = request.getParameter("identificadorNuevo");
            String cedulaUsuario = request.getParameter("cedulaUsuario");
            System.out.println("Cédula recibida: '" + cedulaUsuario + "'");
            String tipoIdentificadorAnterior = request.getParameter("tipoIdentificadorAnterior");
            String tipoIdentificadorNuevo = request.getParameter("tipoIdentificadorNuevo");
            String identificadorAnterior = request.getParameter("identificadorActual");

            // Validaciones básicas
            if (nuevoIdentificador == null || nuevoIdentificador.trim().isEmpty()) {
                throw new IllegalArgumentException("El nuevo identificador no puede estar vacío.");
            }
            if (identificadorAnterior == null || identificadorAnterior.trim().isEmpty()) {
                throw new IllegalArgumentException("No se encontró el identificador actual del elemento (enviado por el formulario).");
            }

            // DAOs
            UsuarioDao usuarioDao = new UsuarioDao(conexion);
            ElementoDao elementoDao = new ElementoDao(conexion);
            CambioIdentificadorDAO cambioDao = new CambioIdentificadorDAO(conexion);

            // Validar cédula de usuario
            Integer idUsuario = usuarioDao.obtenerIdPorCedula(cedulaUsuario);
            if (idUsuario == null) {
                throw new IllegalArgumentException("La cédula ingresada no está registrada.");
            }

            // Actualizar identificador
            boolean actualizado = elementoDao.actualizarIdentificador(idElemento, nuevoIdentificador, tipoIdentificadorNuevo);
            if (!actualizado) {
                throw new Exception("No se pudo actualizar el identificador.");
            }

            // Registrar cambio en la tabla cambio_identificador
            cambioDao.insertar(idElemento, identificadorAnterior, tipoIdentificadorAnterior, nuevoIdentificador, tipoIdentificadorNuevo, idUsuario);

            // Confirmar cambios
            conexion.commit();

            // Redireccionar con éxito
            request.setAttribute("mensaje", "Identificador actualizado correctamente.");
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/cambiarIdentificador.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                conexion.rollback();
                System.out.println("Rollback ejecutado por error: " + e.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // Log detallado
            request.setAttribute("mensaje", "Error al cambiar el identificador: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/cambiarIdentificador.jsp").forward(request, response);
        }
    }

    private void verHistorialCambios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("mensaje", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            CambioIdentificadorDAO cambioIdentificadorDao = new CambioIdentificadorDAO(conexion);
            List<CambioIdentificador> historial = cambioIdentificadorDao.obtenerTodos();

            request.setAttribute("historial", historial);
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/VerCambioIdentificadores.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al cargar el historial de cambios: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

    private void mostrarFormularioReporte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String elementoId = request.getParameter("elementoId");
        if (elementoId == null || elementoId.trim().isEmpty()) {
            request.setAttribute("mensaje", "No se proporcionó un ID de elemento válido.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
            return;
        }
        request.setAttribute("elementoId", elementoId);
        request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
    }

    private void mostrarFormularioQuitarReporte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String elementoId = request.getParameter("elementoId");
        if (elementoId == null || elementoId.trim().isEmpty()) {
            request.setAttribute("mensaje", "No se proporcionó un ID de elemento válido.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
            return;
        }
        request.setAttribute("elementoId", elementoId);
        request.getRequestDispatcher("/Vistas/Elemento/Acciones/quitarReporte.jsp").forward(request, response);
    }

    private void reportarElemento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String elementoIdStr = request.getParameter("elementoId").trim();
        String descripcion = request.getParameter("descripcion").trim();
        String cedula = request.getParameter("cedula").trim();
        String nuevoEstado = request.getParameter("estado").trim();

        // Validar que no se envíen datos vacíos
        if (elementoIdStr.isEmpty() || descripcion.isEmpty() || cedula.isEmpty() || nuevoEstado.isEmpty()) {
            request.setAttribute("mensaje", "Por favor, complete todos los campos antes de enviar el reporte.");
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
            return;
        }

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("mensaje", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }
            conexion.setAutoCommit(false);

            // Validar cédula y obtener el ID del usuario
            UsuarioDao usuarioDAO = new UsuarioDao(conexion);
            int usuarioId = usuarioDAO.obtenerIdPorCedula(cedula);
            if (usuarioId == -1) {
                request.setAttribute("mensaje", "La cédula ingresada no corresponde a ningún usuario registrado.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
                return;
            }

            // Actualizar el estado del elemento
            int elementoId = Integer.parseInt(elementoIdStr);
            ElementoDao elementoDAO = new ElementoDao(conexion);
            boolean actualizado = elementoDAO.actualizarEstadoElemento(elementoId, nuevoEstado);
            if (!actualizado) {
                request.setAttribute("mensaje", "No se pudo actualizar el estado del elemento.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
                return;
            }

            // Registrar el reporte usando ReporteDAO
            ReporteDao reporteDAO = new ReporteDao();
            boolean reporteRegistrado = reporteDAO.registrarReporte(conexion, descripcion, elementoId, usuarioId);
            if (!reporteRegistrado) {
                request.setAttribute("mensaje", "No se pudo registrar el reporte.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
                return;
            }

            // Confirmar la transacción manualmente
            conexion.commit();

            request.setAttribute("mensaje", "El reporte se registró correctamente.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el reporte: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
            return;
        }
    }
    
    private void quitarReporte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String elementoIdStr = request.getParameter("elementoId").trim();
        String cedula = request.getParameter("cedula").trim();
        String nuevoEstado = request.getParameter("estado").trim();

        // Validar que no se envíen datos vacíos
        if (elementoIdStr.isEmpty() || cedula.isEmpty() || nuevoEstado.isEmpty()) {
            request.setAttribute("mensaje", "Por favor, complete todos los campos antes de enviar el reporte.");
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
            return;
        }

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("mensaje", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }
            conexion.setAutoCommit(false);

            // Validar cédula y obtener el ID del usuario
            UsuarioDao usuarioDAO = new UsuarioDao(conexion);
            int usuarioId = usuarioDAO.obtenerIdPorCedula(cedula);
            if (usuarioId == -1) {
                request.setAttribute("mensaje", "La cédula ingresada no corresponde a ningún usuario registrado.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
                return;
            }

            // Actualizar el estado del elemento
            int elementoId = Integer.parseInt(elementoIdStr);
            ElementoDao elementoDAO = new ElementoDao(conexion);
            boolean actualizado = elementoDAO.actualizarEstadoElemento(elementoId, nuevoEstado);
            if (!actualizado) {
                request.setAttribute("mensaje", "No se pudo eliminar el reporte al elemento.");
                request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
                return;
            }

            // Registrar el reporte usando ReporteDAO
            ReporteDao reporteDAO = new ReporteDao();
            boolean reporteEliminado = reporteDAO.eliminarReporte(elementoId);
            if (!reporteEliminado) {
                request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
                return;
            }

            // Confirmar la transacción manualmente
            conexion.commit();

            request.setAttribute("mensaje", "El reporte se elimino correctamente.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el reporte: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/reportarElemento.jsp").forward(request, response);
            return;
        }
    }

    private void MostrarFormEliminarElemento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID y tipo del elemento desde los parámetros del request
        String idElemento = request.getParameter("idElemento");
        String tipoElemento = request.getParameter("tipoElemento");

        // Validar que ambos parámetros estén presentes
        if (idElemento == null || idElemento.trim().isEmpty() || tipoElemento == null || tipoElemento.trim().isEmpty()) {
            request.setAttribute("mensaje", "No se proporcionó un ID o tipo de elemento válido.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
            return;
        }

        // Pasar los datos al formulario de eliminación
        request.setAttribute("idElemento", idElemento);
        request.setAttribute("tipoElemento", tipoElemento);
        request.getRequestDispatcher("/Vistas/Elemento/Acciones/eliminarElemento.jsp").forward(request, response);
    }

    private void eliminarElemento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String elementoIdStr = request.getParameter("idElemento");
        String motivo = request.getParameter("motivo");
        String cedula = request.getParameter("cedulaUsuario");
        String tipoElemento = request.getParameter("tipoElemento");

        // Validar que los datos no estén vacíos
        if (elementoIdStr == null || elementoIdStr.trim().isEmpty()
                || motivo == null || motivo.trim().isEmpty()
                || cedula == null || cedula.trim().isEmpty()
                || tipoElemento == null || tipoElemento.trim().isEmpty()) {

            request.setAttribute("mensaje", "Por favor, complete todos los campos antes de enviar.");
            request.getRequestDispatcher("/Vistas/Elemento/Acciones/eliminarElemento.jsp").forward(request, response);
            return;
        }

        // Validamos la conexion
        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("mensaje", "Error al conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            conexion.setAutoCommit(false);

            // Validar cédula y obtener el ID del usuario
            UsuarioDao usuarioDAO = new UsuarioDao(conexion);
            int usuarioId = usuarioDAO.obtenerIdPorCedula(cedula.trim());
            if (usuarioId == -1) {
                request.setAttribute("mensaje", "La cédula ingresada no corresponde a ningún usuario registrado.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/eliminarElemento.jsp").forward(request, response);
                return;
            }

            // Validar que el ID del elemento sea un número
            int elementoId;
            try {
                elementoId = Integer.parseInt(elementoIdStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "El ID del elemento debe ser un número válido.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/eliminarElemento.jsp").forward(request, response);
                return;
            }

            // Crear Daos necesarios
            ElementoDao elementoDAO = new ElementoDao(conexion);
            ElementoEliminadoDao elementoEliminadoDao = new ElementoEliminadoDao(conexion);

            // Cambiar estado a eliminado al elemento
            boolean actualizado = elementoDAO.actualizarEstadoElemento(elementoId, "Eliminado");
            if (!actualizado) {
                conexion.rollback();
                request.setAttribute("mensaje", "No se pudo actualizar el estado del elemento. Verifique que el elemento exista.");
                request.getRequestDispatcher("/Vistas/Elemento/Acciones/eliminarElemento.jsp").forward(request, response);
                return;
            }

            // Registrar el elemento eliminado
            boolean registrado = elementoEliminadoDao.registrarElementoEliminado(elementoId, motivo.trim(), usuarioId);
            if (!registrado) {
                conexion.rollback();
                request.setAttribute("mensaje", "No se pudo registrar la eliminación del elemento.");
                request.getRequestDispatcher("/Vistas/Elemento/Elemento/listarElementos.jsp").forward(request, response);
                return;
            }

            // Confirmar la transacción
            conexion.commit();
            request.setAttribute("mensaje", "Elemento eliminado correctamente.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error interno al intentar eliminar el elemento. Consulte al administrador del sistema.");
            request.getRequestDispatcher("/Vistas/Elemento/listarElementos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try (Connection conexion = Conexion.getConexion()) {
            if ("listarTodos".equals(accion)) {
                listarTodosLosElementos(request, response);
            } else if ("verHistorialCambios".equals(accion)) {
                verHistorialCambios(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método GET no permitido para esta acción.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
        }
    }

}
