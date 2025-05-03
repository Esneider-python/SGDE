<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Mover Elemento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloElemento.css">
    </head>
    <body>
        <div class="contenedor">
            <h2>Mover Elemento a otra Aula</h2>
            <%
                String numeroAula = (String) request.getAttribute("numeroAula");
                String cedulaUsuario = (String) request.getAttribute("cedulaUsuario");
            %>

            <% if (numeroAula != null && cedulaUsuario != null) {%>
            <div class="alert alert-success">
                <p><strong>Movimiento realizado con los siguientes datos:</strong></p>
                <ul>
                    <li>Número de Aula Destino: <%= numeroAula%></li>
                    <li>Cédula del Usuario: <%= cedulaUsuario%></li>
                </ul>
            </div>
            <% }%>

            <!-- Mensajes -->
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <c:if test="${not empty mensaje}">
                <p class="exito">${mensaje}</p>
            </c:if>

            <!-- Formulario -->
            <form action="${pageContext.request.contextPath}/ElementoServlet" method="post">
                <input type="hidden" name="accion" value="moverElemento" />
                <input type="hidden" name="idElemento" value="${param.idElemento}" />
                <input type="hidden" name="tipoElemento" value="${param.tipoElemento}" />

                <div class="campo">
                    <label for="nuevaAula">Número de Aula Destino:</label>
                    <input type="number" name="idAulaDestino" id="nuevaAula" required min="1" />
                </div>

                <div class="campo">
                    <label for="cedulaUsuario">Cédula del Usuario que realiza el movimiento:</label>
                    <input type="text" name="cedulaUsuario" id="cedulaUsuario" required maxlength="15" />
                </div>

                <div class="botones">
                    <button type="submit">Mover Elemento</button>
                    <a href="${pageContext.request.contextPath}/Vistas/Elemento/menuElemento.jsp">Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>
