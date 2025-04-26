<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Crear Rol</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h1>Crear nuevo rol</h1>

            <%-- Mostrar mensajes de error o éxito --%>
            <%
                String mensajeError = (String) request.getAttribute("mensajeError");
                String mensajeExito = (String) request.getAttribute("mensajeExito");
            %>

            <% if (mensajeError != null) {%>
            <div class="mensaje-error"><%= mensajeError%></div>
            <% } %>

            <% if (mensajeExito != null) {%>
            <div class="mensaje-exito"><%= mensajeExito%></div>
            <% }%>

            <form action="${pageContext.request.contextPath}/RolServlet" method="post">
                <input type="hidden" name="accion" value="crear">
                <input type="text" name="nombreRol" placeholder="Nombre del rol" required>
                <button type="submit">Crear rol</button>
            </form>
            <br>
            <a href="menuRol.jsp">← Volver al menú</a>
        </div>
    </body>
</html>
