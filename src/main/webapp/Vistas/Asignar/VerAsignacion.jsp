<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inventario.modelo.DocenteAula" %>
<%@ page import="com.mycompany.sgde.dao.DocenteAulaDao" %>
<%@ page import="com.mycompany.sgde.util.Conexion" %>
<%
    String mensaje = (String) request.getAttribute("mensaje");
    String tipoMensaje = (String) request.getAttribute("tipoMensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Asignaciones por Usuario</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/asignacion.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    </head>
    <body>
        <div class="container-asignaciones">
            <h2>Ver Asignaciones de Docente</h2>

            <%
                if (mensaje != null) {
            %>
            <div class="<%=tipoMensaje.equals("exito") ? "mensaje-exito" : "mensaje-error"%>">
                <%= mensaje%>
            </div>
            <%
                }

                List<DocenteAula> asignaciones = (List<DocenteAula>) request.getAttribute("asignaciones");
                if (asignaciones != null && !asignaciones.isEmpty()) {
            %>
            <table class="table-asignaciones">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>ID Usuario</th>
                        <th>ID Aula</th>
                        <th>Día de la Semana</th>
                        <th>Hora Inicio</th>
                        <th>Hora Fin</th>
                        <th>accion</th>

                    </tr>
                </thead>
                <tbody>
                    <%
                        for (DocenteAula asignacion : asignaciones) {
                    %>
                    <tr>
                        <td><%= asignacion.getId()%></td>
                        <td><%= asignacion.getIdUsuario()%></td>
                        <td><%= asignacion.getIdAula()%></td>
                        <td><%= asignacion.getDia()%></td>
                        <td><%= asignacion.getHoraInicio()%></td>
                        <td><%= asignacion.getHoraFin()%></td>
                        <td> <!-- comment -->
                            <form action="<%=request.getContextPath()%>/AsignarDocenteAulaServlet" method="post";">
                                <input type="hidden" name="idAsignacion" value="<%= asignacion.getId()%>">
                                <input type="hidden" name="action" value="quitarAsignacion">
                                <button class="btn-quitar" type="submit" class="btn-quitar"> <i class="fa fa-user-slash"></i>Quitar</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
            } else if (request.getParameter("idUsuario") != null) {
            %>
            <p>No se encontraron asignaciones para el usuario especificado.</p>
            <%
                }
            %>

           <a href="${pageContext.request.contextPath}/UsuarioServlet?action=listarUsuarios" class="btn-volver">Volver a Usuarios</a>

        </div>
    </body>

</html>
