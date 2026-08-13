import { escaparHtml, porId } from "../core/dom.js";

export function preencherSelect(id, itens, textoPadrao) {
    const select = porId(id);
    if (!select) return;
    select.innerHTML = `<option value="">${textoPadrao}</option>` + itens
        .map(item => `<option value="${escaparHtml(item.id)}">${escaparHtml(item.nome)}</option>`)
        .join("");
}

export function alterarModoRelacionamento(tipo, modo) {
    const eAutor = tipo === "autor";
    const existente = porId(`${tipo}Existente`);
    const novo = porId(`${tipo}Novo`);
    const select = porId(eAutor ? "autorId" : "categoriaId");
    const seletorCampos = eAutor ? ".novo-autor" : ".nova-categoria";
    const usarNovo = modo === "novo";

    document.querySelectorAll(`[data-tipo="${tipo}"]`).forEach(botao => {
        botao.classList.toggle("active", botao.dataset.modo === modo);
    });
    existente?.classList.toggle("d-none", usarNovo);
    novo?.classList.toggle("d-none", !usarNovo);
    if (select) {
        select.required = !usarNovo;
        select.disabled = usarNovo;
    }
    document.querySelectorAll(seletorCampos).forEach(campo => {
        campo.required = usarNovo;
        campo.disabled = !usarNovo;
    });
}

export function renderizarEtapas({ livroPronto, autorPronto, categoriaPronta }) {
    const livro = document.querySelector('[data-etapa="livro"]');
    const autor = document.querySelector('[data-etapa="autor"]');
    const categoria = document.querySelector('[data-etapa="categoria"]');
    if (!livro || !autor || !categoria) return;

    livro.classList.toggle("active", !livroPronto);
    livro.classList.toggle("completed", livroPronto);
    autor.classList.toggle("active", livroPronto && !autorPronto);
    autor.classList.toggle("completed", livroPronto && autorPronto);
    categoria.classList.toggle("active", livroPronto && autorPronto && !categoriaPronta);
    categoria.classList.toggle("completed", livroPronto && autorPronto && categoriaPronta);
    document.querySelector('[data-linha="autor"]')?.classList.toggle("completed", livroPronto);
    document.querySelector('[data-linha="categoria"]')?.classList.toggle("completed", livroPronto && autorPronto);
}
