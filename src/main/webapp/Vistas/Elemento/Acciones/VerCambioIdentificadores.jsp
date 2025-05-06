<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="com.inventario.modelo.CambioIdentificador"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Historial de Cambios de Identificadores</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloHistorial.css">
    </head>
    <body>

        <h2>Historial de Cambios de Identificadores</h2>
        <%-- Mensajes de estado --%>
    <c:if test="${not empty mensajeError}">
        <div class="alert alert-danger">${mensajeError}</div>
    </c:if>
    <c:if test="${not empty mensajeAdvertencia}">
        <div class="alert alert-warning">${mensajeAdvertencia}</div>
    </c:if>
    <c:if test="${not empty mensajeExito}">
        <div class="alert alert-success">${mensajeExito}</div>
    </c:if>


    <%
        List<CambioIdentificador> historial = (List<CambioIdentificador>) request.getAttribute("historial");
        String mensaje = (String) request.getAttribute("mensaje");

        if (mensaje != null) {
    %>
    <p style="color: red;"><strong><%= mensaje%></strong></p>
            <%
                }
            %>

    <%
        if (historial != null && !historial.isEmpty()) {
    %>
    <table border="1">
        <thead>
            <tr>
                <th>ID Cambio</th>
                <th>ID Elemento</th>
                <th>Identificador Anterior</th>
                <th>Tipo Identificador Anterior</th>
                <th>Identificador Nuevo</th>
                <th>Tipo Identificador Nuevo</th>
                <th>Usuario Modificador</th>
                <th>Fecha</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (CambioIdentificador cambio : historial) {
            %>
            <tr>
                <td><%= cambio.getIdCambio()%></td>
                <td><%= cambio.getIdElemento()%></td>
                <td><%= cambio.getIdentificadorAnterior()%></td>
                <td><%= cambio.getTipoIdentificadorAnterior()%></td>
                <td><%= cambio.getIdentificadorNuevo()%></td>
                <td><%= cambio.getTipoIdentificadorNuevo()%></td>
                <td><%= cambio.getUsuarioModifica()%></td>
                <td><%= cambio.getFechaModificacion()%></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p>No hay registros de cambios de identificadores.</p>
    <%
        }
    %>

    <a href="${pageContext.request.contextPath}/Vistas/Elemento/menuElemento.jsp">
        <button>Volver a la página principal</button>
    </a>

</body>
</html>
