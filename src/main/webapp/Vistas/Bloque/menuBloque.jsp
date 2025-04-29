<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilloBloque.css">
        <title>Menú Principal de Bloques</title>
    </head>
    <body>
        <h2>Menú Principal de Bloques</h2>
        <form action="${pageContext.request.contextPath}/Vistas/Bloque/registrarBloque.jsp">
            <button type="submit">Registrar Bloque</button>
        </form>
        <form action="${pageContext.request.contextPath}/Vistas/Bloque/actualizarBloque.jsp">
            <button type="submit">Actualizar Bloque</button>
        </form>
        <form action="${pageContext.request.contextPath}/Vistas/Bloque/eliminarBloque.jsp">
            <button type="submit">Eliminar Bloque</button>
        </form>
        <form action="${pageContext.request.contextPath}/Vistas/Bloque/listarBloques.jsp">
            <button type="submit">Listar Bloques</button>
        </form>
            <br>
        <form action="${pageContext.request.contextPath}/Vistas/MenuRegistros/menuRegistros.jsp">
            <button type="submit">Volver atras</button>
        </form>

        <c:if test="${not empty mensaje}">
            <p style="color:green;">${mensaje}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </body>
</html>
