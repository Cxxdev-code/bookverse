import { escaparHtml, formatarData, porId } from "../core/dom.js";

function resumo(texto, limite = 125) {
    const valor = texto || "Descrição não informada.";
    return valor.length > limite ? `${valor.slice(0, limite).trim()}…` : valor;
}

export function renderizarLivros(livros) {
    const lista = porId("listaLivros");
    if (!lista) return;

    if (livros.length === 0) {
        lista.innerHTML = '<div class="col-12 text-center py-5"><h4 class="text-muted">Nenhum livro encontrado.</h4></div>';
        return;
    }

    lista.innerHTML = livros.map(livro => `
        <div class="col-xl-3 col-lg-4 col-md-6 mb-4">
            <article class="book-card h-100 d-flex flex-column">
                <div class="book-cover position-relative flex-shrink-0">
                    <img src="assets/img/capa.png" alt="Capa de ${escaparHtml(livro.titulo)}" class="book-image">
                    <span class="categoria-badge position-absolute top-0 end-0 m-3 badge bg-warning text-dark">${escaparHtml(livro.categoria || "Geral")}</span>
                </div>
                <div class="book-info p-3 d-flex flex-column flex-grow-1">
                    <h5 class="book-title mb-1">${escaparHtml(livro.titulo)}</h5>
                    <p class="book-author mb-2">${escaparHtml(livro.autor || "Autor não informado")}</p>
                    <p class="book-description flex-grow-1">${escaparHtml(resumo(livro.descricao))}</p>
                    <div class="book-meta">
                        <small class="book-isbn"><i class="bi bi-upc-scan"></i> ISBN: ${escaparHtml(livro.isbn || "N/A")}</small>
                        <small class="book-publicado"><i class="bi bi-calendar3"></i> ${formatarData(livro.publicado)}</small>
                    </div>
                    <div class="book-actions">
                        <a class="book-read-action" data-livro-id="${escaparHtml(livro.id)}" href="ler.html?id=${encodeURIComponent(livro.id)}"><i class="bi bi-book"></i> Ler</a>
                        <button class="book-detail-action" type="button" data-acao="detalhes-livro" data-livro-id="${escaparHtml(livro.id)}" aria-label="Ver detalhes de ${escaparHtml(livro.titulo)}"><i class="bi bi-eye"></i> Detalhes</button>
                    </div>
                </div>
            </article>
        </div>
    `).join("");
}

export function renderizarFiltrosCategorias(categorias, categoriaAtiva) {
    const lista = porId("listaCategorias");
    if (!lista) return;

    const nomes = [...new Set(categorias.map(categoria => categoria.nome).filter(Boolean))];
    lista.innerHTML = ["todas", ...nomes].map(nome => {
        const ativo = nome === categoriaAtiva ? " active" : "";
        const rotulo = nome === "todas" ? "Todos" : nome;
        return `<button class="filter-item${ativo}" type="button" data-categoria="${escaparHtml(nome)}">${escaparHtml(rotulo)}</button>`;
    }).join("");
}

export function renderizarDetalhesLivro(livro) {
    let modal = porId("modalDetalhesLivro");
    if (!modal) {
        document.body.insertAdjacentHTML("beforeend", `
            <div class="modal fade" id="modalDetalhesLivro" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered"><div class="modal-content bg-dark text-light border-secondary">
                    <div class="modal-header border-secondary"><h5 class="modal-title">Detalhes do livro</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Fechar"></button></div>
                    <div id="conteudoDetalhesLivro" class="modal-body"></div>
                </div></div>
            </div>
        `);
        modal = porId("modalDetalhesLivro");
    }

    porId("conteudoDetalhesLivro").innerHTML = `
        <p class="small text-secondary mb-1">Livro #${escaparHtml(livro.id)}</p>
        <h4>${escaparHtml(livro.titulo)}</h4>
        <p class="text-warning mb-3">${escaparHtml(livro.categoria || "Geral")}</p>
        <p>${escaparHtml(livro.descricao || "Descrição não informada.")}</p>
        <hr class="border-secondary">
        <p class="mb-1"><strong>ID do autor:</strong> ${escaparHtml(livro.autorId ?? "N/A")}</p>
        <p class="mb-1"><strong>Autor:</strong> ${escaparHtml(livro.autor || "Não informado")}</p>
        <p class="mb-1"><strong>ID da categoria:</strong> ${escaparHtml(livro.categoriaId ?? "N/A")}</p>
        <p class="mb-1"><strong>ISBN:</strong> ${escaparHtml(livro.isbn || "N/A")}</p>
        <p class="mb-0"><strong>Publicação:</strong> ${formatarData(livro.publicado)}</p>
    `;
    window.bootstrap?.Modal.getOrCreateInstance(modal).show();
}
