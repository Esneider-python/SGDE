<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Verificar Código</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/estilos.css">
</head>
<body>
    <h2>Verificación de Código</h2>
    <form action="${pageContext.request.contextPath}/VerificarCodigoServlet" method="post">
        <label>Código de verificación:</label>
        <input type="number" name="codigo" required pattern="\\d{6}">
        <button type="submit">Verificar</button>
    </form>

    <p style="color:red">${mensajeError}</p>
</body>
</html>
