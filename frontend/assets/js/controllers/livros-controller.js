import { LivroModel } from "../models/livro-model.js";
import { AutorModel } from "../models/autor-model.js";
import { CategoriaModel } from "../models/categoria-model.js";
import { definirTexto, escaparHtml, porId } from "../core/dom.js";
import { renderizarDetalhesLivro, renderizarFiltrosCategorias, renderizarLivros } from "../views/livro-view.js?v=leitura-1";

const estado = { livros: [], autores: [], categorias: [], categoria: "todas" };

function ordenar(livros) {
    const ordem = porId("ordenacao")?.value || "";
    const resultado = [...livros];
    if (ordem.includes("A-Z")) resultado.sort((a, b) => a.titulo.localeCompare(b.titulo));
    if (ordem.includes("Z-A")) resultado.sort((a, b) => b.titulo.localeCompare(a.titulo));
    if (ordem.includes("Autor")) resultado.sort((a, b) => (a.autor || "").localeCompare(b.autor || ""));
    if (ordem.includes("recentes")) resultado.sort((a, b) => new Date(b.publicado) - new Date(a.publicado));
    if (ordem.includes("antigos")) resultado.sort((a, b) => new Date(a.publicado) - new Date(b.publicado));
    return resultado;
}

function aplicarFiltros() {
    const termo = porId("pesquisaLivro")?.value.toLocaleLowerCase("pt-BR").trim() || "";
    const filtrados = estado.livros.filter(livro => {
        const categoriaCorreta = estado.categoria === "todas" || livro.categoria?.toLocaleLowerCase("pt-BR") === estado.categoria.toLocaleLowerCase("pt-BR");
        const texto = [livro.titulo, livro.autor, livro.isbn, livro.descricao, livro.categoria].filter(Boolean).join(" ").toLocaleLowerCase("pt-BR");
        return categoriaCorreta && texto.includes(termo);
    });
    renderizarLivros(ordenar(filtrados));
    definirTexto("quantidadeLivrosLista", filtrados.length);
}

function configurarEventos() {
    porId("pesquisaLivro")?.addEventListener("input", aplicarFiltros);
    porId("btnPesquisar")?.addEventListener("click", aplicarFiltros);
    porId("ordenacao")?.addEventListener("change", aplicarFiltros);
    document.querySelector(".navbar .search-box")?.addEventListener("submit", evento => {
        evento.preventDefault();
        const campoNavbar = evento.currentTarget.querySelector("input");
        const pesquisa = porId("pesquisaLivro");
        if (!campoNavbar || !pesquisa) return;
        pesquisa.value = campoNavbar.value;
        aplicarFiltros();
    });
    document.querySelector(".navbar .search-box input")?.addEventListener("input", evento => {
        const pesquisa = porId("pesquisaLivro");
        if (!pesquisa) return;
        pesquisa.value = evento.target.value;
        aplicarFiltros();
    });
    porId("listaCategorias")?.addEventListener("click", evento => {
        const botao = evento.target.closest("[data-categoria]");
        if (!botao) return;
        estado.categoria = botao.dataset.categoria;
        renderizarFiltrosCategorias(estado.categorias, estado.categoria);
        aplicarFiltros();
    });
    porId("listaLivros")?.addEventListener("click", async evento => {
        const botao = evento.target.closest("[data-acao='detalhes-livro']");
        if (!botao) return;
        try {
            const livro = await LivroModel.buscarPorId(botao.dataset.livroId);
            renderizarDetalhesLivro(livro);
        } catch (erro) {
            console.error(erro);
            const livro = estado.livros.find(item => String(item.id) === botao.dataset.livroId);
            if (livro) renderizarDetalhesLivro(livro);
        }
    });
}

async function iniciar() {
    const lista = porId("listaLivros");
    try {
        [estado.livros, estado.autores, estado.categorias] = await Promise.all([
            LivroModel.listar(), AutorModel.listar(), CategoriaModel.listar()
        ]);
        const parametros = new URLSearchParams(window.location.search);
        const busca = parametros.get("busca");
        const categoria = parametros.get("categoria");
        if (busca) porId("pesquisaLivro").value = busca;
        if (categoria && estado.categorias.some(item => item.nome === categoria)) estado.categoria = categoria;
        definirTexto("quantidadeLivros", estado.livros.length);
        definirTexto("quantidadeAutores", estado.autores.length);
        definirTexto("quantidadeCategorias", estado.categorias.length);
        renderizarFiltrosCategorias(estado.categorias, estado.categoria);
        aplicarFiltros();
    } catch (erro) {
        console.error(erro);
        lista.innerHTML = `<div class="col-12 text-center py-5"><h4 class="text-danger">${escaparHtml(erro.message)}</h4></div>`;
    }
}

export function iniciarLivros() {
    configurarEventos();
    return iniciar();
}
