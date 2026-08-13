import { LivroModel } from "../models/livro-model.js";
import { UsuarioModel } from "../models/usuario-model.js";
import { atualizarUsuarioAtivo } from "../core/session.js";
import { definirCarregando, escaparHtml, mostrarMensagem, ocultarMensagem, porId } from "../core/dom.js";
import { renderizarPerfil, preencherFormularioPerfil } from "../views/usuario-view.js";

let usuarioAtual;

function abrirEdicao() {
    preencherFormularioPerfil(usuarioAtual);
    porId("edicaoPerfil")?.classList.remove("d-none");
    porId("edicaoPerfil")?.scrollIntoView({ behavior: "smooth", block: "center" });
}

function fecharEdicao() {
    porId("edicaoPerfil")?.classList.add("d-none");
    ocultarMensagem("mensagemPerfil");
}

async function salvarPerfil(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = porId("btnSalvarPerfil");
    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagem("mensagemPerfil", "error", "Preencha os dados obrigatórios do perfil.");
        return;
    }
    definirCarregando(botao, true, '<i class="bi bi-check2-circle"></i> Salvar alterações', "Salvando perfil...");
    try {
        usuarioAtual = await UsuarioModel.atualizarMeuPerfil({
            nome: porId("nomePerfilEdicao").value.trim(),
            sexo: porId("sexoPerfilEdicao").value,
            dataNascimento: porId("nascimentoPerfilEdicao").value,
            imagemPerfilUrl: porId("imagemPerfilEdicao").value.trim() || null
        });
        atualizarUsuarioAtivo(usuarioAtual);
        renderizarPerfil(usuarioAtual, null);
        mostrarMensagem("mensagemPerfil", "success", "Perfil atualizado com sucesso.");
    } catch (erro) {
        mostrarMensagem("mensagemPerfil", "error", erro.message);
    } finally {
        definirCarregando(botao, false, '<i class="bi bi-check2-circle"></i> Salvar alterações');
    }
}

async function iniciar() {
    const carregando = porId("carregandoPerfil");
    const vazio = porId("perfilVazio");
    try {
        const [usuario, home] = await Promise.all([UsuarioModel.meuPerfil(), LivroModel.carregarHome()]);
        usuarioAtual = usuario;
        atualizarUsuarioAtivo(usuario);
        carregando?.classList.add("d-none");
        renderizarPerfil(usuario, home.totais || { livros: 0, autores: 0, categorias: 0 });
    } catch (erro) {
        carregando?.classList.add("d-none");
        vazio?.classList.remove("d-none");
        vazio.innerHTML = `<i class="bi bi-exclamation-circle display-1"></i><h2>Não foi possível carregar o perfil</h2><p>${escaparHtml(erro.message)}</p>`;
    }
}

export function iniciarPerfil() {
    porId("btnEditarPerfil")?.addEventListener("click", abrirEdicao);
    porId("btnCancelarEdicaoPerfil")?.addEventListener("click", fecharEdicao);
    porId("formEditarPerfil")?.addEventListener("submit", salvarPerfil);
    return iniciar();
}
