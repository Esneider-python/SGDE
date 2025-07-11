<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="paquete.modelo.Colegio" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Eliminar Colegio</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosColegiosGeneral.css">
    </head>
    <body>
        <h2>Eliminar un colegio</h2>

        <% 
            Colegio colegio = (Colegio) request.getAttribute("colegio");
            if (request.getAttribute("mensaje") != null) {
        %>
            <p style="color: red;"><%= request.getAttribute("mensaje") %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/ColegioServlet" method="post">
            <input type="hidden" name="accion" value="eliminar">

            <label>ID del colegio a eliminar:</label><br>
            <input type="number" name="id_colegio" value="<%= colegio != null ? colegio.getId() : "" %>" readonly><br><br>

            <label>Cédula del usuario que elimina:</label><br>
            <input type="number" name="usuario_elimina" required><br><br>

            <button type="submit">Eliminar</button>
            <br><br>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" method="get">
            <button type="submit" class="boton">Volver al Menú</button>
        </form>
    </body>
</html>
