<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Recuperar Contraseña</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <div class="contenedor">
            <h2>¿Olvidaste tu contraseña?</h2>

            <form action="${pageContext.request.contextPath}/EnviarCodigoServlet" method="post">
                <label for="correo">Ingresa tu correo electrónico:</label>
                <input type="email" name="correo" id="correo" required placeholder="Correo electrónico">

                <button type="submit">Enviar código</button>
            </form>

            <div class="mensaje">
                <c:if test="${not empty mensajeError}">
                    <p class="error">${mensajeError}</p>
                </c:if>
                <c:if test="${not empty mensajeExito}">
                    <p class="exito">${mensajeExito}</p>
                </c:if>
            </div>

            <div class="links">
                <a href="${pageContext.request.contextPath}/Vistas/Login/login.jsp">Volver al login</a>
            </div>
        </div>
    </body>
</html>
