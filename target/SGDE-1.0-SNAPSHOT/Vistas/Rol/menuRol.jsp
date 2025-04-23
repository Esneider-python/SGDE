<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Roles</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h1>Gestión de Roles</h1>
            <div class="botones-acciones">
                <form action="${pageContext.request.contextPath}/Vistas/Rol/crearRol.jsp" method="get">
                    <button type="submit">➕ Crear nuevo rol</button>
                </form>
                <form action="${pageContext.request.contextPath}/Vistas/Rol/editarRol.jsp" method="get">
                    <button type="submit">✏️ Actualizar rol</button>
                </form>
                <form action="${pageContext.request.contextPath}/Vistas/Rol/eliminarRol.jsp" method="get">
                    <button type="submit">🗑️ Eliminar rol</button>
                </form>
            </div>
            <c:if test="${not empty mensajeExito}">
                <div class="mensaje-exito">${mensajeExito}</div>
            </c:if>

            <c:if test="${not empty mensajeError}">
                <div class="mensaje-error">${mensajeError}</div>
            </c:if>

        </div>
    </body>
</html>
