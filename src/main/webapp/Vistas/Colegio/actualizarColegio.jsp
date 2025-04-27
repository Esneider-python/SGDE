<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Actualizar Colegio</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosColegiosGeneral.css">
    </head>
    <body>
        <h2>Actualizar un colegio existente</h2>

        <% if (request.getAttribute("mensaje") != null) {%>
        <p style="color: blue;"><%= request.getAttribute("mensaje")%></p>
        <% }%>

        <form action="${pageContext.request.contextPath}/ColegioServlet" method="post">
            <input type="hidden" name="accion" value="actualizar">

            <label>ID del colegio:</label><br>
            <input type="number" name="id_colegio" required><br><br>

            <label>Nuevo nombre del colegio:</label><br>
            <input type="text" name="nombre_colegio" required><br><br>

            <label>ID Usuario que actualiza:</label><br>
            <input type="number" name="cedula_usuario" required><br><br>

            <input type="submit" value="Actualizar">

        </form>
            <br>
        <form action="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" method="get">
            <button type="submit" class="boton">Volver al Menú</button>
        </form>
    </body>
</html>
