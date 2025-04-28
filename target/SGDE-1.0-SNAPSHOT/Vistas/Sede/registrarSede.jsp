<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar Sede</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
    </head>
    <body>
        <h1>Registrar Nueva Sede</h1>

        <form action="${pageContext.request.contextPath}/SedeServlet" method="post">
            <input type="hidden" name="accion" value="registrar">

            <label>Nombre de la Sede:</label>
            <input type="text" name="nombre" required><br>

            <label>Nombre Colegio:</label>
            <input type="text" name="colegioNombre" required><br>

            <label>ID Usuario que registra:</label>
            <input type="number" name="cedulaUsuario" required><br>

            <button type="submit">Registrar</button>
        </form>

        <br>
        <form action="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp">
            <button type="submit">Volver al Menú</button>
        </form>
    </body>
</html>
