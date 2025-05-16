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
                        <a href="${pageContext.request.contextPath}/UsuarioServlet?action=actualizar&id=<%= usuario.getIdUsuario()%>" class="btn btn-edit" title="Editar">
                            <i class="fa fa-pen-to-square"></i> Editar
                        </a>
                        <a href="${pageContext.request.contextPath}/UsuarioServlet?action=eliminar&id=<%= usuario.getIdUsuario()%>" class="btn btn-delete" onclick="return confirm('¿Estás seguro de eliminar este usuario?')" title="Eliminar">
                            <i class="fa fa-trash"></i> Eliminar
                        </a>
                        <a href="${pageContext.request.contextPath}/AsignarAulas.jsp?cedula=<%= usuario.getCedula()%>" class="btn btn-assign" title="Asignar Aulas">
                            <i class="fa fa-chalkboard-user"></i> Asignar Aulas
                        </a>
                        <a href="${pageContext.request.contextPath}/UsuarioServlet?action=quitarAsignaciones&id=<%= usuario.getIdUsuario()%>" class="btn btn-remove" title="Quitar Asignaciones">
                            <i class="fa fa-user-slash"></i> Quitar Asignaciones
                        </a>
                        <a href="${pageContext.request.contextPath}/VerAsignaciones.jsp?id=<%= usuario.getIdUsuario()%>" class="btn btn-view" title="Ver Asignaciones">
                            <i class="fa fa-eye"></i> Ver Asignaciones
                        </a>
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
                <button type="submit">Ir menu usuario</button>
            </form>
        </div>
    </body>
</html>
