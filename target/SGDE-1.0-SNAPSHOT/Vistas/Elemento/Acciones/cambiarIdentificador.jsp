<%@page import="com.inventario.modelo.ElementosMobiliarios"%>
<%@page import="com.inventario.modelo.ElementoTecnologico"%>
<%@page import="com.inventario.modelo.ElementoTecnologico" %>
<%@page import="com.inventario.modelo.ElementosMobiliarios" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object elementoObj = request.getAttribute("elemento");
    String tipoElemento = (String) request.getAttribute("tipoElemento");
    String cedulaUsuario = (String) request.getAttribute("cedulaUsuario");

    String identificadorActual = "";
    int idElemento = 0;

    if (elementoObj != null && tipoElemento != null) {
        if ("tecnologico".equals(tipoElemento)) {
            ElementoTecnologico elemento = (ElementoTecnologico) elementoObj;
            identificadorActual = elemento.getIdentificadorUnico();
            idElemento = elemento.getIdElemento();

        } else {
            ElementosMobiliarios elemento = (ElementosMobiliarios) elementoObj;
            identificadorActual = elemento.getIdentificadorUnico();
            idElemento = elemento.getIdElemento();

        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Cambiar Identificador</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloCambiarId.css">
    </head>
    <body>
        <h2>Cambiar Identificador del Elemento</h2>
        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
        <div style="padding: 10px; border: 1px solid <%= mensaje.contains("Error") ? "red" : "green"%>; color: <%= mensaje.contains("Error") ? "red" : "green"%>;">
            <%= mensaje%>
        </div>
        <%
            }
        %>


        <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post">
            <input type="hidden" name="accion" value="cambiarIdentificador">
            <input type="hidden" name="idElemento" value="<%= idElemento%>">
            <input type="hidden" name="tipoElemento" value="<%= tipoElemento%>">
            <input type="hidden" name="tipoIdentificadorAnterior" value="Registro Municipal">


            <!-- Campo oculto para enviar el identificador actual -->
            <input type="hidden" name="identificadorActual" value="<%= identificadorActual%>">

            <label>Identificador actual:</label>
            <input type="text" value="<%= identificadorActual%>" disabled>

            <label>Nuevo identificador:</label>
            <input type="text" name="identificadorNuevo" required>

            <label for="tipoIdentificadorNuevo">Tipo de nuevo identificador:</label>
            <select name="tipoIdentificadorNuevo" id="tipoIdentificadorNuevo" required>
                <option value="">-- Selecciona una opción --</option>
                <option value="Registro Municipal">Registro Municipal</option>
                <option value="Codigo Qr">Código QR</option>
            </select>


            <label>Usuario modifica:</label>
            <input type="text" name="cedulaUsuario" id="cedulaUsuario" required>

            <button type="submit">Actualizar Identificador</button>
        </form>

        <br>
        <a href="${pageContext.request.contextPath}/ElementoServlet?accion=listarTodos">
            <button type="button">Volver atras</button>
        </a>
    </body>
</html>
