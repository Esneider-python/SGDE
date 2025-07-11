<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú de Colegios</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="conta">
            <h1>Menú de Colegios</h1>

            <a href="${pageContext.request.contextPath}/Vistas/Colegio/registrarColegio.jsp" class="boton">Registrar Colegio</a>

            <form action="${pageContext.request.contextPath}/ColegioServlet" method="post" style="display:inline;">
                <input type="hidden" name="accion" value="listar">
                <button type="submit" class="boton">Ver Colegios</button>
            </form>

            <br><br>
            <form action="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp" method="get">
                <button type="submit" class="boton">Volver al Menú Registros</button>
            </form>
        </div>
    </body>
</html>
