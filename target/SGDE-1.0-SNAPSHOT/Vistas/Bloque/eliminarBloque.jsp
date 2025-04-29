<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eliminar Bloque</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilloBloque.css">
</head>
<body>
    <h2>Eliminar Bloque</h2>

    <form action="${pageContext.request.contextPath}/BloqueServlet" method="post">
        <input type="hidden" name="accion" value="eliminar">

        <label>Número del bloque:</label>
        <input type="number" name="numeroBloque" required><br>

        <label>Cédula del usuario:</label>
        <input type="text" name="cedulaUsuario" required><br>

        <button type="submit">Eliminar</button>
    </form>

    <form action="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp">
        <button type="submit">Volver al Menú</button>
    </form>
</body>
</html>
