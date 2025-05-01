<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.inventario.modelo.ElementoTecnologico, com.inventario.modelo.ElementosMobiliarios" %>
<%
    Object elemento = request.getAttribute("elemento");
    String tipo = (String) request.getAttribute("tipo"); // "tecnologico" o "mobiliario"
%>
<html>
    <head>
        <title>Actualizar Elemento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/actualizarElemento.css">
    </head>
    <body>
        <h2>Actualizar Elemento <%= (tipo != null) ? "(" + tipo + ")" : ""%></h2>

        <form action="${pageContext.request.contextPath}/ElementoServlet" method="post">
            <!-- Datos ocultos necesarios para el backend -->
            <input type="hidden" name="accion" value="actualizarGuardar">
            <input type="hidden" name="idElemento" value="<%= (tipo.equals("tecnologico")) ? ((ElementoTecnologico) elemento).getIdElemento() : ((ElementosMobiliarios) elemento).getIdElemento()%>">
            <input type="hidden" name="tipoElemento" value="<%= tipo%>">

            <!-- Campos a actualizar -->
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<%= (tipo.equals("tecnologico")) ? ((ElementoTecnologico) elemento).getNombre() : ((ElementosMobiliarios) elemento).getNombre()%>" required><br>

            <label>Cédula del Usuario que actualiza:</label>
            <input type="text" name="cedulaUsuario" required><br>

            <label>ID Aula:</label>
            <input type="number" name="aulaId" value="<%= (tipo.equals("tecnologico")) ? ((ElementoTecnologico) elemento).getAulaId() : ((ElementosMobiliarios) elemento).getAulaId()%>" required><br>

            <% if ("tecnologico".equals(tipo)) {
                    ElementoTecnologico el = (ElementoTecnologico) elemento;
            %>
            <label>Marca:</label>
            <input type="text" name="marca" value="<%= el.getMarca()%>" required><br>

            <label>Serie:</label>
            <input type="text" name="serie" value="<%= el.getSerie()%>" required><br>
            <% }%>

            <button type="submit">✅ Confirmar Actualización</button>
            <a href="${pageContext.request.contextPath}/ElementoServlet?accion=listarTodos">
                <button type="button">❌ Cancelar</button>
            </a>
        </form>
    </body>
</html>
