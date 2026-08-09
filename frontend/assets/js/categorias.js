const iconesCategoria = [
    "bi-bookmark-star-fill",
    "bi-lightbulb-fill",
    "bi-rocket-takeoff-fill",
    "bi-heart-fill",
    "bi-cpu-fill",
    "bi-globe-americas"
];

function criarCardCategoria(categoria, livros, indice) {
    const quantidadeLivros = livros.filter(livro =>
        livro.categoria && livro.categoria.toLocaleLowerCase("pt-BR") === categoria.nome.toLocaleLowerCase("pt-BR")
    ).length;
    const icone = iconesCategoria[indice % iconesCategoria.length];
    const textoLivro = quantidadeLivros === 1 ? "livro disponível" : "livros disponíveis";

    return `
        <div class="col-xl-4 col-md-6">
            <article class="category-card h-100">
                <div class="category-icon"><i class="bi ${icone}"></i></div>
                <div class="category-card-content">
                    <span class="category-count">${quantidadeLivros} ${textoLivro}</span>
                    <h3>${categoria.nome}</h3>
                    <p>${categoria.descricao || "Uma seleção especial de livros para você explorar."}</p>
                    <a href="livros.html" class="category-link">Explorar categoria <i class="bi bi-arrow-right"></i></a>
                </div>
            </article>
        </div>
    `;
}

function atualizarElemento(id, valor) {
    const elemento = document.getElementById(id);
    if (elemento) elemento.textContent = valor;
}

async function carregarCategorias() {
    const carregando = document.getElementById("carregandoCategorias");
    const listaCategorias = document.getElementById("listaCategorias");
    const nenhumaCategoria = document.getElementById("nenhumaCategoria");

    try {
        const [categorias, livros] = await Promise.all([buscarCategorias(), buscarLivros()]);

        atualizarElemento("quantidadeCategorias", categorias.length);
        atualizarElemento("quantidadeCategoriasLista", categorias.length);
        atualizarElemento("quantidadeLivros", livros.length);
        carregando.classList.add("d-none");

        if (categorias.length === 0) {
            nenhumaCategoria.classList.remove("d-none");
            return;
        }

        listaCategorias.innerHTML = categorias
            .map((categoria, indice) => criarCardCategoria(categoria, livros, indice))
            .join("");
    } catch (erro) {
        console.error("Erro ao carregar categorias:", erro);
        carregando.classList.add("d-none");
        listaCategorias.innerHTML = `
            <div class="col-12 text-center py-5">
                <h4 class="text-danger">Não foi possível carregar as categorias.</h4>
            </div>
        `;
    }
}

document.addEventListener("DOMContentLoaded", carregarCategorias);
