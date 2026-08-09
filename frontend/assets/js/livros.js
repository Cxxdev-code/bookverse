let todosOsLivros = [];
let categoriaSelecionada = "todas";

// Carrega os livros e monta a interface
async function carregarLivros() {
    const listaLivros = document.getElementById("listaLivros");

    listaLivros.innerHTML = `
        <div class="col-12 text-center py-5">
            <p class="text-light">Carregando livros...</p>
        </div>
    `;

    try {
        // Cada estatística vem da sua própria coleção. Assim, um autor ou
        // categoria recém-cadastrado é contado mesmo que ainda não possua livro.
        const [livros, autores, categorias] = await Promise.all([
            buscarLivros(),
            buscarAutores(),
            buscarCategorias()
        ]);

        todosOsLivros = livros;
        atualizarContador("quantidadeLivros", livros.length);
        atualizarContador("quantidadeAutores", autores.length);
        atualizarContador("quantidadeCategorias", categorias.length);

        // Os filtros também devem exibir todas as categorias cadastradas,
        // não somente as categorias de livros já salvos.
        renderizarCategorias(categorias);
        aplicarFiltrosEExibir();

    } catch (erro) {
        console.error("Erro ao carregar livros:", erro);
        listaLivros.innerHTML = `
            <div class="col-12 text-center py-5">
                <h4 class="text-danger">Erro ao carregar os livros.</h4>
            </div>
        `;
    }
}

function atualizarContador(id, quantidade) {
    const contador = document.getElementById(id);

    if (contador) {
        contador.textContent = quantidade;
    }
}

// Renderiza o card padronizado do livro
function criarCardLivro(livro) {
    return `
    <div class="col-xl-3 col-lg-4 col-md-6 mb-4">
        <div class="book-card h-100">
            <div class="book-cover position-relative">
                <img
                    src="${livro.imagem || 'assets/img/baner.png'}"
                    alt="${livro.titulo}"
                    class="book-image">
                <span class="categoria-badge position-absolute top-0 end-0 m-3 badge bg-warning text-dark">
                    ${livro.categoria || 'Geral'}
                </span>
            </div>
            <div class="book-info p-3 d-flex flex-column justify-content-between">
                <div>
                    <h5 class="book-title text-white font-weight-bold mb-1">${livro.titulo}</h5>
                    <p class="book-author text-muted mb-2">${livro.autor}</p>
                    <small class="book-isbn d-block text-secondary mb-2">ISBN: ${livro.isbn || 'N/A'}</small>
                </div>
                <button class="btn btn-book w-100 mt-2">
                    <i class="bi bi-book"></i> Ler Agora
                </button>
            </div>
        </div>
    </div>
    `;
}

// Filtra e ordena a lista de livros conforme interação
function aplicarFiltrosEExibir() {
    const termoPesquisa = document.getElementById("pesquisaLivro").value.toLowerCase().trim();
    const ordenacao = document.getElementById("ordenacao").value;
    const listaLivros = document.getElementById("listaLivros");

    let resultados = todosOsLivros.filter(livro => {
        const atendeCategoria = categoriaSelecionada === "todas" ||
            (livro.categoria && livro.categoria.toLowerCase() === categoriaSelecionada.toLowerCase());

        const atendePesquisa = livro.titulo.toLowerCase().includes(termoPesquisa) ||
            livro.autor.toLowerCase().includes(termoPesquisa) ||
            (livro.isbn && livro.isbn.includes(termoPesquisa));

        return atendeCategoria && atendePesquisa;
    });

    // Aplicação da Ordenação
    if (ordenacao === "az") {
        resultados.sort((a, b) => a.titulo.localeCompare(b.titulo));
    } else if (ordenacao === "za") {
        resultados.sort((a, b) => b.titulo.localeCompare(a.titulo));
    }

    // O cabeçalho mantém o total do acervo; este contador mostra apenas o
    // resultado após pesquisa, filtro ou ordenação.
    atualizarContador("quantidadeLivrosLista", resultados.length);

    // Exibir no DOM
    listaLivros.innerHTML = "";
    if (resultados.length === 0) {
        listaLivros.innerHTML = `
            <div class="col-12 text-center py-5">
                <h4 class="text-muted">Nenhum livro encontrado.</h4>
            </div>
        `;
        return;
    }

    resultados.forEach(livro => {
        listaLivros.innerHTML += criarCardLivro(livro);
    });
}

// Extrai e exibe botões dinâmicos de categorias
function renderizarCategorias(categoriasCadastradas) {
    const listaCategorias = document.getElementById("listaCategorias");
    const categorias = [...new Set(
        categoriasCadastradas
            .map(categoria => categoria.nome)
            .filter(Boolean)
    )];

    let html = `<button class="filter-item active" data-categoria="todas">Todos</button>`;

    categorias.forEach(cat => {
        html += `<button class="filter-item" data-categoria="${cat}">${cat}</button>`;
    });

    listaCategorias.innerHTML = html;

    // Configura eventos nos botões de filtro
    document.querySelectorAll(".filter-item").forEach(btn => {
        btn.addEventListener("click", (e) => {
            document.querySelectorAll(".filter-item").forEach(b => b.classList.remove("active"));
            e.target.classList.add("active");
            categoriaSelecionada = e.target.getAttribute("data-categoria");
            aplicarFiltrosEExibir();
        });
    });
}

/* ==================================================
   INICIALIZAÇÃO E EVENTOS
================================================== */
document.addEventListener("DOMContentLoaded", () => {
    carregarLivros();

    // Eventos de pesquisa e filtro
    document.getElementById("pesquisaLivro").addEventListener("input", aplicarFiltrosEExibir);
    document.getElementById("btnPesquisar").addEventListener("click", aplicarFiltrosEExibir);
    document.getElementById("ordenacao").addEventListener("change", aplicarFiltrosEExibir);

    // Pesquisa pelo Navbar superior
    const navSearch = document.getElementById("navbarSearchInput");
    if(navSearch) {
        navSearch.addEventListener("input", (e) => {
            document.getElementById("pesquisaLivro").value = e.target.value;
            aplicarFiltrosEExibir();
        });
    }
});
