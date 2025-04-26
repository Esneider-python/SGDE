<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Rol</title>
        <!-- Referencia al archivo CSS externo -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h1>Editar rol</h1>
            
            <!-- Mostrar mensajes -->
            <c:if test="${not empty mensajeError}">
                <div class="mensajeError">${mensajeError}</div>
            </c:if>
            <c:if test="${not empty mensajeExito}">
                <div class="mensajeExito">${mensajeExito}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/RolServlet" method="post">
                <input type="hidden" name="accion" value="actualizar">

                <div style="margin-bottom: 10px;">
                    <label for="idRol">ID del rol:</label>
                    <input type="number" id="idRol" name="idRol" placeholder="ID del rol" required>
                </div>

                <div style="margin-bottom: 10px;">
                    <label for="nuevoNombreRol">Nuevo nombre del rol:</label>
                    <input type="text" id="nuevoNombreRol" name="nuevoNombreRol" required>
                </div>

                <button type="submit">Actualizar rol</button>
            </form>
            <br>
            <a href="menuRol.jsp">← Volver al menú</a>
        </div>
    </body>
</html>
