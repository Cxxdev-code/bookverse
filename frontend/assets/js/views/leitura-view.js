import { definirTexto, escaparHtml, formatarData, porId } from "../core/dom.js";

export function renderizarLivroLeitura(livro) {
    document.title = `${livro.titulo || "Leitura"} | BookVerse`;
    definirTexto("codigoLivroLeitura", `Livro #${livro.id}`);
    definirTexto("tituloLivroLeitura", livro.titulo || "Livro sem título");
    definirTexto("autorLivroLeitura", livro.autor || "Autor não informado");
    definirTexto("categoriaLivroLeitura", livro.categoria || "Categoria não informada");
    definirTexto("descricaoLivroLeitura", livro.descricao || "Descrição não informada.");
    definirTexto("isbnLivroLeitura", livro.isbn || "Não informado");
    definirTexto("publicadoLivroLeitura", formatarData(livro.publicado));
    definirTexto("autorIdLivroLeitura", livro.autorId ? `#${livro.autorId}` : "Não informado");
    definirTexto("categoriaIdLivroLeitura", livro.categoriaId ? `#${livro.categoriaId}` : "Não informado");

    const capa = porId("capaLivroLeitura");
    if (capa) capa.alt = `Capa de ${livro.titulo || "livro"}`;

    porId("estadoLeitura")?.classList.add("d-none");
    porId("livroLeitura")?.classList.remove("d-none");
}

export function mostrarErroLeitura(mensagem) {
    const estado = porId("estadoLeitura");
    if (!estado) return;

    estado.innerHTML = `
        <div class="reader-state-card">
            <i class="bi bi-bookmark-x"></i>
            <h1>Não foi possível abrir este livro</h1>
            <p>${escaparHtml(mensagem)}</p>
            <a class="reader-back-button" href="livros.html"><i class="bi bi-arrow-left"></i> Voltar para a Biblioteca</a>
        </div>
    `;
    estado.classList.remove("d-none");
    porId("livroLeitura")?.classList.add("d-none");
}
