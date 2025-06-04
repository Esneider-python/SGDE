document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        var mensaje = document.getElementById("mensaje-error");
        if (mensaje) {
            mensaje.remove();
        }
    }, 3000); // 30 segundos


    const fFin = document.getElementById("fechaFin");
    const fInicio = document.getElementById("fechaInicio");
    const cedula = document.getElementById("cedulaUsuario");
    const btn1 = document.getElementById("btnHabilitado1");
    const btn2 = document.getElementById("btnHabilitado2");
    const btn3 = document.getElementById("btnHabilitado3");
    // Permitir solo números en numAula
    cedula.addEventListener("input", () => {
        cedula.valfue = cedula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });
    // Eventos para todos los demás campos
    [fInicio, fFin, cedula].forEach(input => {
        input.addEventListener("input", verificarCampos);
        input.addEventListener("change", verificarCampos);
    });
    function verificarCampos() {
        const camposLlenos =
                fInicio.value.trim() !== "" &&
                fFin.value.trim() !== "" &&
                cedula.value.trim() !== "";
        btn1.disabled = !camposLlenos;
        btn2.disabled = !camposLlenos;
        btn3.disabled = !camposLlenos;
    }

    verificarCampos(); // Verifica al carga


});
