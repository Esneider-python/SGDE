<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú Sedes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>

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
        <div class="container">
            <h1>Gestión de Sedes</h1>
            <div class="menu-container">
                <form action="${pageContext.request.contextPath}/SedeServlet" method="get">
                    <input type="hidden" name="accion" value="showFormRegister">
                    <button type="submit">Registrar Sede</button>
                </form>
                <br>
                <form action="${pageContext.request.contextPath}/SedeServlet" method="get">
                    <input type="hidden" name="accion" value="listar">
                    <button type="submit">Listar Sedes</button>
                </form>
                <br>

            </div>
            <br>
            <form action="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp">
                <button type="submit">Ir atras</button>
            </form>
        </div>
    </body>
</html>
