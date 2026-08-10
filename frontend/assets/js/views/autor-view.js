import { escaparHtml, formatarData, porId } from "../core/dom.js";

export function renderizarAutores(autores) {
    const lista = porId("listaAutores");
    if (!lista) return;
    lista.innerHTML = autores.map(autor => {
        const total = Number(autor.quantidadeLivros ?? 0);
        return `<div class="col-xl-3 col-lg-4 col-md-6 mb-4"><article class="book-card h-100"><div class="author-avatar"><i class="bi bi-person-circle"></i></div><div class="book-info p-3 d-flex flex-column h-100"><small class="text-secondary">Autor #${escaparHtml(autor.id)}</small><h5 class="text-white fw-bold mb-1">${escaparHtml(autor.nome)}</h5><span>${escaparHtml(autor.nacionalidade || "Nacionalidade não informada")}</span><p class="text-secondary small flex-grow-1">${escaparHtml(autor.biografia || "Biografia não informada.")}</p><small class="d-block text-secondary">Nascimento: ${formatarData(autor.dataNascimento)}</small><small class="d-block text-warning mt-1"><i class="bi bi-book"></i> ${total} ${total === 1 ? "livro" : "livros"} no acervo</small></div></article></div>`;
    }).join("");
}
