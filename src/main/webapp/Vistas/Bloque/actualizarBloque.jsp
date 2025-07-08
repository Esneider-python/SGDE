<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

                <label>Nuevo número de sede:</label>
                <input type="number" name="idSede" required
                       value="<%= request.getParameter("idSede") != null ? request.getParameter("idSede") : ""%>"><br>

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
