document.addEventListener("DOMContentLoaded", () => {
    const idAula = document.getElementById("idAula");
    const numAula = document.getElementById("numAula");
    const piso = document.getElementById("idPiso");
    const cedula = document.getElementById("cedula");
    const btn = document.getElementById("btnHabilitado");

    // Permitir solo números en numAula
    numAula.addEventListener("input", () => {
        numAula.value = numAula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    piso.addEventListener("input", () => {
        piso.value = piso.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    cedula.addEventListener("input", () => {
        cedula.value = cedula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });


    idAula.addEventListener("input", () => {
        idAula.value = idAula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    // Eventos para todos los demás campos
    [idAula, numAula, piso, cedula].forEach(input => {
        input.addEventListener("input", verificarCampos);
        input.addEventListener("change", verificarCampos);
    });

    function verificarCampos() {
        const camposLlenos =
                idAula.value.trim() !== "" &&
                numAula.value.trim() !== "" &&
                piso.value.trim() !== "" &&
                cedula.value.trim() !== "";

        btn.disabled = !camposLlenos;
    }

    verificarCampos(); // Verifica al cargar la página
});
