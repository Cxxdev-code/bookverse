export function iniciarNavegacao(pagina) {
    if (pagina === "livros") return;

    document.querySelectorAll(".navbar .search-box").forEach(formulario => {
        formulario.addEventListener("submit", evento => {
            evento.preventDefault();
            const termo = formulario.querySelector("input")?.value.trim();
            const sufixo = termo ? `?busca=${encodeURIComponent(termo)}` : "";
            window.location.href = `livros.html${sufixo}`;
        });
    });
}
