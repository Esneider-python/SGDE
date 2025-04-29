<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Registrar Aula</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAula.css">
    </head>
    <body>
        <h2>Registrar Aula</h2>

        <form action="${pageContext.request.contextPath}/AulaServlet" method="post">
            <input type="hidden" name="accion" value="registrar">

            <label>ID del piso:</label>
            <input type="number" name="idPiso" required><br>
            
              <label>Numero aula:</label>
            <input type="text" name="numeroAula" required><br>

            <label>Cédula del usuario:</label>
            <input type="text" name="cedulaUsuario" required><br>

            <button type="submit">Registrar</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Aula/menuAula.jsp">
            <button type="submit">Volver al Menú Aula</button>
        </form>
    </body>
</html>
