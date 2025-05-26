document.addEventListener("DOMContentLoaded", () => {
    const numAula = document.getElementById("numAula");
    const dia = document.getElementById("diaSemana");
    const horaInicio = document.getElementById("horaInicio");
    const horaFin = document.getElementById("horaFin");
    const btnAsignar = document.getElementById("btnAsignar");

    // Permitir solo números en numAula
    numAula.addEventListener("input", () => {
        numAula.value = numAula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    // Eventos para todos los demás campos
    [ numAula, dia, horaInicio, horaFin].forEach(input => {
        input.addEventListener("input", verificarCampos);
        input.addEventListener("change", verificarCampos);
    });

    function verificarCampos() {
        const camposLlenos =
                numAula.value.trim() !== "" &&
                dia.value.trim() !== "" &&
                horaInicio.value.trim() !== "" &&
                horaFin.value.trim() !== "";

        btnAsignar.disabled = !camposLlenos;
    }

    verificarCampos(); // Verifica al cargar la página
});
