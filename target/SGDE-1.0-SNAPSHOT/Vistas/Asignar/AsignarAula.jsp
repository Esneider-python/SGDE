<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asignar Aulas a Docente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAsignar.css">
</head>
<body>
    <div class="container">
        <h1>Asignar Aulas a Docente</h1>
        <form action="${pageContext.request.contextPath}/DocenteAulaServlet" method="post">
            <div class="form-group">
                <label for="cedulaUsuario">Cédula del Docente:</label>
                <input type="text" id="cedulaUsuario" name="cedulaUsuario" required>
            </div>
            <div class="form-group">
                <label for="numAula">Numero del Aula:</label>
                <input type="text" id="numAula" name="numAula" required>
            </div>
            <div class="form-group">
                <label for="diaSemana">Día de la Semana:</label>
                <select id="diaSemana" name="diaSemana" required>
                    <option value="Lunes">Lunes</option>
                    <option value="Martes">Martes</option>
                    <option value="Miércoles">Miércoles</option>
                    <option value="Jueves">Jueves</option>
                    <option value="Viernes">Viernes</option>
                    <option value="Sábado">Sábado</option>
                </select>
            </div>
            <div class="form-group">
                <label for="horaInicio">Hora de Inicio:</label>
                <input type="time" id="horaInicio" name="horaInicio" required>
            </div>
            <div class="form-group">
                <label for="horaFin">Hora de Fin:</label>
                <input type="time" id="horaFin" name="horaFin" required>
            </div>
            <button type="submit">Asignar Aula</button>
        </form>
    </div>
</body>
</html>