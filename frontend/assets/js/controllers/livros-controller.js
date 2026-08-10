import { LivroModel } from "../models/livro-model.js";
import { CategoriaModel } from "../models/categoria-model.js";
import { definirTexto, escaparHtml, porId } from "../core/dom.js";
import { renderizarDetalhesLivro, renderizarFiltrosCategorias, renderizarLivros, renderizarPaginacao } from "../views/livro-view.js?v=catalogo-api-1";

const estado = { categorias: [], pagina: 0, tamanho: 12, busca: "", categoriaId: null, ordem: "recentes" };
let temporizadorPesquisa;

function sincronizarURL() {
    const parametros = new URLSearchParams();
    if (estado.busca) parametros.set("busca", estado.busca);
    const categoria = estado.categorias.find(item => Number(item.id) === Number(estado.categoriaId));
    if (categoria?.nome) parametros.set("categoria", categoria.nome);
    const sufixo = parametros.toString();
    window.history.replaceState({}, "", `livros.html${sufixo ? `?${sufixo}` : ""}`);
}

async function carregarCatalogo({ reiniciarPagina = false } = {}) {
    if (reiniciarPagina) estado.pagina = 0;
    const lista = porId("listaLivros");
    lista?.setAttribute("aria-busy", "true");
    try {
        const resposta = await LivroModel.listarCatalogo({
            page: estado.pagina,
            size: estado.tamanho,
            busca: estado.busca,
            categoriaId: estado.categoriaId,
            ordem: estado.ordem
        });
        estado.pagina = resposta.page;
        renderizarLivros(resposta.content || []);
        renderizarPaginacao(resposta);
        definirTexto("quantidadeLivrosLista", resposta.totalElements ?? 0);
        sincronizarURL();
    } catch (erro) {
        console.error(erro);
        if (lista) lista.innerHTML = `<div class="col-12 text-center py-5"><h4 class="text-danger">${escaparHtml(erro.message)}</h4></div>`;
        renderizarPaginacao(null);
    } finally {
        lista?.removeAttribute("aria-busy");
    }
}

function agendarPesquisa() {
    window.clearTimeout(temporizadorPesquisa);
    temporizadorPesquisa = window.setTimeout(() => carregarCatalogo({ reiniciarPagina: true }), 300);
}

function configurarEventos() {
    porId("pesquisaLivro")?.addEventListener("input", evento => {
        estado.busca = evento.target.value.trim();
        agendarPesquisa();
    });
    porId("btnPesquisar")?.addEventListener("click", () => {
        estado.busca = porId("pesquisaLivro")?.value.trim() || "";
        carregarCatalogo({ reiniciarPagina: true });
    });
    porId("ordenacao")?.addEventListener("change", evento => {
        estado.ordem = evento.target.value;
        carregarCatalogo({ reiniciarPagina: true });
    });
    document.querySelector(".navbar .search-box")?.addEventListener("submit", evento => {
        evento.preventDefault();
        const campoNavbar = evento.currentTarget.querySelector("input");
        const pesquisa = porId("pesquisaLivro");
        if (!campoNavbar || !pesquisa) return;
        pesquisa.value = campoNavbar.value;
        estado.busca = campoNavbar.value.trim();
        carregarCatalogo({ reiniciarPagina: true });
    });
    porId("listaCategorias")?.addEventListener("click", evento => {
        const botao = evento.target.closest("[data-categoria-id]");
        if (!botao) return;
        estado.categoriaId = botao.dataset.categoriaId ? Number(botao.dataset.categoriaId) : null;
        renderizarFiltrosCategorias(estado.categorias, estado.categoriaId);
        carregarCatalogo({ reiniciarPagina: true });
    });
    porId("paginacaoLivros")?.addEventListener("click", evento => {
        const botao = evento.target.closest("[data-pagina]");
        if (!botao || botao.disabled) return;
        estado.pagina = Number(botao.dataset.pagina);
        carregarCatalogo();
        porId("tituloCatalogo")?.scrollIntoView({ behavior: "smooth", block: "start" });
    });
    porId("listaLivros")?.addEventListener("click", async evento => {
        const botao = evento.target.closest("[data-acao='detalhes-livro']");
        if (!botao) return;
        try {
            renderizarDetalhesLivro(await LivroModel.buscarPorId(botao.dataset.livroId));
        } catch (erro) {
            console.error(erro);
        }
    });
}

async function iniciar() {
    const lista = porId("listaLivros");
    try {
        const [categorias, home] = await Promise.all([CategoriaModel.listar(), LivroModel.carregarHome()]);
        estado.categorias = categorias;
        const parametros = new URLSearchParams(window.location.search);
        estado.busca = parametros.get("busca")?.trim() || "";
        const categoriaInicial = categorias.find(item => item.nome === parametros.get("categoria"));
        estado.categoriaId = categoriaInicial?.id ?? null;
        if (porId("pesquisaLivro")) porId("pesquisaLivro").value = estado.busca;

        definirTexto("quantidadeLivros", home.totais?.livros ?? 0);
        definirTexto("quantidadeAutores", home.totais?.autores ?? 0);
        definirTexto("quantidadeCategorias", home.totais?.categorias ?? categorias.length);
        renderizarFiltrosCategorias(categorias, estado.categoriaId);
        await carregarCatalogo();
    } catch (erro) {
        console.error(erro);
        if (lista) lista.innerHTML = `<div class="col-12 text-center py-5"><h4 class="text-danger">${escaparHtml(erro.message)}</h4></div>`;
    }
}

export function iniciarLivros() {
    configurarEventos();
    return iniciar();
}
