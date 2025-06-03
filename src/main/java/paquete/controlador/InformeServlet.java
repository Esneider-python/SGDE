package paquete.controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import paquete.dao.ElementoDao;
import paquete.dao.ElementoEliminadoDao;
import paquete.dao.InformeDao;
import paquete.dao.UsuarioDao;
import paquete.modelo.Elemento;
import paquete.modelo.ElementoEliminado;
import paquete.modelo.Informe;
import paquete.util.Conexion;

import java.io.IOException;
import java.sql.Connection;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/InformeServlet")
public class InformeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipoInforme = request.getParameter("tipoInforme");
        String cedula = request.getParameter("cedulaUsuario");
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        try (Connection conexion = Conexion.getConexion()) {
            if (conexion == null) {
                request.setAttribute("error", "No se pudo conectar con la base de datos.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            conexion.setAutoCommit(false);

            UsuarioDao userDao = new UsuarioDao(conexion);
            int idUsuario = userDao.obtenerIdPorCedula(cedula);

            if (idUsuario == -1) {
                request.setAttribute("error", "Cédula no válida. No se encontró el usuario.");
                request.getRequestDispatcher("/Vistas/Elemento/menuElemento.jsp").forward(request, response);
                return;
            }

            InformeDao daoInforme = new InformeDao();
            daoInforme.insertarInforme(new Informe(tipoInforme, idUsuario));

            ElementoDao daoElemento = new ElementoDao(conexion);
            ElementoEliminadoDao daoEliminado = new ElementoEliminadoDao(conexion);

            switch (tipoInforme) {
                case "anual_aula" -> {
                    List<Elemento> elementos = daoElemento.obtenerElementosVigentes(cedula, fechaInicio, fechaFin);
                    generarPdfElementos(response, elementos, "Informe Anual de Artículos Vigentes en Aula");
                }
                case "anual_eliminados" -> {
                    List<ElementoEliminado> elementos = daoEliminado.obtenerElementosEliminados(cedula, fechaInicio, fechaFin);
                    generarPdfElementosEliminados(response, elementos, "Informe Anual de Artículos Eliminados");
                }
                case "general_sede" -> {
                    List<Elemento> elementos = daoElemento.obtenerElementosGenerales(cedula, fechaInicio, fechaFin);
                    generarPdfElementos(response, elementos, "Informe General de Artículos en la Sede");
                }
                default -> {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de informe inválido.");
                    return;
                }
            }

            conexion.commit();

        } catch (Exception ex) {
            Logger.getLogger(InformeServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error al generar el informe", ex);
        }
    }

    private void generarPdfElementos(HttpServletResponse response, List<Elemento> elementos, String titulo)
            throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + titulo.replace(" ", "_") + ".pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph(titulo.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        document.add(new Paragraph(" ")); // Espacio

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Estado");
        table.addCell("Fecha");
        table.addCell("Aula");

        for (Elemento e : elementos) {
            table.addCell(String.valueOf(e.getIdElemento()));
            table.addCell(e.getNombre());
            table.addCell(e.getEstado());
            table.addCell(String.valueOf(e.getFechaCreacion()));
            table.addCell(String.valueOf(e.getAulaId()));
        }

        document.add(table);
        document.close();
    }

    private void generarPdfElementosEliminados(HttpServletResponse response, List<ElementoEliminado> elementos, String titulo)
            throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + titulo.replace(" ", "_") + ".pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph(titulo.toUpperCase(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Motivo");
        table.addCell("Fecha de Eliminación");

        for (ElementoEliminado e : elementos) {
            table.addCell(String.valueOf(e.getElementoId()));
            table.addCell(e.getMotivoEliminacion());
            table.addCell(e.getFechaHoraEliminacion() != null ? e.getFechaHoraEliminacion().toString() : "");
        }

        document.add(table);
        document.close();
    }
}
