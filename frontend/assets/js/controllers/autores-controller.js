import { AutorModel } from "../models/autor-model.js";
import { definirTexto, escaparHtml, porId } from "../core/dom.js";
import { renderizarAutores } from "../views/autor-view.js";

async function iniciar() {
    const carregando = porId("carregandoAutores");
    const vazio = porId("nenhumAutor");
    const lista = porId("listaAutores");
    try {
        const autores = await AutorModel.listar();
        definirTexto("quantidadeAutores", autores.length);
        definirTexto("quantidadeAutoresLista", autores.length);
        carregando?.classList.add("d-none");
        if (autores.length === 0) {
            vazio?.classList.remove("d-none");
            return;
        }
        renderizarAutores(autores);
    } catch (erro) {
        console.error(erro);
        carregando?.classList.add("d-none");
        lista.innerHTML = `<div class="col-12 text-center py-5"><h4 class="text-danger">${escaparHtml(erro.message)}</h4></div>`;
    }
}

export function iniciarAutores() {
    return iniciar();
}
