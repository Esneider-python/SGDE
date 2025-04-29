<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="com.inventario.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("../../Index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Área principal</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menuPrincipal.css">
    </head>
    <body>
        <header>
            <h1>Area principal</h1>
            <div class="usuario">
                <span>👤</span>
                <span><%= usuario.getNombres()%></span>
            </div>
        </header>

        <main class="contenedor">
            <ul class="menu-lateral">
                <li><a href="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp"><i>🖊️</i> Registros</a></li>
                <li><a href="#"><i>📄</i> Historial de movimientos</a></li>
                <li><a href="#"><i>📊</i> Informes</a></li>
                <li><a href="${pageContext.request.contextPath}/Vistas/Elemento/menuElemento.jsp"><i>💻</i> Elementos</a></li>
                <li><a href="#"><i>🗑️</i> Historial eliminaciones</a></li>
                <li><a href="#"><i>👤</i> Usuarios</a></li>
                <li><a href="#"><i>📋</i> Reportes</a></li>
                <li><a href="${pageContext.request.contextPath}/Vistas/Rol/menuRol.jsp"><i>📋</i> Roles</a></li>
            </ul>

            <div class="salir">
                <form action="${pageContext.request.contextPath}/CerrarSesionServlet" method="post">
                    <button type="submit">SALIR</button>
                </form>
            </div>
        </main>
    </body>
</html>
