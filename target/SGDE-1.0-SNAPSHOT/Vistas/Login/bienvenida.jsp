<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.inventario.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Bienvenida</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">

    </head>
    <body>
        <h1>Bienvenido, <%= usuario.getNombres()%> <%= usuario.getApellidos()%>!</h1>
        <p>Correo: <%= usuario.getCorreo()%></p>
        <p>Rol ID: <%= usuario.getRolId()%></p>

        <a href="logout.jsp">Cerrar sesión</a>
    </body>
</html>
