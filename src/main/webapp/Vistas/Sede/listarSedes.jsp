<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inventario.modelo.Sede" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Listar Sedes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
    </head>
    <body>
        <h1>Listado de Sedes</h1>

        <%
            List<Sede> listaSedes = (List<Sede>) request.getAttribute("listaSedes");

            if (listaSedes != null && !listaSedes.isEmpty()) {
        %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nombre Sede</th>
                <th>ID Colegio</th>
                <th>ID Usuario</th>
            </tr>
            <%
                for (Sede sede : listaSedes) {
            %>
            <tr>
                <td><%= sede.getId()%></td>
                <td><%= sede.getNombre()%></td>
                <td><%= sede.getColegio().getId()%></td>
                <td><%= sede.getUsuarioRegistra().getIdUsuario()%></td>
            </tr>
            <% } %>
        </table>
        <%
        } else {
        %>
        <p>No hay sedes disponibles.</p>
        <%
            }
        %>

        <br>
        <form action="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp">
            <button type="submit">Volver al Menú</button>
        </form>
    </body>
</html>
