import { CategoriaModel } from "../models/categoria-model.js?v=deploy-20261114-2";
import { LivroModel } from "../models/livro-model.js?v=deploy-20261114-2";
import { definirTexto, escaparHtml, porId } from "../core/dom.js?v=deploy-20261114-2";
import { renderizarCategorias } from "../views/categoria-view.js?v=deploy-20261114-2";

async function iniciar() {
    const carregando = porId("carregandoCategorias");
    const vazio = porId("nenhumaCategoria");
    const lista = porId("listaCategorias");
    try {
        const [categorias, home] = await Promise.all([CategoriaModel.listar(), LivroModel.carregarHome()]);
        definirTexto("quantidadeCategorias", categorias.length);
        definirTexto("quantidadeCategoriasLista", categorias.length);
        definirTexto("quantidadeLivros", home.totais?.livros ?? 0);
        carregando?.classList.add("d-none");
        if (categorias.length === 0) {
            vazio?.classList.remove("d-none");
            return;
        }
        renderizarCategorias(categorias);
    } catch (erro) {
        console.error(erro);
        carregando?.classList.add("d-none");
        lista.innerHTML = `<div class="col-12 text-center py-5"><h4 class="text-danger">${escaparHtml(erro.message)}</h4></div>`;
    }
}

export function iniciarCategorias() {
    return iniciar();
}
