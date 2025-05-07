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

            <h2>Reportar Elemento</h2>

            <!-- Mostrar mensaje de error o confirmación -->
            <c:if test="${not empty mensaje}">
                <div class="mensaje">${mensaje}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/ElementoServlet" method="POST">
                <input type="hidden" name="accion" value="reportarElemento">
                <input type="hidden" name="elementoId" value="${elementoId}">

                <label for="estado">Estado del Elemento:</label>
                <select name="estado" id="estado" required>
                    <option value="">Seleccione un estado</option>
                    <option value="Disponible">Disponible</option>
                    <option value="En uso">En uso</option>
                    <option value="Reportado">Reportado</option>
                    <option value="Fuera de servicio">Fuera de servicio</option>
                </select>

                <label for="descripcion">Descripción del Reporte:</label>
                <textarea name="descripcion" id="descripcion" rows="4" required></textarea>

                <label for="cedula">Cédula del Usuario que Reporta:</label>
                <input type="text" name="cedula" id="cedula" required>

                <button type="submit">Registrar Reporte</button>
            </form>
             <br>
            
            <a href="${pageContext.request.contextPath}/ElementoServlet?accion=listarTodos">
                <button type="button">Ir atras</button>
            </a>

        </div>
    </body>
</html>
