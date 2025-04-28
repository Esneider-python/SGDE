<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro - SGDE</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <div class="login-container">
        <div class="login-box">
            <h2>Registrarme</h2>

            <c:if test="${not empty errorRegistro}">
                <p class="error-message">${errorRegistro}</p>
            </c:if>
            <c:if test="${not empty resultado}">
                <p class="success-message">${resultado}</p>
            </c:if>
            <c:if test="${not empty mensajeExito}">
                <p class="success-message">${mensajeExito}</p>
            </c:if>
            <c:if test="${not empty mensajeError}">
                <p class="error-message">${mensajeError}</p>
            </c:if>

            <!-- FORMULARIO -->
            <form action="${pageContext.request.contextPath}/RegistrarUsuarioServlet" method="post">
                <input type="text" name="nombres" placeholder="Nombres" required>
                <input type="text" name="apellidos" placeholder="Apellidos" required>
                <input type="text" name="telefono" placeholder="Teléfono">
                <input type="email" name="correo" placeholder="Correo" required>
                <input type="text" name="cedula" placeholder="Cédula" required>
                <input type="password" name="contrasena" placeholder="Contraseña" required>
                <input type="number" name="rol_id" placeholder="Rol (número)" required>
                <input type="hidden" name="origen" value="inicio">

                <button type="submit">Registrarse</button>
            </form>

            <!-- ENLACE PARA VOLVER AL INICIO -->
            <div class="login-links">
               <a href="${pageContext.request.contextPath}/Index.jsp">← Volver al inicio</a>

            </div>
        </div>
    </div>
</body>
</html>
