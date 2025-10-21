<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Actualizar Bloque</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h2>Actualizar Bloque</h2>

            <form action="${pageContext.request.contextPath}/BloqueServlet" method="post">
                <input type="hidden" name="accion" value="actualizar">

                <label>ID del bloque a actualizar:</label>
                <input type="number" name="idBloque" required
                       value="<%= request.getParameter("idBloque") != null ? request.getParameter("idBloque") : ""%>"
                       readonly><br>

                <label>Nuevo número de bloque:</label>
                <input type="number" name="numeroBloque" required
                       value="<%= request.getParameter("numeroBloque") != null ? request.getParameter("numeroBloque") : ""%>"><br>

                <label>Nueva sede:</label>
                <select name="idSede" required>
                    <option value="">Selecciona una sede</option>
                    <c:forEach var="sede" items="${listaSedes}">
                        <option value="${sede.id}">${sede.nombre}</option>
                    </c:forEach>
                </select>

                <label>Nueva cédula del usuario:</label>
                <input type="text" name="cedulaUsuario" required
                       value="<%= request.getParameter("cedulaUsuario") != null ? request.getParameter("cedulaUsuario") : ""%>"><br>
                <button type="submit">Actualizar</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp">
                <button type="submit">Volver al Menú</button>
            </form>
        </div>
    </body>
</html>
