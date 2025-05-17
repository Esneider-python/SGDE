<%@page import="com.inventario.modelo.Usuario"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Actualizar Usuario</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloUsuario.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <div class="container">
            <h1>Actualizar Usuario</h1>

            <!-- Mensaje de éxito o error -->
            <c:if test="${not empty mensaje}">
                <div class="${mensaje == 'Usuario actualizado correctamente.' ? 'alert-success' : 'alert-error'}">
                    ${mensaje}
                </div>
            </c:if>

            <!-- Verificar si el usuario fue cargado correctamente -->
            <c:if test="${not empty usuario}">
                <form action="${pageContext.request.contextPath}/UsuarioServlet" method="post">
                    <!-- Acción para actualizar -->
                    <input type="hidden" name="action" value="actualizarUsuario">

                    <!-- ID del usuario (oculto) -->
                    <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">

                    <label for="nombres">Nombres:</label>
                    <input type="text" id="nombres" name="nombres" value="${usuario.nombres}" required>

                    <label for="apellidos">Apellidos:</label>
                    <input type="text" id="apellidos" name="apellidos" value="${usuario.apellidos}" required>

                    <label for="telefono">Teléfono:</label>
                    <input type="text" id="telefono" name="telefono" value="${usuario.telefono}" required>

                    <label for="correo">Correo Electrónico:</label>
                    <input type="email" id="correo" name="correo" value="${usuario.correo}" required>

                    <label for="correo">Cedula:</label>
                    <input type="cedula" id="cedula" name="cedula" value="${usuario.cedula}" required>

                    <div class="button-group">
                        <button class="confirmar" type="submit" class="btn btn-save">
                            <i class="fa fa-save"></i> Guardar Cambios
                        </button>
                    </div>
                </form>
                    <br>
                <form action="${pageContext.request.contextPath}/Vistas/Usuario/menuUsuario.jsp">
                    <button class="cancelar" type="submit">Volver al Menú Piso</button>
                </form>
            </c:if>

            <!-- Mensaje si el usuario no fue encontrado -->
            <c:if test="${empty usuario}">
                <div class="alert-error">
                    Usuario no encontrado.
                </div>
                <a href="${pageContext.request.contextPath}/Vistas/Usuario/menuUsuario.jsp" class="btn btn-back">
                    <i class="fa fa-arrow-left"></i> Volver al Menú
                </a>
            </c:if>
        </div>
    </body>
</html>
