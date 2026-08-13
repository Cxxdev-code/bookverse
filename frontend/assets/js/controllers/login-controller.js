import { AuthModel } from "../models/auth-model.js";
import { definirCarregando, mostrarMensagem, ocultarMensagem, porId } from "../core/dom.js";
import { obterUsuarioAtivo, salvarSessao } from "../core/session.js";
import { alterarModoAuth } from "../views/auth-view.js";

function destinoAposLogin() {
    const retorno = new URLSearchParams(window.location.search).get("retorno");
    return retorno && /^[a-z-]+\.html(?:\?.*)?$/i.test(retorno) ? retorno : "perfil.html";
}

function redirecionar(texto) {
    mostrarMensagem("mensagemAuth", "success", texto);
    window.setTimeout(() => { window.location.href = destinoAposLogin(); }, 650);
}

async function entrar(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = porId("btnEntrar");
    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagem("mensagemAuth", "error", "Informe seu e-mail e senha para continuar.");
        return;
    }
    definirCarregando(botao, true, '<i class="bi bi-arrow-right-circle"></i> Entrar no BookVerse', "Verificando acesso...");
    try {
        const autenticacao = await AuthModel.entrar({
            email: porId("emailLogin").value.trim(),
            senha: porId("senhaLogin").value
        });
        salvarSessao(autenticacao);
        redirecionar(`Olá, ${autenticacao.usuario.nome}! Seu acesso foi confirmado.`);
    } catch (erro) {
        mostrarMensagem("mensagemAuth", "error", erro.message);
    } finally {
        definirCarregando(botao, false, '<i class="bi bi-arrow-right-circle"></i> Entrar no BookVerse');
    }
}

async function criarConta(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = porId("btnCriarConta");
    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagem("mensagemAuth", "error", "Preencha os campos obrigatórios para criar sua conta.");
        return;
    }
    if (porId("senhaNovoUsuario").value !== porId("confirmacaoSenhaNovoUsuario").value) {
        mostrarMensagem("mensagemAuth", "error", "A confirmação de senha não corresponde à senha informada.");
        return;
    }
    definirCarregando(botao, true, '<i class="bi bi-person-check-fill"></i> Criar e entrar', "Criando conta...");
    try {
        const autenticacao = await AuthModel.registrar({
            nome: porId("nomeNovoUsuario").value.trim(),
            email: porId("emailNovoUsuario").value.trim(),
            senha: porId("senhaNovoUsuario").value,
            sexo: porId("sexoNovoUsuario").value,
            dataNascimento: porId("nascimentoNovoUsuario").value,
            imagemPerfilUrl: porId("imagemNovoUsuario").value.trim() || null
        });
        salvarSessao(autenticacao);
        redirecionar("Conta criada com sucesso. Bem-vindo ao BookVerse!");
    } catch (erro) {
        mostrarMensagem("mensagemAuth", "error", erro.message);
    } finally {
        definirCarregando(botao, false, '<i class="bi bi-person-check-fill"></i> Criar e entrar');
    }
}

export async function iniciarLogin() {
    if (obterUsuarioAtivo()) {
        window.location.replace("perfil.html");
        return;
    }
    document.querySelectorAll(".auth-tab").forEach(botao => botao.addEventListener("click", () => {
        alterarModoAuth(botao.dataset.authMode);
        ocultarMensagem("mensagemAuth");
    }));
    porId("formEntrar")?.addEventListener("submit", entrar);
    porId("formCriarConta")?.addEventListener("submit", criarConta);
    if (new URLSearchParams(window.location.search).get("motivo") === "sessao") {
        mostrarMensagem("mensagemAuth", "error", "Sua sessão expirou. Entre novamente para continuar.");
    }
}
