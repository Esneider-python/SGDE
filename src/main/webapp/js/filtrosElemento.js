document.addEventListener("DOMContentLoaded", function () {
    const tarjetas = document.querySelectorAll(".tarjeta");

    const filtroId = document.getElementById("filtroId");
    const filtroTipo = document.getElementById("filtroTipo");
    const filtroUsuario = document.getElementById("filtroUsuario");

    function filtrar() {
        const id = filtroId.value.toLowerCase();
        const tipo = filtroTipo.value.toLowerCase();
        const usuario = filtroUsuario.value.toLowerCase();

        tarjetas.forEach(tarjeta => {
            const matchId = tarjeta.dataset.id.toLowerCase().includes(id);
            const matchTipo = tarjeta.dataset.tipo.toLowerCase().includes(tipo);
            const matchUsuario = tarjeta.dataset.usuario.toLowerCase().includes(usuario);

            if (matchId && matchTipo && matchUsuario) {
                tarjeta.style.display = "block";
            } else {
                tarjeta.style.display = "none";
            }
        });
    }

    filtroId.addEventListener("input", filtrar);
    filtroTipo.addEventListener("input", filtrar);
    filtroUsuario.addEventListener("input", filtrar);
});
