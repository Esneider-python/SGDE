<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Eliminar Piso</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloPiso.css">
    </head>
    <body>
        <h2>Eliminar Piso</h2>

        <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
            <input type="hidden" name="accion" value="eliminar">

            <label>ID del piso a eliminar:</label>
            <input type="number" name="idPiso" required><br>

            <label>Cédula del usuario:</label>
            <input type="text" name="cedulaUsuario" required><br>

            <button type="submit">Eliminar</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Piso/menuPiso.jsp">
            <button type="submit">Volver al Menú Piso</button>
        </form>
    </body>
</html>
