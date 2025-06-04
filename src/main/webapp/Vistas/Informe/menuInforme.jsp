<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Menú Aula</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h2>Menú Informes</h2>

            <form action="${pageContext.request.contextPath}/Vistas/Informe/Informe.jsp">
                <button type="submit">Generar informes</button>
            </form>
            <form action="${pageContext.request.contextPath}/InformeServlet" method="get">
                <button type="submit">Ver informes</button>
                <input type="hidden" name="action" value="verInformes">
            </form>
            <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp">
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
