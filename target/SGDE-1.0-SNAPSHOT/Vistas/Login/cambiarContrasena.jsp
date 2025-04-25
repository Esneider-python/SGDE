<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <title>Restablecer Contraseña</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/estilos.css">
    </head>
    <body>
        <h2>Restablecer Contraseña</h2>
        <form action="${pageContext.request.contextPath}/CambiarContrasenaServlet" method="post">
            <label>Nueva contraseña:</label>
            <input type="password" name="nuevaContrasena" required>
            <button type="submit">Cambiar contraseña</button>
        </form>

        <p style="color:red">${mensajeError}</p>
        <p style="color:green">${mensajeExito}</p>
    </body>
</html>
