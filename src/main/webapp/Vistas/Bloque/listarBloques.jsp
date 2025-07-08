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

            <form action="${pageContext.request.contextPath}/BloqueServlet" method="post">
                <input type="hidden" name="accion" value="listar">
                <button type="submit">Listar Bloques</button>
            </form>

            <c:if test="${not empty listaBloques}">
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Número de Bloque</th>
                        <th>ID Sede</th>
                        <th>ID Usuario</th>
                        <th>Acciones</th>
                    </tr>
                    <c:forEach var="bloque" items="${listaBloques}">
                        <tr>
                            <td>${bloque.id}</td>
                            <td>${bloque.numeroBloque}</td>
                            <td>${bloque.sede.id}</td>
                            <td>${bloque.usuarioRegistra.idUsuario}</td>
                            <td>
                                <!-- Botón para ir a actualizarBloque.jsp con parámetros -->
                                <form action="${pageContext.request.contextPath}/Vistas/Bloque/actualizarBloque.jsp" method="get" style="display:inline;">
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
