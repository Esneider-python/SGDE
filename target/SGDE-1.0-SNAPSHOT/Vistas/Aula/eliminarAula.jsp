<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eliminar Aula</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAula.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/validaciones.css">
</head>
<body>
    <h2>Eliminar Aula</h2>

    <form action="${pageContext.request.contextPath}/AulaServlet" method="post">
        <input type="hidden" name="accion" value="eliminar">

        <label>ID del aula:</label>
        <input type="number" name="idAula" id="idAula" value="${param.idAula}" readonly required><br>

        <label>Cédula del usuario:</label>
        <input type="text" name="cedulaUsuario" id="cedula" required><br>

        <button id="btnHabilitado" type="submit" disabled>Eliminar</button>
    </form>

    <form action="${pageContext.request.contextPath}/Vistas/Aula/menuAula.jsp">
        <button type="submit">Volver al Menú Aula</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/Validaciones/Aula/eliminarAula.js"></script> 
</body>
</html>
