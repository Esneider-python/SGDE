document.addEventListener("DOMContentLoaded", () => {
    const idPiso = document.getElementById("idPiso");
    const numAula = document.getElementById("numAula");
    const cedula = document.getElementById("cedula");
    const btn = document.getElementById("btnHabilitado");

    // Permitir solo números en numAula
    numAula.addEventListener("input", () => {
        numAula.value = numAula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    idPiso.addEventListener("input", () => {
        piso.value = piso.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    cedula.addEventListener("input", () => {
        cedula.value = cedula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });


    // Eventos para todos los demás campos
    [idPiso, numAula,cedula].forEach(input => {
        input.addEventListener("input", verificarCampos);
        input.addEventListener("change", verificarCampos);
    });

    function verificarCampos() {
        const camposLlenos =
                idPiso.value.trim() !== "" &&
                numAula.value.trim() !== "" &&
                cedula.value.trim() !== "";

        btn.disabled = !camposLlenos;
    }

    verificarCampos(); // Verifica al cargar la página
});
