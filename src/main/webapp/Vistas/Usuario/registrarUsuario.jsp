<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registrar Usuario</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloUsuario.css">
    </head>
    <body>
        <div class="container">
            <div class="message">
                <%
                    String mensaje = (String) request.getAttribute("mensaje");
                    if (mensaje != null && !mensaje.trim().isEmpty()) {
                %>
                <p class="info"><%= mensaje%></p>
                <%
                    }
                %>
            </div>

            <h2>Registrar Usuario</h2>
            <form action="${pageContext.request.contextPath}/UsuarioServlet" method="post">
                <input type="hidden" name="action" value="registrar">
                <label for="nombres">Nombres:</label>
                <input type="text" id="nombres" name="nombres" required>

                <label for="apellidos">Apellidos:</label>
                <input type="text" id="apellidos" name="apellidos" required>

                <label for="telefono">Teléfono:</label>
                <input type="tel" id="telefono" name="telefono" required>

                <label for="correo">Correo:</label>
                <input type="email" id="correo" name="correo" required>

                <label for="cedula">Cédula:</label>
                <input type="text" id="cedula" name="cedula" required>

                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required>

                <label for="rol_id">Código del Rol:</label>
                <input type="number" id="rol_id" name="rol_id" required>

                <button type="submit">Registrar</button>
            </form>
            <br><!-- comment -->
            <form action="${pageContext.request.contextPath}/Vistas/Usuario/menuUsuario.jsp">
                <button type="submit">Volver al Menú</button>
            </form>

    </body>
</html>
