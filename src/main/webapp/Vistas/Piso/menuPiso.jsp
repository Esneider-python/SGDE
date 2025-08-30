
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Menú Piso</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>

        <c:if test="${not empty mensaje}">
            <div style="color: green;">${mensaje}</div>
        </c:if>


        <div class="container">
            <h2>Menú Piso</h2>

            <form action="${pageContext.request.contextPath}/Vistas/Piso/registrarPiso.jsp">
                <button type="submit">Registrar Piso</button>
            </form>
            <form action="${pageContext.request.contextPath}/Vistas/Piso/listarPisos.jsp">
                <button type="submit">Listar Pisos</button>
            </form>

            <form action="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp">
                <button type="submit">Volver al Menú Registros</button>
            </form>

            <c:if test="${not empty mensaje}">
                <p style="color:green;">${mensaje}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p style="color:red;">${error}</p>
            </c:if>
        </div>
    </body>
</html>
