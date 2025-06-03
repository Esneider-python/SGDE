document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        var mensaje = document.getElementById("mensaje-error");
        if (mensaje) {
            mensaje.remove();
        }
    }, 3000); // 30 segundos
});
