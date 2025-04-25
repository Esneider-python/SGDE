<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Restablecer Contraseña</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cambiarContrasena.css">

    <!-- Redirección automática si hay éxito -->
    <c:if test="${not empty mensajeExito}">
        <meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/Index.jsp" />
    </c:if>
</head>
<body>
    <h2>Restablecer Contraseña</h2>
    <form action="${pageContext.request.contextPath}/CambiarContrasenaServlet" method="post">
        <label>Nueva contraseña:</label>
        <input type="password" name="nuevaContrasena" required>

        <label>Confirmar contraseña:</label>
        <input type="password" name="confirmarContrasena" required>

        <button type="submit">Cambiar contraseña</button>
    </form>

    <p style="color:red">${mensajeError}</p>
    <p style="color:green">${mensajeExito}</p>
</body>
</html>
