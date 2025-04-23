<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h4>Registrar Usuario</h4>
        <form action="UsuarioServlet" method="post">
            <div class="input-field">
                <input id="nombre" name="nombre" type="text" required>
                <label for="nombre">Nombres</label>
            </div>
            <div class="input-field">
                <input id="apellidos" name="apellidos" type="text" required>
                <label for="apellidos">Apellidos</label>
            </div>
            <div class="input-field">
                <input id="telefono" name="telefono" type="text" required>
                <label for="telefono">Teléfono</label>
            </div>
            <div class="input-field">
                <input id="correo" name="correo" type="email" required>
                <label for="correo">Correo</label>
            </div>
            <div class="input-field">
                <input id="cedula" name="cedula" type="number" required>
                <label for="cedula">Cédula</label>
            </div>
            <div class="input-field">
                <input id="contrasena" name="contrasena" type="password" required>
                <label for="contrasena">Contraseña</label>
            </div>
            <div class="input-field">
                <input id="rol" name="rol" type="number" required>
                <label for="rol">Rol</label>
            </div>
            <div class="center">
                <button class="btn waves-effect waves-light" type="submit">Registrar</button>
            </div>
        </form>
    </div>

    <!-- Materialize JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>
