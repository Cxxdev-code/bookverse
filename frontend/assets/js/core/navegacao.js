import { ehAdministrador, limparSessao, obterUsuarioAtivo } from "./session.js";

function configurarBusca(pagina) {
    if (pagina === "livros") return;
    document.querySelectorAll(".navbar .search-box").forEach(formulario => {
        formulario.addEventListener("submit", evento => {
            evento.preventDefault();
            const termo = formulario.querySelector("input")?.value.trim();
            window.location.href = `livros.html${termo ? `?busca=${encodeURIComponent(termo)}` : ""}`;
        });
    });
}

function atualizarItensDeAcesso() {
    const usuario = obterUsuarioAtivo();
    const admin = ehAdministrador();
    [document.documentElement, document.body].forEach(elemento => {
        elemento.classList.toggle("navbar-usuario", Boolean(usuario) && !admin);
        elemento.classList.toggle("navbar-administrador", admin);
    });
    document.querySelectorAll('a[href="adicionar.html"], a[href^="adicionar.html?"]').forEach(link => {
        const item = link.closest(".nav-item") || link;
        item.classList.toggle("d-none", !admin);
    });
    document.querySelectorAll(".admin-only").forEach(elemento => elemento.classList.toggle("d-none", !admin));

    document.querySelectorAll(".navbar-nav").forEach(lista => {
        if (!admin || lista.querySelector('[href="historico.html"]')) return;
        lista.insertAdjacentHTML("beforeend", '<li class="nav-item"><a class="nav-link" href="historico.html">Usuários</a></li>');
    });

    document.querySelectorAll(".nav-login-btn").forEach(link => {
        if (!usuario) return;
        link.innerHTML = '<i class="bi bi-box-arrow-right" aria-hidden="true"></i> Sair';
        link.href = "login.html";
        link.addEventListener("click", evento => {
            evento.preventDefault();
            limparSessao();
            window.location.href = "login.html";
        }, { once: true });
    });
}

export function iniciarNavegacao(pagina) {
    atualizarItensDeAcesso();
    configurarBusca(pagina);
}
