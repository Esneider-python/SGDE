<%@page import="com.inventario.modelo.DocenteAula"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ver Asignaciones de Aulas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloAsignar.css">
</head>
<body>
    <div class="container">
        <h1>Asignaciones de Aulas</h1>
        <table>
            <thead>
                <tr>
                    <th>ID Asignación</th>
                    <th>ID Usuario</th>
                    <th>ID Aula</th>
                    <th>Día</th>
                    <th>Hora Inicio</th>
                    <th>Hora Fin</th>
                </tr>
            </thead>
            <tbody>
                <%-- Aquí se deben insertar las filas de asignaciones desde el servlet --%>
                <% 
                List<DocenteAula> asignaciones = (List<DocenteAula>) request.getAttribute("asignaciones");
                if (asignaciones != null) {
                    for (DocenteAula asignacion : asignaciones) {
                %>
                <tr>
                    <td><%= asignacion.getId() %></td>
                    <td><%= asignacion.getIdUsuario() %></td>
                    <td><%= asignacion.getIdAula() %></td>
                    <td><%= asignacion.getDiaSemana() %></td>
                    <td><%= asignacion.getHoraInicio() %></td>
                    <td><%= asignacion.getHoraFin() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6">No hay asignaciones registradas.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
