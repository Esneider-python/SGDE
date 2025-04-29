<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Actualizar Piso</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloPiso.css">
    </head>
    <body>
        <h2>Actualizar Piso</h2>

        <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
            <input type="hidden" name="accion" value="actualizar">

            <label>ID del piso:</label>
            <input type="number" name="idPiso" required><br>

            <label>Nuevo número de piso:</label>
            <input type="number" name="numeroPiso" required><br>

            <label>Nuevo ID de bloque:</label>
            <input type="number" name="idBloque" required><br>

            <label>Nueva cédula de usuario:</label>
            <input type="text" name="cedulaUsuario" required><br>

            <button type="submit">Actualizar</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Piso/menuPiso.jsp">
            <button type="submit">Volver al Menú Piso</button>
        </form>
    </body>
</html>
