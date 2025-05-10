<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Eliminar Elemento</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/eliminarElemento.css">
    </head>
    <body>
        <div class="container">
            <h2>Eliminar Elemento</h2>

            <!-- Mostrar mensajes del servlet -->
            <c:if test="${not empty mensaje}">
                <div class="mensaje">${mensaje}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/ElementoServlet" method="post">
                <input type="hidden" name="accion" value="eliminarElemento">
                <input type="hidden" name="idElemento" value="${idElemento}">
                <input type="hidden" name="tipoElemento" value="${tipoElemento}">

                <div class="form-group">
                    <label for="motivo">Motivo de Eliminación:</label>
                    <textarea id="motivo" name="motivo" rows="4" required></textarea>
                </div>

                <div class="form-group">
                    <label for="cedulaUsuario">Cédula del Usuario:</label>
                    <input type="text" id="cedulaUsuario" name="cedulaUsuario" pattern="\d+" required />
                </div>

                <button type="submit" class="btn btn-danger">Eliminar</button>
                <a href="${pageContext.request.contextPath}/ElementoServlet?accion=listarTodos">
                    <button type="button" class="btn btn-secondary">Cancelar</button>
                </a>
            </form>
        </div>
    </body>
</html>
