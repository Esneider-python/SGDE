<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head> 
        <title>Lista de Pisos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/roles.css">
    </head>
    <body>
        <div class="container">
            <h2>Lista de Pisos</h2>

            <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
                <input type="hidden" name="accion" value="listar">
                <button type="submit">Listar Pisos</button>
            </form>

            <c:if test="${not empty listaPisos}">
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Número de Piso</th>
                        <th>ID Bloque</th>
                        <th>ID Usuario</th>
                        <th>Acciones</th> 
                    </tr>
                    <c:forEach var="p" items="${listaPisos}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.numeroPiso}</td>
                            <td>${p.bloque.id}</td>
                            <td>${p.usuarioRegistra.idUsuario}</td>
                            <<td>
                                <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
                                    <input type="hidden" name="accion" value="mostrarFormularioActualizar">
                                    <input type="hidden" name="id_piso" value="${p.id}">
                                    <button type="submit">Actualizar</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/PisoServlet" method="post">
                                    <input type="hidden" name="accion" value="mostrarFormularioEliminar">
                                    <input type="hidden" name="id_piso" value="${p.id}">
                                    <button type="submit">Eliminar</button>
                                </form></td>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <form action="${pageContext.request.contextPath}/Vistas/Piso/menuPiso.jsp">
                <button type="submit">Volver al Menú Piso</button>
            </form>
        </div>
    </body>
</html>
