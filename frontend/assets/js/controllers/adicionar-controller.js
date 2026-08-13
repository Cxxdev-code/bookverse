import { AutorModel } from "../models/autor-model.js";
import { CategoriaModel } from "../models/categoria-model.js";
import { LivroModel } from "../models/livro-model.js";
import { definirCarregando, mostrarMensagem, ocultarMensagem, porId } from "../core/dom.js";
import { alterarModoRelacionamento, preencherSelect, renderizarEtapas } from "../views/form-view.js";

const estado = { modoAutor: "existente", modoCategoria: "existente", edicaoId: null };

function valor(id) { return porId(id)?.value.trim() || ""; }
function numeroOpcional(id) {
    const numero = Number(valor(id));
    return Number.isInteger(numero) && numero > 0 ? numero : null;
}
function livroPreenchido() { return ["titulo", "isbn", "publicado", "descricao"].every(id => valor(id)); }
function autorPreenchido() {
    return estado.modoAutor === "existente"
        ? Boolean(valor("autorId"))
        : Boolean(valor("nomeAutor") && valor("nascimentoAutor") && valor("nacionalidadeAutor") && valor("biografiaAutor").length >= 30);
}
function categoriaPreenchida() {
    return estado.modoCategoria === "existente" ? Boolean(valor("categoriaId")) : Boolean(valor("nomeCategoria") && valor("descricaoCategoria"));
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
function textoBotaoSalvar() {
    return estado.edicaoId ? '<i class="bi bi-save"></i> Salvar alterações' : '<i class="bi bi-cloud-arrow-up-fill"></i> Salvar livro';
}

async function carregarRelacionamentos() {
    const [autores, categorias] = await Promise.all([AutorModel.listar(), CategoriaModel.listar()]);
    preencherSelect("autorId", autores, "Selecione um autor");
    preencherSelect("categoriaId", categorias, "Selecione uma categoria");
}
async function resolverAutor() {
    if (estado.modoAutor === "existente") return Number(valor("autorId"));
    const autor = await AutorModel.criar({ nome: valor("nomeAutor"), dataNascimento: valor("nascimentoAutor"), nacionalidade: valor("nacionalidadeAutor"), biografia: valor("biografiaAutor") });
    return autor.id;
}
async function resolverCategoria() {
    if (estado.modoCategoria === "existente") return Number(valor("categoriaId"));
    const categoria = await CategoriaModel.criar({ nome: valor("nomeCategoria"), descricao: valor("descricaoCategoria") });
    return categoria.id;
}
function dadosLivro(autorId, categoriaId) {
    return {
        titulo: valor("titulo"), isbn: valor("isbn"), publicado: valor("publicado"), descricao: valor("descricao"), autorId, categoriaId,
        capaUrl: valor("capaUrl") || null, urlLeitura: valor("urlLeitura") || null, numeroPaginas: numeroOpcional("numeroPaginas"), idioma: valor("idioma") || null,
        editora: valor("editora") || null, edicao: valor("edicao") || null, classificacaoEtaria: valor("classificacaoEtaria") || null,
        status: valor("statusLivro") || "PUBLICADO", destaque: Boolean(porId("destaque")?.checked)
    };
}
function preencherEdicao(livro) {
    const campos = ["titulo", "isbn", "publicado", "descricao", "capaUrl", "urlLeitura", "numeroPaginas", "idioma", "editora", "edicao", "classificacaoEtaria"];
    campos.forEach(campo => { if (porId(campo)) porId(campo).value = livro[campo] ?? ""; });
    porId("autorId").value = livro.autorId || "";
    porId("categoriaId").value = livro.categoriaId || "";
    porId("statusLivro").value = livro.status || "PUBLICADO";
    porId("destaque").checked = Boolean(livro.destaque);
    porId("tituloAdicionar").innerHTML = 'Atualize uma história do <span>acervo.</span>';
    porId("btnSalvarLivro").innerHTML = textoBotaoSalvar();
    document.title = `Editar ${livro.titulo} | BookVerse`;
}
async function carregarEdicaoSeNecessario() {
    const id = new URLSearchParams(window.location.search).get("editar");
    if (!id) return;
    if (!/^\d+$/.test(id)) throw new Error("O identificador do livro para edição é inválido.");
    estado.edicaoId = Number(id);
    definirModo("autor", "existente");
    definirModo("categoria", "existente");
    preencherEdicao(await LivroModel.buscarPorId(estado.edicaoId));
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
    definirCarregando(botao, true, textoBotaoSalvar(), estado.edicaoId ? "Atualizando livro..." : "Salvando livro...");
    try {
        const autorId = await resolverAutor();
        const categoriaId = await resolverCategoria();
        const livro = dadosLivro(autorId, categoriaId);
        if (estado.edicaoId) {
            await LivroModel.atualizar(estado.edicaoId, livro);
            mostrarMensagem("mensagemFormulario", "success", "Livro atualizado com sucesso. As mudanças já estão no acervo.");
        } else {
            await LivroModel.criar(livro);
            await carregarRelacionamentos();
            formulario.reset();
            formulario.classList.remove("was-validated");
            definirModo("autor", "existente");
            definirModo("categoria", "existente");
            mostrarMensagem("mensagemFormulario", "success", "Livro cadastrado com sucesso! Ele já está disponível na Biblioteca.");
        }
    } catch (erro) {
        mostrarMensagem("mensagemFormulario", "error", erro.message);
    } finally {
        definirCarregando(botao, false, textoBotaoSalvar());
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
        await carregarEdicaoSeNecessario();
    } catch (erro) {
        mostrarMensagem("mensagemFormulario", "error", erro.message);
    }
    atualizarEtapas();
}
