<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Verificar Código</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/estilos.css">
    </head>
    <body>
        <h2>Verificación de Código</h2>
        <form action="${pageContext.request.contextPath}/RecuperarContrasenaServlet" method="post">
            <input type="hidden"  name="action"  value="verificarCodigo">   
            <label>Código de verificación:</label>
            <input type="number" name="codigo" required pattern="\\d{6}">
            <button type="submit">Verificar</button>
        </form>

        <p style="color:red">${mensajeError}</p>

        <div class="volver-menu">
            <form action="${pageContext.request.contextPath}/Vistas/Login/login.jsp" method="get">
                <button type="submit"> Cancelar</button>
            </form>
        </div>

    </body>
</html>
