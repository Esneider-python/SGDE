<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Listado de Bloques</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h2>Listado de Bloques</h2>
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

            <c:if test="${not empty mensajeVacio}">
                <div style="color: red; font-weight: bold; margin-bottom: 10px;">
                    ${mensajeVacio}
                </div>
            </c:if>

            <c:if test="${not empty listaBloques}">
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Número de Bloque</th>
                        <th>Sede</th>
                        <th>Usuario</th>
                        <th>Acciones</th>
                    </tr>
                    <c:forEach var="bloque" items="${listaBloques}">
                        <tr>
                            <td>${bloque.id}</td>
                            <td>${bloque.numeroBloque}</td>
                            <td>${bloque.sede.nombre}</td>
                            <td>${bloque.usuarioRegistra.cedula}</td>
                            <td>
                                <!-- Botón para ir a actualizarBloque.jsp con parámetros -->
                                <form action="${pageContext.request.contextPath}/BloqueServlet" method="get" style="display:inline;">
                                    <input type="hidden" name="accion" value="showFormUpdate">
                                    <input type="hidden" name="idBloque" value="${bloque.id}">
                                    <input type="hidden" name="numeroBloque" value="${bloque.numeroBloque}">
                                    <input type="hidden" name="idSede" value="${bloque.sede.id}">
                                    
                                    <button type="submit">Actualizar</button>
                                </form>


                                <!-- Botón para ir a eliminarBloque.jsp con parámetros -->
                                <form action="${pageContext.request.contextPath}/Vistas/Bloque/eliminarBloque.jsp" method="get" style="display:inline;">
                                    <input type="hidden" name="numeroBloque" value="${bloque.numeroBloque}">
                                    <button type="submit">Eliminar</button>
                                </form>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <br>
            <form action="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp" method="get">
                <button type="submit">Volver al Menú</button>
            </form>
        </div>
    </body>
</html>
