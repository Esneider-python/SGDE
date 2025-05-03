<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.inventario.modelo.HistorialMovimiento" %>
<%@ page import="java.util.List" %>
<html>
    <head>
        <title>Historial de Movimientos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosHE.css">
    </head>
    <body>
        <h2>📋 Historial de Movimientos</h2>

        <%
            List<HistorialMovimiento> historial = (List<HistorialMovimiento>) request.getAttribute("movimientos");

            if (historial == null || historial.isEmpty()) {
        %>
        <p>No se encontraron registros en el historial.</p>
        <%
        } else {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ID Elemento</th>
                    <th>Tipo</th>
                    <th>Aula Origen</th>
                    <th>Aula Destino</th>
                    <th>Usuario Movió</th>
                    <th>Fecha Movimiento</th>
                </tr>
            </thead>
            <tbody>
                <% for (HistorialMovimiento mov : historial) {%>
                <tr>
                    <td><%= mov.getIdHistorial()%></td>
                    <td><%= mov.getElementoId()%></td>
                    <td><%= mov.getTipoElemento()%></td>
                    <td><%= mov.getAulaOrigen()%></td>
                    <td><%= mov.getAulaDestino()%></td>
                    <td><%= mov.getUsuarioMovio()%></td>
                    <td><%= mov.getFechaMovimiento()%></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% }%>

        <br>
        
         <form action="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp">
            <button type="submit">Volver al menu</button>
        </form>
    </body>
</html>
