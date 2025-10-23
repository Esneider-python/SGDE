<%@page import="paquete.modelo.Sede"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

                <label>Nuevo nombre de sede:</label>
                <input type="text" name="nombre" required><br>

                <label>Nombre Colegio:</label>
                <select name="colegio_name" required>
                    <option value="">Selecciona un colegio</option>
                    <c:forEach var="colegio" items="${listaColegios}">
                        <option value="${colegio.nombre}">${colegio.nombre}</option>
                    </c:forEach>
                </select>

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
