document.addEventListener("DOMContentLoaded", () => {
    const idAula = document.getElementById("idAula");
    const cedula = document.getElementById("cedula");
    const btn = document.getElementById("btnHabilitado");

    // Permitir solo números en numAula
    idAula.addEventListener("input", () => {
        idAula.value = idAula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });
    cedula.addEventListener("input", () => {
        cedula.value = cedula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });


    // Eventos para todos los demás campos
    [idAula,cedula].forEach(input => {
        input.addEventListener("input", verificarCampos);
        input.addEventListener("change", verificarCampos);
    });

    function verificarCampos() {
        const camposLlenos =
                idAula.value.trim() !== "" &&
                cedula.value.trim() !== "";

        btn.disabled = !camposLlenos;
    }

    verificarCampos(); // Verifica al cargar la página
});
