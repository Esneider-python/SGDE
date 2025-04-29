<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Listado de Bloques</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilloBloque.css">
</head>
<body>
    <h2>Listado de Bloques</h2>

    <form action="${pageContext.request.contextPath}/BloqueServlet" method="post">
        <input type="hidden" name="accion" value="listar">
        <button type="submit">Listar Bloques</button>
    </form>

    <c:if test="${not empty listaBloques}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Número de Bloque</th>
                <th>ID Sede</th>
                <th>ID Usuario</th>
            </tr>
            <c:forEach var="bloque" items="${listaBloques}">
                <tr>
                    <td>${bloque.id}</td>
                    <td>${bloque.numeroBloque}</td>
                    <td>${bloque.sede.id}</td>
                    <td>${bloque.usuarioRegistra.idUsuario}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <form action="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp">
        <button type="submit">Volver al Menú</button>
    </form>
</body>
</html>
