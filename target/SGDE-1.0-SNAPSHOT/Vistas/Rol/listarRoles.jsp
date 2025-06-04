<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Listado de Roles</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <h1>Listado de Roles</h1>

        <c:if test="${not empty mensajeError}">
            <p style="color: red;">${mensajeError}</p>
        </c:if>

        <c:if test="${not empty mensajeExito}">
            <p style="color: green;">${mensajeExito}</p>
        </c:if>

        <table border="1" cellpadding="10">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre del Rol</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="rol" items="${listaRoles}">
                    <tr>
                        <td>${rol.idRol}</td>
                        <td>${rol.nombreRol}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <br>
        <div class="volver-menu">
            <form action="${pageContext.request.contextPath}/Vistas/Rol/menuRol.jsp" method="get">
                <button type="submit"> Volver al menu</button>
            </form>
        </div>

    </body>
</html>
