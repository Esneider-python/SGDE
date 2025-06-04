<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="paquete.modelo.Informe" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Informes Generados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="conte">
            <h2>Informes Generados</h2>

            <c:if test="${empty informes}">
                <p>No se han generado informes aún.</p>
            </c:if>

            <c:if test="${not empty informes}">
                <table border="1" cellpadding="8" cellspacing="0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tipo de Informe</th>
                            <th>Fecha de Generación</th>
                            <th>Usuario Generador</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="informe" items="${informes}">
                            <tr>
                                <td>${informe.idInforme}</td>
                                <td>${informe.tipoInforme}</td>
                                <td>${informe.fechaGeneracion}</td>
                                <td>${informe.usuarioGenerador}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <br>
            <form class="form-r" action="${pageContext.request.contextPath}/Vistas/Informe/menuInforme.jsp">
                <button class="cancelar" type="submit">Volver al menu</button>
            </form> 



        </div>

    </body>
</html>
