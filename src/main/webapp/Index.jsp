<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Inicio</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bienvenida.css">
    </head>



    <body>



        <h1>Bienvenido al sistema de gestión</h1>
        <div>
            <%
                String mensajeRegistro = (String) session.getAttribute("mensajeRegistro");
                if (mensajeRegistro != null) {
            %>
            <div style="color:green; font-size:18px; margin-bottom:200px;  font-family:Arial, sans-serif;"><%= mensajeRegistro%></div>
            <%
                    session.removeAttribute("mensajeRegistro"); // para que solo se muestre una vez
                }
            %>
        </div>
        <form action="Vistas/Login/login.jsp" method="get">
            <input type="submit" value="Iniciar Sesión">
        </form>

        <form action="Vistas/Login/registro.jsp" method="get">
            <input type="submit" value="Registrarse">
        </form>
    </body>
</html>
