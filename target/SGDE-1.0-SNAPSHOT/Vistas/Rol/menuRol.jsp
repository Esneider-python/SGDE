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
            <!-- Botón para registrar nuevo rol -->
            <form action="${pageContext.request.contextPath}/Vistas/Rol/crearRol.jsp" method="get">
                <button type="submit">Registrar rol</button>
            </form>

            <!-- Botón para ver roles -->
            <form action="${pageContext.request.contextPath}/RolServlet" method="get">
                <input type="hidden" name="action" value="verRoles">
                <button type="submit">Ver roles</button>
            </form>
        </div>

        <c:if test="${not empty mensajeExito}">
            <div class="mensaje-exito">${mensajeExito}</div>
        </c:if>

        <c:if test="${not empty mensajeError}">
            <div class="mensaje-error">${mensajeError}</div>
        </c:if>

        <!-- Botón para volver al menú principal -->
        <div class="volver-menu">
            <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp" method="get">
                <button type="submit">🏠 Volver al Área Principal</button>
            </form>
        </div>
    </div>
</body>
</html>
