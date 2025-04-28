<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Eliminar Rol</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
</head>
<body>
    <div class="container">
        <h1>Eliminar rol</h1>
        <form action="${pageContext.request.contextPath}/RolServlet" method="post">
            <input type="hidden" name="accion" value="eliminar">
            <input type="number" name="idRol" placeholder="ID del rol a eliminar" required>
            <button type="submit">Eliminar rol</button>
        </form>
        <br>
        <a href="menuRol.jsp">← Volver al menú</a>
    </div>
</body>
</html>
