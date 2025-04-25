document.addEventListener("DOMContentLoaded", function() {
    const formulario = document.getElementById("formularioCambio");
    const mensajeValidacion = document.getElementById("mensajeValidacion");

    formulario.addEventListener("submit", function(event) {
        const nuevaContrasena = formulario.nuevaContrasena.value;
        const confirmarContrasena = formulario.confirmarContrasena.value;

        if (nuevaContrasena !== confirmarContrasena) {
            event.preventDefault(); // Detiene el envío
            mensajeValidacion.textContent = "Las contraseñas no coinciden.";
        } else {
            mensajeValidacion.textContent = ""; // Limpia mensaje si todo va bien
        }
    });
});
