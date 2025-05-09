<%-- 
    Document   : listarReportes
    Created on : 9/05/2025, 1:14:41 p. m.
    Author     : LIZETH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver reportes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloReportes.css" />
    </head>
    <body>
        <h1>Reportes emitidos</h1>

        <!-- Mostrar mensajes de error si existen -->
        <c:if test="${not empty error}">
            <div style="color: red;">
                <strong>Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Botón para listar los reportes -->
        <form action="${pageContext.request.contextPath}/ReporteServlet" method="get">
            <input type="hidden" name="accion" value="listarReporte" />
            <button type="submit">Listar reportes</button>
        </form>

        <!-- Mostrar tabla solo si hay reportes -->
        <c:if test="${not empty listaReportes}">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID Reporte</th>
                        <th>Fecha de Reporte</th>
                        <th>Descripción</th>
                        <th>ID Elemento</th>
                        <th>Usuario que Reportó</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reporte" items="${listaReportes}">
                        <tr>
                            <td>${reporte.idReporte}</td>
                            <td>${  reporte.fechaHoraReporte}</td>
                            <td>${reporte.descripcion}</td>
                            <td>${reporte.elementoReportado}</td>
                            <td>${reporte.usuarioReporta}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <!-- Enlace para volver al menú principal de reportes -->
        <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp">
            <button type="submit">Volver al Menú Principal</button>
        </form>
    </body>
</html>
