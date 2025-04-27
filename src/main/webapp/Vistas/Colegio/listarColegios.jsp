<%@ page import="com.inventario.modelo.Colegio" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Colegios</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosColegiosGeneral.css">
</head>
<body>

<h1>Listado de Colegios Registrados</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Nombre del Colegio</th>
        <th>ID Usuario Registra</th>
    </tr>

<%
    List<Colegio> colegios = (List<Colegio>) request.getAttribute("colegios");
    if (colegios != null && !colegios.isEmpty()) {
        for (Colegio colegio : colegios) {
%>
    <tr>
        <td><%= colegio.getId() %></td>
        <td><%= colegio.getNombre() %></td>
        <td><%= colegio.getUsuarioRegistra().getIdUsuario() %></td>
    </tr>
<%
        }
    } else {
%>
    <tr>
        <td colspan="3">No hay colegios registrados.</td>
    </tr>
<%
    }
%>

</table>

<br>

<form action="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" method="get">
    <button type="submit" class="boton">Volver al Menú</button>
</form>

</body>
</html>
