import { LivroModel } from "../models/livro-model.js";
import { escaparHtml, porId } from "../core/dom.js";
import { renderizarDetalhesLivro, renderizarLivros } from "../views/livro-view.js?v=leitura-1";

function configurarEventos() {
    porId("listaLivros")?.addEventListener("click", async evento => {
        const botao = evento.target.closest("[data-acao='detalhes-livro']");
        if (!botao) return;

        try {
            const livro = await LivroModel.buscarPorId(botao.dataset.livroId);
            renderizarDetalhesLivro(livro);
        } catch (erro) {
            console.error(erro);
        }
    });
}

async function iniciar() {
    const lista = porId("listaLivros");
    if (!lista) return;
    try {
        const livros = await LivroModel.listar();
        renderizarLivros(livros.slice(0, 4));
    } catch (erro) {
        console.error(erro);
        lista.innerHTML = `<div class="col-12 text-center py-5"><p class="text-secondary">${escaparHtml(erro.message)}</p></div>`;
    }
}

export function iniciarHome() {
    configurarEventos();
    return iniciar();
}
