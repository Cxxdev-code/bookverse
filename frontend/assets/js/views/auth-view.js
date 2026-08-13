import { porId } from "../core/dom.js";

export function alterarModoAuth(modo) {
    const entrar = modo === "entrar";
    porId("formEntrar")?.classList.toggle("d-none", !entrar);
    porId("formCriarConta")?.classList.toggle("d-none", entrar);
    document.querySelectorAll(".auth-tab").forEach(botao => {
        botao.classList.toggle("active", botao.dataset.authMode === modo);
    });
}
