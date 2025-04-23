<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Inicio</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bienvenida.css">
    </head>

    <body>
        <h1>Bienvenido al sistema de gestión</h1>

        <form action="Vistas/Login/login.jsp" method="get">
            <input type="submit" value="Iniciar Sesión">
        </form>

        <form action="Vistas/Login/registro.jsp" method="get">
            <input type="submit" value="Registrarse">
        </form>
    </body>
</html>

//https://github.com/Esneider-python/SGDE.git
// echo "# SGDE" >> README.md
//git init
//git add README.md
//git commit -m "first commit"
//git branch -M main
//git remote add origin https://github.com/Esneider-python/SGDE.git
//git push -u origin main