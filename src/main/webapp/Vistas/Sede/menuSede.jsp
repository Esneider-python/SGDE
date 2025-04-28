<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú Sedes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
    </head>
    <body>
        <h1>Gestión de Sedes</h1>
        <!-- Mostrar mensaje de operación (si existe) -->
        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
        <div class="mensaje">
            <%= mensaje%>
        </div>
        <%
            }
        %>

        <div class="menu-container">
            <form action="${pageContext.request.contextPath}/Vistas/Sede/registrarSede.jsp">
                <button type="submit">Registrar Sede</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/Sede/actualizarSede.jsp">
                <button type="submit">Actualizar Sede</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/Sede/listarSedes.jsp">
                <button type="submit">Listar Sedes</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/Sede/eliminarSede.jsp">
                <button type="submit">Eliminar Sede</button>
            </form>
        </div>
        <br>
        <form action="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp">
            <button type="submit">Ir atras</button>
        </form>
    </body>
</html>
