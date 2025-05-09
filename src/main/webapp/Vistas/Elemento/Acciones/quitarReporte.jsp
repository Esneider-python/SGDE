<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Reportar Elemento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/actualizarElemento.css">
    </head>
    <body>

        <div class="container">

            <h2>Quitar reporte</h2>

            <!-- Mostrar mensaje de error o confirmación -->
            <c:if test="${not empty mensaje}">
                <div class="mensaje">${mensaje}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/ElementoServlet" method="POST">
                <input type="hidden" name="accion" value="quitarReporte">
                <input type="hidden" name="elementoId" value="${elementoId}">

                <label for="estado">Nuevo estado elemento:</label>
                <select name="estado" id="estado" required>
                    <option value="">Seleccione un estado</option>
                    <option value="Bueno">Bueno</option>
                    <option value="Regular">Regular</option>
                    <option value="Funcional">Funcional</option>
                </select>
                
                <label for="cedula">Cedula de usuario quita reporte:</label>
                <input type="text" name="cedula" id="cedula" required>

                <button type="submit">Quitar reporte</button>
            </form>
             <br>
            
            <a href="${pageContext.request.contextPath}/ElementoServlet?accion=listarTodos">
                <button type="button">Ir atras</button>
            </a>

        </div>
    </body>
</html>
