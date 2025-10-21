<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    <title>Menú Principal de Bloques</title>
</head>
<body>
    <div class="container">
        <h2>Menú Principal de Bloques</h2>

        <!-- Botón para registrar bloque -->
        <form action="${pageContext.request.contextPath}/BloqueServlet" method="get">
            <input type="hidden" name="accion" value="showFormRegister">
            <button type="submit">Registrar Bloque</button>
        </form>

        <!-- Botón para ver/listar bloques -->
        <form action="${pageContext.request.contextPath}/BloqueServlet" method="get">
            <input type="hidden" name="accion" value="listar">
            <button type="submit">Ver Bloques</button>
        </form>

        <br>

        <!-- Botón para volver atrás -->
        <form action="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp" method="get">
            <button type="submit">Volver atrás</button>
        </form>

        <!-- Mensajes de éxito o error -->
        <c:if test="${not empty mensaje}">
            <p style="color:green;">${mensaje}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </div>
</body>
</html>
