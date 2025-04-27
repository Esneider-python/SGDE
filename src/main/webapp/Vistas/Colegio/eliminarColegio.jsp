<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Eliminar Colegio</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosColegiosGeneral.css">
    </head>
    <body>
        <h2>Eliminar un colegio</h2>

        <% if (request.getAttribute("mensaje") != null) {%>
        <p style="color: red;"><%= request.getAttribute("mensaje")%></p>
        <% }%>

        <form action="${pageContext.request.contextPath}/ColegioServlet" method="post">
            <input type="hidden" name="accion" value="eliminar">

            <label>ID del colegio a eliminar:</label><br>
            <input type="number" name="id_colegio" required><br><br>
            <label>usuario elimina:</label><br>
            <input type="number" name="usuario_elimina" required><br><br>

            <input type="submit" value="Eliminar">
            <br><!-- <> -->
            <br><!-- comment -->
        </form>
        <form action="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" method="get">
            <button type="submit" class="boton">Volver al Menú</button>
        </form>

    </body>
</html>
