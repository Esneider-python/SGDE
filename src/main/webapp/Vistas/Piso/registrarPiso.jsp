<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Registrar Piso</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/validaciones.css">

    </head>
    <body>
        <div class="container">
            <h2>Registrar Piso</h2>

            <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
                <input type="hidden" name="accion" value="registrar">

                <label>Número de piso:</label>
                <input type="number" name="numeroPiso" id="numPiso" required><br>

                <label>ID del bloque:</label>
                <input type="number" name="idBloque" id="idBloque" required><br>

                <label>Cédula del usuario:</label>
                <input type="text" name="cedulaUsuario" id="cedula" required><br>

                <button id="btnHabilitado" type="submit" disabled>Registrar</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/Piso/menuPiso.jsp">
                <button type="submit">Volver al Menú Piso</button>
            </form>

        </div>
        <script src="${pageContext.request.contextPath}/js/Validaciones/Piso/registrarPiso.js"></script>     
    </body>
</html>
