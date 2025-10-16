<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="paquete.modelo.Colegio" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Actualizar Colegio</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosColegiosGeneral.css">
    </head>
    <body>
        <h2>Actualizar Colegio</h2>

        <% Colegio colegio = (Colegio) request.getAttribute("colegio"); %>

        <% if (request.getAttribute("mensaje") != null) { %>
            <p style="color: green;"><%= request.getAttribute("mensaje") %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/ColegioServlet" method="post">
            <input type="hidden" name="accion" value="actualizar">

            <label>ID del Colegio:</label><br>
            <input type="number" name="id_colegio" value="<%= colegio != null ? colegio.getId() : "" %>" readonly><br><br>

            <label>Nombre del Colegio:</label><br>
            <input type="text" name="nombre_colegio"  required><br><br>

            <label>Cédula del Usuario que Actualiza:</label><br>
            <input type="text" name="cedula_usuario" required><br><br>

            <button type="submit">Actualizar</button>
        </form>

        <br>
        <form action="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" method="get">
            <button type="submit" class="boton">Volver al Menú</button>
        </form>
    </body>
</html>
