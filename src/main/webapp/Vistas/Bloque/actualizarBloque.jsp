<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actualizar Bloque</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilloBloque.css">
</head>
<body>
    <h2>Actualizar Bloque</h2>

    <form action="${pageContext.request.contextPath}/BloqueServlet" method="post">
        <input type="hidden" name="accion" value="actualizar">

        <label>ID del bloque a actualizar:</label>
        <input type="number" name="idBloque" required><br>

        <label>Nuevo número de bloque:</label>
        <input type="number" name="numeroBloque" required><br>

        <label>Nuevo número de sede:</label>
        <input type="number" name="idSede" required><br>

        <label>Nueva cédula del usuario:</label>
        <input type="text" name="cedulaUsuario" required><br>

        <button type="submit">Actualizar</button>
    </form>

    <form action="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp">
        <button type="submit">Volver al Menú</button>
    </form>
</body>
</html>
