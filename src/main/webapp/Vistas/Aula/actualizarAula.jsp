<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actualizar Aula</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAula.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/validaciones.css">
</head>
<body>
    <h2>Actualizar Aula</h2>

    <form action="${pageContext.request.contextPath}/AulaServlet" method="post">
        <input type="hidden" name="accion" value="actualizar">

        <label>ID del aula:</label>
        <input type="number" name="idAula" id="idAula" value="${param.id}" readonly required><br>

        <label>Número aula:</label>
        <input type="text" name="numeroAula" id="numAula" required><br>

        <label>Nuevo ID del piso:</label>
        <input type="number" name="idPiso" id="idPiso" required><br>

        <label>Nueva cédula del usuario:</label>
        <input type="text" name="cedulaUsuario" id="cedula" required><br>

        <button id="btnHabilitado" type="submit" disabled>Actualizar</button>
    </form>

    <form action="${pageContext.request.contextPath}/Vistas/Aula/menuAula.jsp">
        <button type="submit">Volver al Menú Aula</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/Validaciones/Aula/actualizarAula.js"></script> 
</body>
</html>
