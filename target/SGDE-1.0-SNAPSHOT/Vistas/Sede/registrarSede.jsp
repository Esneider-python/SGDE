<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar Sede</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
    </head>
    <body>
        <div class="container">
            <h1>Registrar Nueva Sede</h1>

            <form action="${pageContext.request.contextPath}/SedeServlet" method="post">
                <input type="hidden" name="accion" value="registrar">

                <label>Nombre de la Sede:</label>
                <input type="text" name="nombre" required><br>

                <label>Nombre Colegio:</label>
                 <select name="colegioNombre" required>
                    <option value="">Selecciona un colegio</option>
                    <c:forEach var="colegio" items="${listaColegios}">
                        <option value="${colegio.nombre}">${colegio.nombre}</option>
                    </c:forEach>
                </select>

                <label>Usuario que registra:</label>
                <input type="number" name="cedulaUsuario" required><br>

                <button type="submit">Registrar</button>
            </form>

            <br>
            <form action="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp">
                <button type="submit">Volver al Menú</button>
            </form>
        </div>
    </body>
</html>
