<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Eliminar Sede</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
</head>
<body>
    <h1>Eliminar Sede</h1>

    <form action="${pageContext.request.contextPath}/SedeServlet" method="post">
        <input type="hidden" name="accion" value="eliminar">

        <label>ID de la Sede a eliminar:</label>
        <input type="number" name="idSede" required><br>

        <button type="submit">Eliminar</button>
    </form>

    <br>
    <form action="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp">
        <button type="submit">Volver al Menú</button>
    </form>
</body>
</html>
