<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Menú Principal</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilosMenuRegistros.css">
    </head>
    <body>
        <h1>Menú de Registros</h1>
        <div class="menu-container">
            <a href="${pageContext.request.contextPath}/Vistas/Colegio/menuColegios.jsp" class="menu-btn">Gestion Colegios</a>
            <a href="${pageContext.request.contextPath}/Vistas/Sede/menuSede.jsp" class="menu-btn"> Gestion Sedes</a>
            <a href="${pageContext.request.contextPath}/Vistas/Bloque/menuBloque.jsp" class="menu-btn">Gestion Bloques</a>
            <a href="${pageContext.request.contextPath}/Vistas/Piso/menuPiso.jsp" class="menu-btn">Gestion Pisos</a>
            <a href="crearAula.jsp" class="menu-btn">Gestion Aulas</a>
            <div class="volver-menu">
                <br>
                <a href="${pageContext.request.contextPath}/Vistas/MenuPrincipal/menuPrincipal.jsp" class="menu-btn">🏠Area principal</a>
      
            </div>
        </div>

    </body>
</html>
