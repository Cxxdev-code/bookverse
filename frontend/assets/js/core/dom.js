export function porId(id) {
    return document.getElementById(id);
}

export function definirTexto(id, valor) {
    const elemento = porId(id);
    if (elemento) elemento.textContent = valor;
}

export function escaparHtml(valor = "") {
    return String(valor)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

export function formatarData(data, fallback = "Não informada") {
    if (!data) return fallback;

    return new Date(`${data}T00:00:00`).toLocaleDateString("pt-BR", {
        timeZone: "UTC"
    });
}

export function mostrarMensagem(id, tipo, texto) {
    const elemento = porId(id);
    if (!elemento) return;

    const icone = tipo === "success" ? "check-circle-fill" : "exclamation-triangle-fill";
    elemento.className = `${elemento.dataset.baseClass || "form-message"} ${tipo}`;
    elemento.innerHTML = `<i class="bi bi-${icone}"></i><span>${escaparHtml(texto)}</span>`;
}

export function ocultarMensagem(id) {
    const elemento = porId(id);
    if (elemento) elemento.classList.add("d-none");
}

export function definirCarregando(botao, carregando, textoPadrao, textoCarregando = "Salvando...") {
    if (!botao) return;
    botao.disabled = carregando;
    botao.innerHTML = carregando
        ? `<span class="spinner-border spinner-border-sm" aria-hidden="true"></span> ${textoCarregando}`
        : textoPadrao;
}
