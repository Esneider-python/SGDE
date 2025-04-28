<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar Colegio</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosColegiosGeneral.css">
    </head>
    <body>
        <h2>Registrar un nuevo colegio</h2>

        <% if (request.getAttribute("mensaje") != null) {%>
        <p style="color: green;"><%= request.getAttribute("mensaje")%></p>
        <% }%>

        <form action="${pageContext.request.contextPath}/ColegioServlet" method="post">
            <input type="hidden" name="accion" value="registrar">

            <input type="text" name="nombre_colegio" placeholder="Nombre del Colegio" required>
            <input type="text" name="cedula_usuario" placeholder="Cédula del Usuario" required>

            <input type="submit" value="Registrar">
        </form>
            <br>
        <form action="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" method="get">
            <button type="submit" class="boton">Volver al Menú</button>
        </form>

    </body>
</html>
