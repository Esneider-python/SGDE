<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="paquete.modelo.Sede" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Listar Sedes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosSedes.css">
    </head>
    <body>
        <div class="conta">
            <h1>Listado de Sedes</h1>

            <!-- Mensaje de éxito -->
            <c:if test="${not empty mensaje}">
                <div style="color: green; font-weight: bold; margin-bottom: 10px;">
                    ${mensaje}
                </div>
            </c:if>

            <!-- Mensaje de error -->
            <c:if test="${not empty error}">
                <div style="color: red; font-weight: bold; margin-bottom: 10px;">
                    ${error}
                </div>
            </c:if>

            <br>

            <%
                List<Sede> listaSedes = (List<Sede>) request.getAttribute("listaSedes");
                if (listaSedes != null && !listaSedes.isEmpty()) {
            %>
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Nombre Sede</th>
                    <th>Colegio</th>
                    <th>Usuario registra</th>
                    <th>Accion</th>
                </tr>
                <%
                    for (Sede sede : listaSedes) {
                %>
                <tr>
                    <td><%= sede.getId()%></td>
                    <td><%= sede.getNombre()%></td>
                    <td><%= sede.getColegio().getNombre()%></td>
                    <td><%= sede.getUsuarioRegistra().getCedula()%></td>
                    <td>
                        <!-- Botón Actualizar -->
                        <form action="${pageContext.request.contextPath}/SedeServlet" method="post">
                            <input type="hidden" name="accion" value="cargarFormularioActualizarSede">
                            <input type="hidden" name="id_sede" value="<%= sede.getId()%>">
                            <button id="azul" type="submit">Actualizar</button>
                        </form>

                        <!-- Botón Eliminar -->
                        <form action="${pageContext.request.contextPath}/SedeServlet" method="post">
                            <input type="hidden" name="accion" value="cargarFormularioEliminarSede">
                            <input type="hidden" name="id_sede" value="<%= sede.getId()%>">
                            <button id="rojo" type="submit">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </table>
            <%
            } else {
            %>
            <p>No hay sedes disponibles.</p>
            <% }%>

            <br>
            <form action="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp">
                <button type="submit">Volver al Menú</button>
            </form>
        </div>
    </body>
</html>
