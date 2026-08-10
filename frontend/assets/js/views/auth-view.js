import { escaparHtml, porId } from "../core/dom.js";

export function renderizarUsuariosLogin(usuarios) {
    const select = porId("usuarioExistente");
    if (!select) return;
    select.innerHTML = '<option value="">Selecione seu nome</option>' + usuarios
        .map(usuario => `<option value="${escaparHtml(usuario.matricula)}">${escaparHtml(usuario.nome)} — matrícula ${escaparHtml(usuario.matricula)}</option>`)
        .join("");
}

export function alterarModoAuth(modo) {
    const entrar = modo === "entrar";
    porId("formEntrar")?.classList.toggle("d-none", !entrar);
    porId("formCriarConta")?.classList.toggle("d-none", entrar);
    document.querySelectorAll(".auth-tab").forEach(botao => {
        botao.classList.toggle("active", botao.dataset.authMode === modo);
    });
}
