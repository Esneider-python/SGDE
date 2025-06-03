<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Registrar Aula</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAula.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/validaciones.css">
        
    </head>
    <body>
        <h2>Registrar Aula</h2>

        <form action="${pageContext.request.contextPath}/AulaServlet" method="post">
            <input type="hidden" name="accion" value="registrar">

            <label>ID del piso:</label>
            <input type="number" name="idPiso" id="idPiso" required><br>

            <label>Numero aula:</label>
            <input type="text" name="numeroAula" id="numAula" required><br>

            <label>Cédula del usuario:</label>
            <input type="text" name="cedulaUsuario" id="cedula" required><br>

            <button id="btnHabilitado" type="submit" disabled>Registrar</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Aula/menuAula.jsp">
            <button type="submit">Volver al Menú Aula</button>

            <script src="${pageContext.request.contextPath}/js/Validaciones/Aula/registrarAula.js"></script> 
        </form>
    </body>
</html>
