<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Menú Elemento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloElemento.css">
    </head>
    <body>
        <h2>Menú de Gestión de Elementos</h2>

        <form action="${pageContext.request.contextPath}/Vistas/Elemento/registroTecnologico.jsp" method="get">
            <button type="submit">Registrar Elemento Tecnológico</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Elemento/registroMobiliario.jsp" method="get">
            <button type="submit">Registrar Elemento Mobiliario</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp" method="get">
            <button type="submit">Ir Menu Principal</button>
        </form>
    </body>
</html>
