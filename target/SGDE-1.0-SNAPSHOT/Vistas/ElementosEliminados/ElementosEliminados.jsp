<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="paquete.modelo.ElementoEliminado" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Elementos Eliminados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>

        <h2>Historial de Elementos Eliminados</h2>

        <c:if test="${empty elementosEliminados}">
            <p>No hay elementos eliminados registrados.</p>
        </c:if>

        <c:if test="${not empty elementosEliminados}">
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Elemento</th>
                        <th>Motivo de Eliminación</th>
                        <th>Fecha y Hora</th>
                        <th>Usuario que Eliminó</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="e" items="${elementosEliminados}">
                        <tr>
                            <td>${e.idElementoEliminado}</td>
                            <td>${e.elementoId}</td>
                            <td>${e.motivoEliminacion}</td>
                            <td>${e.fechaHoraEliminacion}</td>
                            <td>${e.usuarioElimino}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <br>
        <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp" method="get">
            <button type="submit">Ir Menu Principal</button>
        </form>

    </body>
</html>
