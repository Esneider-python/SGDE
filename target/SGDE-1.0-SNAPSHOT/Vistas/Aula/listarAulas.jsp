<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Listado de Aulas</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h2>Listado de Aulas</h2>

            <form action="${pageContext.request.contextPath}/AulaServlet" method="post">
                <input type="hidden" name="accion" value="listar">
                <button type="submit">Listar Aulas</button>
            </form>

            <c:if test="${not empty listaAulas}">
                <table border="1">
                    <tr>
                        <th>ID Aula</th>
                        <th>Numero Aula</th>
                        <th>ID Piso</th>
                        <th>ID Usuario</th>
                    </tr>
                    <c:forEach var="aula" items="${listaAulas}">
                        <tr>
                            <td>${aula.id}</td>
                            <td>${aula.piso.id}</td>
                            <td>${aula.piso.id}</td>
                            <td>${aula.usuarioRegistra.idUsuario}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
                <br>

            <form action="${pageContext.request.contextPath}/Vistas/Aula/menuAula.jsp">
                <button type="submit">Volver al Menú Aula</button>
            </form>
        </div>
    </body>
</html>
