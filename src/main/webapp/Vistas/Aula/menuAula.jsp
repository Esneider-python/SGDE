<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menú Aula</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAula.css">
</head>
<body>
    <h2>Menú Aula</h2>

    <form action="${pageContext.request.contextPath}/Vistas/Aula/registrarAula.jsp">
        <button type="submit">Registrar Aula</button>
    </form>
    <form action="${pageContext.request.contextPath}/Vistas/Aula/actualizarAula.jsp">
        <button type="submit">Actualizar Aula</button>
    </form>
    <form action="${pageContext.request.contextPath}/Vistas/Aula/eliminarAula.jsp">
        <button type="submit">Eliminar Aula</button>
    </form>
    <form action="${pageContext.request.contextPath}/Vistas/Aula/listarAulas.jsp">
        <button type="submit">Listar Aulas</button>
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
</body>
</html>
