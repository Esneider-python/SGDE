<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Recuperar Contraseña</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/estilos.css">
    </head>
    <body>
        <h2>Recuperar Contraseña</h2>
        <form action="${pageContext.request.contextPath}/EnviarCodigoServlet" method="post">
            <label>Correo electrónico:</label>
            <input type="email" name="correo" required>
            <button type="submit">Enviar código</button>
        </form>

        <p style="color:red">${mensajeError}</p>
        <p style="color:green">${mensajeExito}</p>
    </body>
</html>
