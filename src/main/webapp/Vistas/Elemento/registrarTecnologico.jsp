<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Registrar Elemento Tecnológico</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloElemento.css">
        <script src="${pageContext.request.contextPath}/js/simuladorQR.js"></script>
    </head>
    <body>
        <h2>Registrar Elemento Tecnológico</h2>

        <form action="${pageContext.request.contextPath}/ElementoServlet" method="post">
            <input type="hidden" name="accion" value="registrarTecnologico">

            <label>Nombre del elemento:</label><br>
            <input type="text" name="nombre" required><br><br>

            <label>Estado:</label><br>
            <select name="estado" required>
                <option value="">--Seleccione--</option>
                <option value="Bueno">Bueno</option>
                <option value="En reparación">En reparación</option>
                <option value="Dañado">Dañado</option>
            </select><br><br>

            <label>Identificador único:</label><br>
            <input type="text" name="identificadorUnico" id="identificadorUnico" required>
            <button type="button" onclick="simularQR()">📷</button><br><br>

            <label>Tipo de identificador:</label><br>
            <select name="tipoIdentificador" required>
                <option value="">--Seleccione--</option>
                <option value="Registro Municipal">Registro Municipal</option>
                <option value="Codigo Qr">Código QR</option>
            </select><br><br>

            <label>Marca:</label><br>
            <input type="text" name="marca" required><br><br>

            <label>Serie:</label><br>
            <input type="text" name="serie"><br><br>

            <label>ID del Aula:</label><br>
            <input type="number" name="idAula" required><br><br>

            <label>Cédula del usuario que registra:</label><br>
            <input type="text" name="cedulaUsuario" required><br><br>

            <button type="submit">Registrar</button>
        </form>

        <br>
        <form action="${pageContext.request.contextPath}/Vistas/Elemento/menuElemento.jsp" method="get">
            <button type="submit">Volver al Menú</button>
        </form>
    </body>
</html>
