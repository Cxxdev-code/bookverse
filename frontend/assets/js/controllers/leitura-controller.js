import { LivroModel } from "../models/livro-model.js";
import { mostrarErroLeitura, renderizarLivroLeitura } from "../views/leitura-view.js";

function obterIdLivro() {
    const valor = new URLSearchParams(window.location.search).get("id");
    if (!valor || !/^\d+$/.test(valor)) return null;
    return Number(valor);
}

export async function iniciarLeitura() {
    const id = obterIdLivro();
    if (!id) {
        mostrarErroLeitura("O link de leitura está inválido. Escolha um livro na Biblioteca.");
        return;
    }

    try {
        const livro = await LivroModel.buscarPorId(id);
        renderizarLivroLeitura(livro);
    } catch (erro) {
        console.error(erro);
        mostrarErroLeitura(erro.message || "O livro solicitado não foi encontrado.");
    }
}
