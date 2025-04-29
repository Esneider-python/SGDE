<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Eliminar Aula</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAula.css">
    </head>
    <body>
        <h2>Eliminar Aula</h2>

        <form action="${pageContext.request.contextPath}/AulaServlet" method="post">
            <input type="hidden" name="accion" value="eliminar">

            <label>ID del aula:</label>
            <input type="number" name="idAula" required><br>

            <label>Cédula del usuario:</label>
            <input type="text" name="cedulaUsuario" required><br>

            <button type="submit">Eliminar</button>
        </form>

        <form action="${pageContext.request.contextPath}/Vistas/Aula/menuAula.jsp
