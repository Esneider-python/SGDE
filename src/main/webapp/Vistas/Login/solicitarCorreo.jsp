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

            <form action="${pageContext.request.contextPath}/RecuperarContrasenaServlet" method="post">
                <label for="correo">Ingresa tu correo electrónico:</label>
                <input type="hidden" name="action" value="enviarCodigo">
                <input type="email" name="correo" id="correo" required placeholder="Correo electrónico">
                <button type="submit">Enviar código</button>
            </form>
            <div>
                <%
                    String mensajeExito = (String) session.getAttribute("mensajeExito");
                    if (mensajeExito != null) {
                %>
                <div style="color:green; font-size:18px; margin-bottom:200px;  font-family:Arial, sans-serif;"><%= mensajeExito%></div>
                <%
                        session.removeAttribute("mensajeExito"); // para que solo se muestre una vez
                    }
                %>
            </div>

            <div>
                <%
                    String mensajeError = (String) session.getAttribute("mensajeError");
                    if (mensajeError != null) {
                %>
                <div style="color:red; font-size:18px; margin-bottom:200px;  font-family:Arial, sans-serif;"><%= mensajeError%></div>
                <%
                        session.removeAttribute("mensajeError"); // mostrar una vez
                    }
                %>
            </div>

            <div class="mensaje">
                <c:if test="${not empty mensajeError}">
                    <p class="error">${mensajeError}</p>
                </c:if>
                <c:if test="${not empty mensajeExito}">
                    <p class="exito">${mensajeExito}</p>
                </c:if>
            </div>

            <div class="volver-menu">
                <form action="${pageContext.request.contextPath}/Vistas/Login/login.jsp" method="get">
                    <button type="submit"> Cancelar</button>
                </form>
            </div>

        </div>
    </body>
</html>
