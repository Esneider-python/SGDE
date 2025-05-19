<%@page import="com.inventario.modelo.Usuario"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Gestión de Usuarios</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloUsuario.css" />
        <!-- Opcional: importar iconos de Font Awesome para mejor UI -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    </head>
    <body>
        <div class="container">
            <h1>Gestión de Usuarios</h1>
            <div class="card-container">
                <%
                    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
                    if (usuarios != null && !usuarios.isEmpty()) {
                        for (Usuario usuario : usuarios) {
                %>
                <div class="card">
                    <h2>Usuario ID: <%= usuario.getIdUsuario()%></h2>
                    <p><strong>Nombre:</strong> <%= usuario.getNombres()%></p>
                    <p><strong>Cédula:</strong> <%= usuario.getCedula()%></p>
                    <div class="button-group">

                        <form action="${pageContext.request.contextPath}/UsuarioServlet" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="mostrarFormularioActualizar">
                            <input type="hidden" name="id" value="<%= usuario.getIdUsuario()%>">
                            <button class="confirmar color-verde" type="submit" class="btn btn-edit" title="Editar">
                                <i class="fa fa-pen-to-square"></i> Editar
                            </button>
                        </form>

                        <form action="${pageContext.request.contextPath}/UsuarioServlet" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="eliminarUsuario">
                            <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario()%>">
                            <button class="confirmar color-rojo" type="submit" class="btn btn-delete" title="Eliminar">
                                <i class="fa fa-trash"></i> Eliminar
                            </button>
                        </form>

                        <form action="${pageContext.request.contextPath}/AsignarDocenteAulaServlet" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="formularioAsignar">
                            <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario()%>">
                            <button class="confirmar color-naranja" type="submit" class="btn btn-asignar" title="asignar">
                                <i class="fa fa-chalkboard-user"></i> Asignar aula
                            </button>
                        </form>

                         <form action="${pageContext.request.contextPath}/AsignarDocenteAulaServlet" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="verAsignaciones">
                            <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario()%>">
                            <button class="confirmar color-amarillo" type="submit" class="btn btn-asignar" title="asignar">
                                <i class="fa fa-eye"></i> Ver asignaciones
                            </button>
                        </form>    

                    </div>

                </div>
                <%
                    }
                } else {
                %>
                <p>No hay usuarios registrados.</p>
                <% }%>
            </div>
            <form action="${pageContext.request.contextPath}/Vistas/Usuario/menuUsuario.jsp" method="get">
                <button class="btn-salir" type="submit">Ir menu usuario</button>
            </form>
        </div>
    </body>
</html>
