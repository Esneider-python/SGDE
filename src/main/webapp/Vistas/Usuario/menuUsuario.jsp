<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
    <head>
        <title>Menú de Gestión de Usuarios</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <h2>Menú de Gestión de Usuarios</h2>
        <div class="message">
            <%
                String mensaje = (String) request.getAttribute("mensaje");
                if (mensaje != null && !mensaje.trim().isEmpty()) {
            %>
            <p class="info"><%= mensaje%></p>
            <%
                }
            %>
        </div>

        <%
            // Verificación de roles permitidos
            if (session != null) {
                String rol = (String) session.getAttribute("rol");
                if (rol != null && (rol.equalsIgnoreCase("administrador")
                        || rol.equalsIgnoreCase("director")
                        || rol.equalsIgnoreCase("rector"))) {
        %>

        <div class="container" >
            <form action="${pageContext.request.contextPath}/Vistas/Usuario/registrarUsuario.jsp" method="get">
                <button class="confirmar color-rojo" type="submit">Registrar usuario</button>
            </form>
                <br>

            <form action="${pageContext.request.contextPath}/UsuarioServlet" method="get">
                <input type="hidden" name="action" value="listarUsuarios">
                <button type="submit">Ver Usuarios</button>
            </form>
                <br>

            <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp" method="get">
                <button type="submit">Ir al Menú Principal</button>
            </form>
                <br>

        </div>
        <%
                } else {
                    // Usuario sin permisos
                    response.sendRedirect("${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp");
                }
            }
        %>
    </body>
</html>
