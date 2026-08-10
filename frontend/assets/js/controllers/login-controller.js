import { UsuarioModel } from "../models/usuario-model.js";
import { definirCarregando, mostrarMensagem, ocultarMensagem, porId } from "../core/dom.js";
import { salvarUsuarioAtivo } from "../core/session.js";
import { alterarModoAuth, renderizarUsuariosLogin } from "../views/auth-view.js";

let usuarios = [];

function redirecionarParaPerfil(texto) {
    mostrarMensagem("mensagemAuth", "success", texto);
    setTimeout(() => { window.location.href = "perfil.html"; }, 700);
}

function entrar(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagem("mensagemAuth", "error", "Selecione um usuário para continuar.");
        return;
    }
    const matricula = porId("usuarioExistente").value;
    const usuario = usuarios.find(item => String(item.matricula) === matricula);
    salvarUsuarioAtivo(usuario);
    redirecionarParaPerfil(`Olá, ${usuario.nome}! Abrindo seu perfil...`);
}

async function criarConta(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = porId("btnCriarConta");
    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagem("mensagemAuth", "error", "Preencha seus dados para criar o perfil.");
        return;
    }
    definirCarregando(botao, true, "<i class=\"bi bi-person-check-fill\"></i> Criar e entrar", "Criando perfil...");
    try {
        const usuario = await UsuarioModel.criar({
            nome: porId("nomeNovoUsuario").value.trim(),
            sexo: porId("sexoNovoUsuario").value,
            dataNascimento: porId("nascimentoNovoUsuario").value
        });
        salvarUsuarioAtivo(usuario);
        redirecionarParaPerfil("Perfil criado com sucesso. Preparando sua biblioteca...");
    } catch (erro) {
        console.error(erro);
        mostrarMensagem("mensagemAuth", "error", erro.message);
    } finally {
        definirCarregando(botao, false, "<i class=\"bi bi-person-check-fill\"></i> Criar e entrar");
    }
}

export async function iniciarLogin() {
    document.querySelectorAll(".auth-tab").forEach(botao => botao.addEventListener("click", () => {
        alterarModoAuth(botao.dataset.authMode);
        ocultarMensagem("mensagemAuth");
    }));
    porId("formEntrar")?.addEventListener("submit", entrar);
    porId("formCriarConta")?.addEventListener("submit", criarConta);
    try {
        usuarios = await UsuarioModel.listar();
        renderizarUsuariosLogin(usuarios);
    } catch (erro) {
        console.error(erro);
        mostrarMensagem("mensagemAuth", "error", erro.message);
    }
}
