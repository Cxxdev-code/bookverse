import { definirTexto, escaparHtml, porId } from "../core/dom.js";

const iconesCategorias = [
    "bi-grid-1x2-fill",
    "bi-cpu-fill",
    "bi-rocket-takeoff-fill",
    "bi-bank2",
    "bi-journal-richtext",
    "bi-lightbulb-fill"
];

function normalizarTexto(valor) {
    return String(valor || "").trim().toLocaleLowerCase("pt-BR");
}

function contarLivrosDaCategoria(categoria, livros) {
    if (Number.isFinite(Number(categoria?.quantidadeLivros))) return Number(categoria.quantidadeLivros);
    const idCategoria = categoria?.id == null ? "" : String(categoria.id);
    const nomeCategoria = normalizarTexto(categoria?.nome);

    return livros.filter(livro => {
        if (idCategoria && String(livro.categoriaId ?? "") === idCategoria) return true;
        return Boolean(nomeCategoria) && normalizarTexto(livro.categoria) === nomeCategoria;
    }).length;
}

export function renderizarResumoHome(totais = {}) {
    definirTexto("homeQuantidadeLivros", totais.livros ?? 0);
    definirTexto("homeQuantidadeAutores", totais.autores ?? 0);
    definirTexto("homeQuantidadeCategorias", totais.categorias ?? 0);
}

export function renderizarCategoriasHome(categorias = [], livros = []) {
    const lista = porId("homeCategorias");
    if (!lista) return;

    const categoriasVisiveis = categorias.filter(categoria => categoria?.nome).slice(0, 5);
    if (categoriasVisiveis.length === 0) {
        lista.innerHTML = "";
        return;
    }

    lista.innerHTML = categoriasVisiveis.map((categoria, indice) => {
        const nome = String(categoria.nome).trim();
        const totalLivros = contarLivrosDaCategoria(categoria, livros);
        const textoContagem = `${totalLivros} ${totalLivros === 1 ? "livro" : "livros"}`;
        const icone = iconesCategorias[indice % iconesCategorias.length];

        return `
            <a class="home-category-card" href="livros.html?categoria=${encodeURIComponent(nome)}">
                <span class="home-category-icon" aria-hidden="true"><i class="bi ${icone}"></i></span>
                <span class="home-category-content">
                    <strong>${escaparHtml(nome)}</strong>
                    <small class="home-category-count">${escaparHtml(textoContagem)} disponíveis</small>
                </span>
                <i class="bi bi-arrow-up-right home-category-arrow" aria-hidden="true"></i>
            </a>
        `;
    }).join("");
}

export function renderizarTagsHome(categorias = []) {
    const lista = porId("homeTags");
    if (!lista) return;

    const categoriasVisiveis = categorias.filter(categoria => categoria?.nome).slice(0, 4);
    lista.innerHTML = categoriasVisiveis.map(categoria => {
        const nome = String(categoria.nome).trim();
        return `<a class="home-tag" href="livros.html?categoria=${encodeURIComponent(nome)}">${escaparHtml(nome)}</a>`;
    }).join("");
}
