<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Asignar Aula a Docente</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloUsuario.css">
    </head>
    <body>
        <div class="conta">
            <h2>Asignar Aula a Docente</h2>

            <%-- Mostrar mensajes de error o éxito --%>
            <c:if test="${not empty mensaje}">
                <div class="mensaje ${tipoMensaje}">
                    ${mensaje}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/AsignarDocenteAulaServlet" method="post" class="formulario">
                <input type="hidden" name="action" value="asignarDocenteAula">
                <!-- ID del usuario (oculto) -->
                <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">
                <div class="campo">
                    <div class="campo">
                        <label for="id_aula">Aula:</label>
                        <input type="text" name="id_aula" id="id_aula" required>
                    </div>
                </div>
                <div class="campo">
                    <label for="dia_semana">Día de la Semana:</label>
                    <select name="dia_semana" id="dia_semana" required>
                        <option value="">Seleccione un día</option>
                        <option value="Lunes">Lunes</option>
                        <option value="Martes">Martes</option>
                        <option value="Miércoles">Miércoles</option>
                        <option value="Jueves">Jueves</option>
                        <option value="Viernes">Viernes</option>
                        <option value="Sábado">Sábado</option>
                    </select>
                </div>
                <div class="campo">
                    <label for="hora_inicio">Hora de Inicio:</label>
                    <input type="time" name="hora_inicio" id="hora_inicio" required>
                </div>
                <div class="campo">
                    <label for="hora_fin">Hora de Fin:</label>
                    <input type="time" name="hora_fin" id="hora_fin" required>
                </div>
                <div class="botones">
                    <button class="con"  type="submit">Asignar</button>
                </div>
                <div class="botones">
                    <button class="can" type="button" onclick="window.history.back();">Cancelar Asignación</button>
                </div>
            </form>
        </div>
    </body>
</html>
