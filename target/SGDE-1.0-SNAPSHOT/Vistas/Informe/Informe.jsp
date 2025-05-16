<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Informes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInforme.css">
    </head>
    <body>
        <div class="container">
            <h1>Generar Informes</h1>
            <form action="${pageContext.request.contextPath}/InformeServlet" method="post">
                <div class="form-group">
                    <label for="cedulaUsuario">C�dula del Usuario:</label>
                    <input type="text" id="cedulaUsuario" name="cedulaUsuario" required>
                </div>
                <div class="button-group">
                    <button type="submit" name="tipoInforme" value="anual_aula">Informe Anual de Art�culos Vigentes en Aula</button>
                    <button type="submit" name="tipoInforme" value="anual_eliminados">Informe Anual de Art�culos Eliminados</button>
                    <button type="submit" name="tipoInforme" value="general_sede">Informe General de Art�culos en la Sede</button>
                </div>

            </form>
            <br>
            <br><!-- comment -->
            <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp">
                <button type="submit">Volver al menu</button>
            </form>
        </div>
    </body>
</html>
