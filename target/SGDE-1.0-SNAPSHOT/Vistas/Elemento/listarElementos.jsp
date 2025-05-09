<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.inventario.modelo.ElementoTecnologico, com.inventario.modelo.ElementosMobiliarios" %>
<html>
    <head>
        <title>Lista de Elementos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloElemento.css">
    </head>
    <body>
        <h2>Listado de Elementos</h2>
        <% String mensaje = (String) request.getAttribute("mensaje"); %>
        <% if (mensaje != null) {%>
        <div class="alerta-exito"><%= mensaje%></div>

        <% } %>
        <!-- Mensajes -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:if test="${not empty mensaje}">
        <p class="exito">${mensaje}</p>
    </c:if>

    <div class="filtros">
        <input type="text" id="filtroId" placeholder="Buscar por ID">
        <input type="text" id="filtroTipo" placeholder="Buscar por Tipo">
        <input type="text" id="filtroUsuario" placeholder="Buscar por Usuario">
    </div>
    <form method="post" action="${pageContext.request.contextPath}/ElementoServlet">
        <input type="hidden" name="accion" value="listarTodos">
        <div style="text-align: center; margin-top: 10px;">
            <button type="submit">🔍 Listar Elementos</button>
        </div>
    </form>


    <div class="contenedor-tarjetas">
        <%
            List<Object> elementos = (List<Object>) request.getAttribute("elementos");
            if (elementos != null) {
                for (Object obj : elementos) {
                    if (obj instanceof ElementoTecnologico) {
                        ElementoTecnologico el = (ElementoTecnologico) obj;
        %>
        <div class="tarjeta tecnologico" data-id="<%=el.getIdElemento()%>" data-tipo="Tecnologico" data-usuario="<%=el.getUsuarioRegistra()%>">
            <div class="cabecera">
                👁 Elemento: <%=el.getIdElemento()%> | Tipo: (Tecnológico)
            </div>
            <div class="contenido">
                <strong>Nombre:</strong> <%=el.getNombre()%><br>
                <strong>Estado:</strong> <%=el.getEstado()%><br>
                <strong>Usuario:</strong> <%=el.getUsuarioRegistra()%><br>
                <strong>Aula:</strong> <%=el.getAulaId()%><br>
                <strong>Identificador:</strong> <%=el.getIdentificadorUnico()%><br>
                <strong>Tipo Identificador:</strong> <%=el.getTipoIdentificador()%><br>
                <strong>Fecha creación:</strong> <%=el.getFechaCreacion()%><br>
                <strong>Marca:</strong> <%=el.getMarca()%><br>
                <strong>Serie:</strong> <%=el.getSerie()%>
            </div>
            <div class="acciones">
                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="MostrarFormEliminarElemento">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="tecnologico"> <!-- o "tecnologico" si estás en el otro bloque -->
                    <button type="submit">🗑 Eliminar</button>
                </form>
                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarFormularioMover">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="tecnologico"> <!-- o "tecnologico" si estás en el otro bloque -->
                    <button type="submit">🏠 Mover</button>
                </form>
                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarFormularioCambioIdentificador">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="tecnologico">
                    <button type="submit">➕</button>
                </form>

                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarActualizar">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="tecnologico">
                    <button type="submit">✏️</button>
                </form>

                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarFormularioReporte">
                    <input type="hidden" name="elementoId" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="tecnologico"> <!-- o "mobiliario" -->
                    <button type="submit">❗</button>
                </form>

            </div>
        </div>
        <%
        } else if (obj instanceof ElementosMobiliarios) {
            ElementosMobiliarios el = (ElementosMobiliarios) obj;
        %>
        <div class="tarjeta mobiliario" data-id="<%=el.getIdElemento()%>" data-tipo="Mobiliario" data-usuario="<%=el.getUsuarioRegistra()%>">
            <div class="cabecera">
                👁 Elemento: <%=el.getIdElemento()%> | Tipo: (Mobiliario)
            </div>
            <div class="contenido">
                <strong>Nombre:</strong> <%=el.getNombre()%><br>
                <strong>Estado:</strong> <%=el.getEstado()%><br>
                <strong>Usuario:</strong> <%=el.getUsuarioRegistra()%><br>
                <strong>Aula:</strong> <%=el.getAulaId()%><br>
                <strong>Identificador:</strong> <%=el.getIdentificadorUnico()%><br>
                <strong>Tipo Identificador:</strong> <%=el.getTipoIdentificador()%><br>
                <strong>Fecha creación:</strong> <%=el.getFechaCreacion()%>
            </div>
            <div class="acciones">
                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="MostrarFormEliminarElemento">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="mobiliario"> <!-- o "tecnologico" si estás en el otro bloque -->
                    <button type="submit">🗑 Eliminar</button>
                </form>
                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarFormularioMover">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="mobiliario"> <!-- o "tecnologico" si estás en el otro bloque -->
                    <button type="submit">🏠 Mover</button>
                </form>

                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarFormularioCambioIdentificador">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="mobiliario">
                    <button type="submit">➕</button>
                </form>

                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarActualizar">
                    <input type="hidden" name="idElemento" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="mobiliario"> <!-- o "mobiliario" -->
                    <button type="submit">✏️</button>
                </form>
                <form class="item" action="${pageContext.request.contextPath}/ElementoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="mostrarFormularioReporte">
                    <input type="hidden" name="elementoId" value="<%=el.getIdElemento()%>">
                    <input type="hidden" name="tipoElemento" value="mobiliario"> <!-- o "mobiliario" -->
                    <button type="submit">❗</button>
                </form>

            </div>
        </div>
        <%
                    }
                }
            }
        %>
    </div>
    <form action="${pageContext.request.contextPath}/Vistas/Elemento/menuElemento.jsp" method="get">
        <button type="submit">Volver al Menú</button>
    </form>

    <script src="${pageContext.request.contextPath}/js/filtrosElemento.js"></script>
</body>
</html>
