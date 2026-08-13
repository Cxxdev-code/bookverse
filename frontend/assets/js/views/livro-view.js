import { escaparHtml, formatarData, porId } from "../core/dom.js";
import { ehAdministrador } from "../core/session.js";

function resumo(texto, limite = 118) {
    const valor = texto || "Descrição não informada.";
    return valor.length > limite ? `${valor.slice(0, limite).trim()}…` : valor;
}

function capaSegura(valor) {
    const capa = String(valor || "").trim();
    return /^(https?:\/\/|assets\/)/i.test(capa) ? capa : "assets/img/capa.png";
}

export function renderizarLivros(livros, { layout = "library" } = {}) {
    const lista = porId("listaLivros");
    if (!lista) return;
    if (livros.length === 0) {
        lista.innerHTML = '<div class="col-12 text-center py-5"><h4 class="text-muted">Nenhum livro encontrado.</h4><p class="text-secondary mb-0">Tente alterar a pesquisa ou escolher outra categoria.</p></div>';
        return;
    }
    const home = layout === "home";
    const coluna = home ? "col-lg-4 col-md-6" : "col-xl-3 col-lg-4 col-md-6";
    lista.innerHTML = livros.map(livro => {
        const titulo = escaparHtml(livro.titulo || "Livro sem título");
        const autor = escaparHtml(livro.autor || "Autor não informado");
        const categoria = escaparHtml(livro.categoria || "Geral");
        const data = formatarData(livro.publicado);
        const id = escaparHtml(livro.id);
        const rotaLeitura = `ler.html?id=${encodeURIComponent(livro.id)}`;
        const paginas = Number.isFinite(Number(livro.numeroPaginas)) ? `${livro.numeroPaginas} pág.` : "";
        const metadados = home
            ? `<div class="book-meta book-meta--home"><small><i class="bi bi-calendar3"></i> ${data}</small>${paginas ? `<small><i class="bi bi-book"></i> ${escaparHtml(paginas)}</small>` : ""}</div>`
            : `<p class="book-description flex-grow-1">${escaparHtml(resumo(livro.descricaoResumo || livro.descricao))}</p><div class="book-meta"><small><i class="bi bi-calendar3"></i> ${data}</small>${paginas ? `<small><i class="bi bi-book"></i> ${escaparHtml(paginas)}</small>` : ""}${livro.isbn ? `<small><i class="bi bi-upc-scan"></i> ISBN: ${escaparHtml(livro.isbn)}</small>` : ""}</div>`;
        return `<div class="${coluna}"><article class="book-card book-card--catalog ${home ? "book-card--home" : "book-card--library"} h-100 d-flex flex-column">
            <a class="book-cover position-relative flex-shrink-0" href="${rotaLeitura}" aria-label="Ler ${titulo}">
                <img src="${escaparHtml(capaSegura(livro.capaUrl))}" alt="Capa de ${titulo}" class="book-image">
                <span class="book-cover-scrim" aria-hidden="true"></span><span class="categoria-badge">${categoria}</span><span class="book-cover-read"><i class="bi bi-book" aria-hidden="true"></i> Ler agora</span>
            </a>
            <div class="book-info d-flex flex-column flex-grow-1"><div class="book-heading"><h3 class="book-title">${titulo}</h3><p class="book-author">${autor}</p></div>${metadados}
                <div class="book-actions"><a class="book-read-action" data-livro-id="${id}" href="${rotaLeitura}"><i class="bi bi-book" aria-hidden="true"></i> Ler</a><button class="book-detail-action" type="button" data-acao="detalhes-livro" data-livro-id="${id}" aria-label="Ver detalhes de ${titulo}"><i class="bi bi-eye" aria-hidden="true"></i><span>Detalhes</span></button></div>
            </div></article></div>`;
    }).join("");
}

export function renderizarFiltrosCategorias(categorias, categoriaAtiva) {
    const lista = porId("listaCategorias");
    if (!lista) return;
    const itens = [{ id: null, nome: "Todos" }, ...categorias.filter(categoria => categoria?.nome)];
    lista.innerHTML = itens.map(categoria => {
        const ativo = String(categoria.id ?? "") === String(categoriaAtiva ?? "") ? " active" : "";
        return `<button class="filter-item${ativo}" type="button" data-categoria-id="${escaparHtml(categoria.id ?? "")}">${escaparHtml(categoria.nome)}</button>`;
    }).join("");
}

export function renderizarPaginacao(resposta) {
    const paginacao = porId("paginacaoLivros");
    if (!paginacao) return;
    if (!resposta || resposta.totalPages <= 1) { paginacao.innerHTML = ""; return; }
    paginacao.innerHTML = `<button class="library-page-button" type="button" data-pagina="${resposta.page - 1}" ${resposta.hasPrevious ? "" : "disabled"}><i class="bi bi-arrow-left"></i><span>Anterior</span></button><span class="library-page-indicator">Página ${resposta.page + 1} de ${resposta.totalPages}</span><button class="library-page-button" type="button" data-pagina="${resposta.page + 1}" ${resposta.hasNext ? "" : "disabled"}><span>Próxima</span><i class="bi bi-arrow-right"></i></button>`;
}

export function renderizarDetalhesLivro(livro) {
    let modal = porId("modalDetalhesLivro");
    if (!modal) {
        document.body.insertAdjacentHTML("beforeend", '<div class="modal fade" id="modalDetalhesLivro" tabindex="-1" aria-hidden="true"><div class="modal-dialog modal-dialog-centered"><div class="modal-content bg-dark text-light border-secondary"><div class="modal-header border-secondary"><h5 class="modal-title">Detalhes do livro</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Fechar"></button></div><div id="conteudoDetalhesLivro" class="modal-body"></div></div></div></div>');
        modal = porId("modalDetalhesLivro");
    }
    const extras = [["Páginas", livro.numeroPaginas], ["Idioma", livro.idioma], ["Editora", livro.editora], ["Edição", livro.edicao], ["Classificação", livro.classificacaoEtaria]]
        .filter(([, valor]) => valor)
        .map(([rotulo, valor]) => `<p class="mb-1"><strong>${rotulo}:</strong> ${escaparHtml(valor)}</p>`).join("");
    const acaoAdministrativa = ehAdministrador()
        ? `<a class="btn btn-warning w-100 mt-4" href="adicionar.html?editar=${encodeURIComponent(livro.id)}"><i class="bi bi-pencil-square"></i> Editar livro</a>` : "";
    porId("conteudoDetalhesLivro").innerHTML = `<p class="small text-secondary mb-1">Livro #${escaparHtml(livro.id)}</p><h4>${escaparHtml(livro.titulo)}</h4><p class="text-warning mb-3">${escaparHtml(livro.categoria || "Geral")}</p><p>${escaparHtml(livro.descricao || "Descrição não informada.")}</p><hr class="border-secondary"><p class="mb-1"><strong>Autor:</strong> ${escaparHtml(livro.autor || "Não informado")}</p><p class="mb-1"><strong>ISBN:</strong> ${escaparHtml(livro.isbn || "N/A")}</p><p class="mb-1"><strong>Publicação:</strong> ${formatarData(livro.publicado)}</p>${extras}${acaoAdministrativa}`;
    window.bootstrap?.Modal.getOrCreateInstance(modal).show();
}
