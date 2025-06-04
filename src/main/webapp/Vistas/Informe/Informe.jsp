<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Informes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloInforme.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/validaciones.css">

    </head>
    <body>
        <div class="container">
            <h1>Generar Informes</h1>
            <c:if test="${not empty error}">
                <div id="mensaje-error" style="color: red; margin-bottom: 15px;">
                    ${error}
                </div>
            </c:if>


            <form action="${pageContext.request.contextPath}/InformeServlet" method="post">
                <div class="form-group">
                    <label for="cedulaUsuario">Cédula del Usuario:</label>
                    <input type="text" id="cedulaUsuario" name="cedulaUsuario" required>
                </div>

                <div class="form-group">
                    <label for="fechaInicio">Fecha de Inicio:</label>
                    <input type="date" id="fechaInicio" name="fechaInicio" required>
                </div>

                <div class="form-group">
                    <label for="fechaFin">Fecha de Fin:</label>
                    <input type="date" id="fechaFin" name="fechaFin" required>
                </div>

                <div class="button-group">
                    <button type="submit" id="btnHabilitado1" name="tipoInforme" value="anual_aula">
                        Informe Anual de Artículos Vigentes en Aula
                    </button>
                    <button type="submit" id="btnHabilitado2" name="tipoInforme" value="anual_eliminados" disabled>
                        Informe Anual de Artículos Eliminados
                    </button>
                    <button type="submit" id="btnHabilitado3" name="tipoInforme" value="general_sede" disabled>
                        Informe General de Artículos en la Sede
                    </button>
                </div>
            </form>

            <br><br>

            <form action="${pageContext.request.contextPath}/Vistas/Informe/menuInforme.jsp">
                <button type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
    <script src="${pageContext.request.contextPath}/js/Validaciones/Informe/Informe.js"></script> 
</html>
