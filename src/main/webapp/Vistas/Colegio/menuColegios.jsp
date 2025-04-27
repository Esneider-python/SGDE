<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú de Colegios</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosMenuColegios.css">

    </head>
    <body>
        <%
            String mensaje = request.getParameter("mensaje");
            if ("eliminado".equals(mensaje)) {
        %>
        <div style="color: green; font-weight: bold;">
            ¡Colegio eliminado correctamente!
        </div>
        <%
            }
        %>


        <h1>Menú Principal - Gestión de Colegios</h1>

        <a href="${pageContext.request.contextPath}/Vistas/Colegio/registrarColegio.jsp" class="boton">Registrar Colegio</a>
        <a href="${pageContext.request.contextPath}/Vistas/Colegio/actualizarColegio.jsp" class="boton">Actualizar Colegio</a>
        <a href="${pageContext.request.contextPath}/Vistas/Colegio/eliminarColegio.jsp" class="boton">Eliminar Colegio</a>
        <a href="${pageContext.request.contextPath}/Vistas/Colegio/listarColegios.jsp" class="boton">Listar colegios</a>
        <br><!-- comment -->
        <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp" method="get">
            <button type="submit" class="boton">Volver al Menú</button>
        </form>

    </body>
</html>
