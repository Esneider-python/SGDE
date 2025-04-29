<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Registrar Piso</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloPiso.css">
    </head>
    <body>
        <h2>Registrar Piso</h2>

        <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
            <input type="hidden" name="accion" value="registrar">

            <label>Número de piso:</label>
            <input type="number" name="numeroPiso" required><br>

            <label>ID del bloque:</label>
            <input type="number" name="idBloque" required><br>

            <label>Cédula del usuario:</label>
            <input type="text" name="cedulaUsuario" required><br>

            <button type="submit">Registrar</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Piso/menuPiso.jsp">
            <button type="submit">Volver al Menú Piso</button>
        </form>
    </body>
</html>
