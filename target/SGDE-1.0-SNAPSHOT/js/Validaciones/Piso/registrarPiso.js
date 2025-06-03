document.addEventListener("DOMContentLoaded", () => {
    const numPiso = document.getElementById("numPiso");
    const idBloque = document.getElementById("idBloque");
    const cedula = document.getElementById("cedula");
    const btn = document.getElementById("btnHabilitado");

    // Permitir solo números en piso
    numPiso.addEventListener("input", () => {
        numPiso.value = numPiso.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    idBloque.addEventListener("input", () => {
        idBloque.value = idBloque.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });

    cedula.addEventListener("input", () => {
        cedula.value = cedula.value.replace(/\D/g, ""); // Borra todo lo que no sea dígito
        verificarCampos();
    });


    // Eventos para todos los demás campos
    [numPiso, idBloque,cedula].forEach(input => {
        input.addEventListener("input", verificarCampos);
        input.addEventListener("change", verificarCampos);
    });

    function verificarCampos() {
        const camposLlenos =
                numPiso.value.trim() !== "" &&
                idBloque.value.trim() !== "" &&
                cedula.value.trim() !== "";

        btn.disabled = !camposLlenos;
    }

    verificarCampos(); // Verifica al cargar la página
});
