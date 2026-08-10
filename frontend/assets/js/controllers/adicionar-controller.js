import { AutorModel } from "../models/autor-model.js";
import { CategoriaModel } from "../models/categoria-model.js";
import { LivroModel } from "../models/livro-model.js";
import { definirCarregando, mostrarMensagem, ocultarMensagem, porId } from "../core/dom.js";
import { alterarModoRelacionamento, preencherSelect, renderizarEtapas } from "../views/form-view.js";

const estado = { modoAutor: "existente", modoCategoria: "existente" };

function valor(id) {
    return porId(id)?.value.trim() || "";
}

function livroPreenchido() {
    return ["titulo", "isbn", "publicado", "descricao"].every(id => valor(id));
}

function autorPreenchido() {
    if (estado.modoAutor === "existente") return Boolean(valor("autorId"));
    return Boolean(valor("nomeAutor") && valor("nascimentoAutor") && valor("nacionalidadeAutor") && valor("biografiaAutor").length >= 30);
}

function categoriaPreenchida() {
    if (estado.modoCategoria === "existente") return Boolean(valor("categoriaId"));
    return Boolean(valor("nomeCategoria") && valor("descricaoCategoria"));
}

function atualizarEtapas() {
    renderizarEtapas({ livroPronto: livroPreenchido(), autorPronto: autorPreenchido(), categoriaPronta: categoriaPreenchida() });
}

function definirModo(tipo, modo) {
    if (tipo === "autor") estado.modoAutor = modo;
    else estado.modoCategoria = modo;
    alterarModoRelacionamento(tipo, modo);
    atualizarEtapas();
}

async function carregarRelacionamentos() {
    const [autores, categorias] = await Promise.all([AutorModel.listar(), CategoriaModel.listar()]);
    preencherSelect("autorId", autores, "Selecione um autor");
    preencherSelect("categoriaId", categorias, "Selecione uma categoria");
}

async function resolverAutor() {
    if (estado.modoAutor === "existente") return Number(valor("autorId"));
    const autor = await AutorModel.criar({
        nome: valor("nomeAutor"),
        dataNascimento: valor("nascimentoAutor"),
        nacionalidade: valor("nacionalidadeAutor"),
        biografia: valor("biografiaAutor")
    });
    return autor.id;
}

async function resolverCategoria() {
    if (estado.modoCategoria === "existente") return Number(valor("categoriaId"));
    const categoria = await CategoriaModel.criar({ nome: valor("nomeCategoria"), descricao: valor("descricaoCategoria") });
    return categoria.id;
}

async function salvar(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = porId("btnSalvarLivro");
    ocultarMensagem("mensagemFormulario");
    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagem("mensagemFormulario", "error", "Preencha todos os campos obrigatórios antes de salvar.");
        return;
    }

    definirCarregando(botao, true, "<i class=\"bi bi-cloud-arrow-up-fill\"></i> Salvar livro");
    try {
        const autorId = await resolverAutor();
        const categoriaId = await resolverCategoria();
        await LivroModel.criar({
            titulo: valor("titulo"), isbn: valor("isbn"), publicado: valor("publicado"), descricao: valor("descricao"), autorId, categoriaId
        });
        await carregarRelacionamentos();
        formulario.reset();
        formulario.classList.remove("was-validated");
        definirModo("autor", "existente");
        definirModo("categoria", "existente");
        mostrarMensagem("mensagemFormulario", "success", "Livro cadastrado com sucesso! Ele já está disponível na Biblioteca.");
    } catch (erro) {
        console.error(erro);
        mostrarMensagem("mensagemFormulario", "error", erro.message);
    } finally {
        definirCarregando(botao, false, "<i class=\"bi bi-cloud-arrow-up-fill\"></i> Salvar livro");
        atualizarEtapas();
    }
}

export async function iniciarAdicionar() {
    definirModo("autor", "existente");
    definirModo("categoria", "existente");
    document.querySelectorAll(".choice-button").forEach(botao => botao.addEventListener("click", () => definirModo(botao.dataset.tipo, botao.dataset.modo)));
    document.querySelectorAll(".form-book").forEach(campo => {
        campo.addEventListener("input", atualizarEtapas);
        campo.addEventListener("change", atualizarEtapas);
    });
    porId("formAdicionarLivro")?.addEventListener("submit", salvar);
    try {
        await carregarRelacionamentos();
    } catch (erro) {
        console.error(erro);
        mostrarMensagem("mensagemFormulario", "error", erro.message);
    }
    atualizarEtapas();
}
