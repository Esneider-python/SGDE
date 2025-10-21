<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Registrar Bloque</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h2>Registrar Bloque</h2>

            <form class="formu" action="${pageContext.request.contextPath}/BloqueServlet" method="post">
                <input type="hidden" name="accion" value="registrar">

                <label>Número de bloque:</label>
                <input type="number" name="numeroBloque" required><br>

                <label>Sede:</label>
                <select name="idSede" required>
                    <option value="">Selecciona una sede</option>
                    <c:forEach var="sede" items="${listaSedes}">
                        <option value="${sede.id}">${sede.nombre}</option>
                    </c:forEach>
                </select>
                <label>Cédula del usuario:</label>
                <input type="text" name="cedulaUsuario" required><br>

                <button type="submit">Registrar</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp">
                <button type="submit">Volver al Menú</button>
            </form>
        </div>
    </body>
</html>
