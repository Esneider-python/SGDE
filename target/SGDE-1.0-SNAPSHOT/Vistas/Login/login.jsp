<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Inicio de sesión</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="login-container">
            <div class="login-box">
                <h2>Iniciar sesión</h2>
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
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <div class="input-group">
                        <label>
                            <span class="icon">👤</span>
                            <input type="email" name="correo" placeholder="Correo electrónico" 
                                   value="${correoIngresado != null ? correoIngresado : ''}" required>
                        </label>
                    </div>
                    <div class="input-group">
                        <label>
                            <span class="icon">🔒</span>
                            <input type="password" name="contrasena" placeholder="Contraseña" required>
                        </label>
                    </div>
                    <button type="submit" class="login-button">INGRESAR</button>
                </form>

                <div class="links">
                    <a href="${pageContext.request.contextPath}/Vistas/Login/solicitarCorreo.jsp">¿Olvidaste tu contraseña?</a><br>
                    <a href="${pageContext.request.contextPath}/Vistas/Login/registro.jsp">Registrarme</a>
                </div>

                <a href="${pageContext.request.contextPath}/Index.jsp">← Volver al inicio</a>

            </div>
        </div>
    </body>
</html>
