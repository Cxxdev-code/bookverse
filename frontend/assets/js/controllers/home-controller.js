import { LivroModel } from "../models/livro-model.js";
import { escaparHtml, porId } from "../core/dom.js";
import { renderizarDetalhesLivro, renderizarLivros } from "../views/livro-view.js?v=catalogo-api-1";
import { renderizarCategoriasHome, renderizarResumoHome, renderizarTagsHome } from "../views/home-view.js?v=catalogo-api-1";

function configurarEventos() {
    porId("listaLivros")?.addEventListener("click", async evento => {
        const botao = evento.target.closest("[data-acao='detalhes-livro']");
        if (!botao) return;
        try {
            renderizarDetalhesLivro(await LivroModel.buscarPorId(botao.dataset.livroId));
        } catch (erro) {
            console.error(erro);
        }
    });

    porId("homeSearchForm")?.addEventListener("submit", evento => {
        evento.preventDefault();
        const campo = evento.currentTarget.querySelector("input[type='search'], input[name='busca'], input");
        const busca = campo?.value.trim();
        if (busca) window.location.href = `livros.html?busca=${encodeURIComponent(busca)}`;
    });
}

async function iniciar() {
    const lista = porId("listaLivros");
    if (!lista) return;
    try {
        const home = await LivroModel.carregarHome();
        const livros = home.destaques?.length ? home.destaques : (home.recentes || []);
        renderizarResumoHome(home.totais);
        renderizarCategoriasHome(home.categorias || []);
        renderizarTagsHome(home.categorias || []);
        renderizarLivros(livros.slice(0, 3), { layout: "home" });
    } catch (erro) {
        console.error(erro);
        lista.innerHTML = `<div class="col-12 text-center py-5"><p class="text-secondary">${escaparHtml(erro.message)}</p></div>`;
    }
}

export function iniciarHome() {
    configurarEventos();
    return iniciar();
}
