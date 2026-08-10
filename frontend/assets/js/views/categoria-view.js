import { escaparHtml, porId } from "../core/dom.js";

const icones = ["bi-bookmark-star-fill", "bi-lightbulb-fill", "bi-rocket-takeoff-fill", "bi-heart-fill", "bi-cpu-fill", "bi-globe-americas"];

export function renderizarCategorias(categorias, livros) {
    const lista = porId("listaCategorias");
    if (!lista) return;

    lista.innerHTML = categorias.map((categoria, indice) => {
        const total = livros.filter(livro => livro.categoria?.toLocaleLowerCase("pt-BR") === categoria.nome.toLocaleLowerCase("pt-BR")).length;
        return `
            <div class="col-xl-4 col-md-6"><article class="category-card h-100">
                <div class="category-icon"><i class="bi ${icones[indice % icones.length]}"></i></div>
                <div class="category-card-content">
                    <small class="text-secondary">Categoria #${escaparHtml(categoria.id)}</small>
                    <span class="category-count">${total} ${total === 1 ? "livro disponível" : "livros disponíveis"}</span>
                    <h3>${escaparHtml(categoria.nome)}</h3>
                    <p>${escaparHtml(categoria.descricao || "Descrição não informada.")}</p>
                    <a href="livros.html?categoria=${encodeURIComponent(categoria.nome)}" class="category-link">Explorar categoria <i class="bi bi-arrow-right"></i></a>
                </div>
            </article></div>
        `;
    }).join("");
}
