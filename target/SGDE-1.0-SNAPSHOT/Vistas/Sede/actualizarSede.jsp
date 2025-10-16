<%@page import="paquete.modelo.Sede"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Sede sede = (Sede) request.getAttribute("sede");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Actualizar Sede</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
    </head>
    <body>
        <div class="container">
            <h1>Actualizar Información de Sede</h1>

            <form action="${pageContext.request.contextPath}/SedeServlet" method="post">
                <input type="hidden" name="accion" value="actualizar">

                <label>ID Sede:</label>
                <input type="number" name="idSede" required  value="<%= sede.getId()%>" readonly><br>

                <label>Nuevo Nombre de la Sede:</label>
                <input type="text" name="nombre" required><br>

                <label>Nombre Colegio:</label>
                <input type="text" name="colegio_name" required value="<%= sede.getColegio().getNombre()%>" readonly><br>

                <label>Nueva cedula Usuario que registra:</label>
                <input type="text" name="cedulaUsuario" required><br>

                <button type="submit">Actualizar</button>
            </form>

            <br>
            <form action="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp">
                <button type="submit">Volver al Menú</button>
            </form>
        </div>
    </body>
</html>
