let usuariosCadastrados = [];

function elementoAuth(id) {
    return document.getElementById(id);
}

function mostrarMensagemAuth(tipo, texto) {
    const mensagem = elementoAuth("mensagemAuth");
    mensagem.className = `auth-message ${tipo}`;
    mensagem.innerHTML = `<i class="bi bi-${tipo === "success" ? "check-circle-fill" : "exclamation-triangle-fill"}"></i><span>${texto}</span>`;
}

function selecionarModoAuth(modo) {
    const entrar = modo === "entrar";
    elementoAuth("formEntrar").classList.toggle("d-none", !entrar);
    elementoAuth("formCriarConta").classList.toggle("d-none", entrar);
    document.querySelectorAll(".auth-tab").forEach(botao => {
        botao.classList.toggle("active", botao.dataset.authMode === modo);
    });
    elementoAuth("mensagemAuth").classList.add("d-none");
}

function preencherUsuarios() {
    const select = elementoAuth("usuarioExistente");
    select.innerHTML = '<option value="">Selecione seu nome</option>' + usuariosCadastrados
        .map(usuario => `<option value="${usuario.matricula}">${usuario.nome} — matrícula ${usuario.matricula}</option>`)
        .join("");
}

function guardarSessao(usuario) {
    sessionStorage.setItem("bookverseUsuarioMatricula", String(usuario.matricula));
}

function redirecionarPerfil(mensagem) {
    mostrarMensagemAuth("success", mensagem);
    setTimeout(() => { window.location.href = "perfil.html"; }, 700);
}

async function cadastrarUsuario(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;
    const botao = elementoAuth("btnCriarConta");

    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagemAuth("error", "Preencha seus dados para criar o perfil.");
        return;
    }

    botao.disabled = true;
    botao.innerHTML = '<span class="spinner-border spinner-border-sm" aria-hidden="true"></span> Criando perfil...';

    try {
        const resposta = await fetch(`${API_URL}/usuarios`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                nome: elementoAuth("nomeNovoUsuario").value.trim(),
                sexo: elementoAuth("sexoNovoUsuario").value,
                dataNascimento: elementoAuth("nascimentoNovoUsuario").value
            })
        });

        if (!resposta.ok) {
            let erro = {};
            try { erro = await resposta.json(); } catch (_) { /* resposta sem JSON */ }
            throw new Error(erro.message || "Não foi possível criar o perfil.");
        }

        const usuario = await resposta.json();
        guardarSessao(usuario);
        redirecionarPerfil("Perfil criado com sucesso. Preparando sua biblioteca...");
    } catch (erro) {
        console.error("Erro ao criar usuário:", erro);
        mostrarMensagemAuth("error", erro.message);
    } finally {
        botao.disabled = false;
        botao.innerHTML = '<i class="bi bi-person-check-fill"></i> Criar e entrar';
    }
}

function entrar(evento) {
    evento.preventDefault();
    const formulario = evento.currentTarget;

    if (!formulario.checkValidity()) {
        formulario.classList.add("was-validated");
        mostrarMensagemAuth("error", "Selecione um usuário para continuar.");
        return;
    }

    const matricula = elementoAuth("usuarioExistente").value;
    const usuario = usuariosCadastrados.find(item => String(item.matricula) === matricula);
    guardarSessao(usuario);
    redirecionarPerfil(`Olá, ${usuario.nome}! Abrindo seu perfil...`);
}

async function iniciarLogin() {
    try {
        usuariosCadastrados = await buscarUsuarios();
        preencherUsuarios();
    } catch (erro) {
        console.error("Erro ao carregar usuários:", erro);
        mostrarMensagemAuth("error", "Não foi possível carregar os usuários. Verifique se a API está em execução.");
    }

    document.querySelectorAll(".auth-tab").forEach(botao => {
        botao.addEventListener("click", () => selecionarModoAuth(botao.dataset.authMode));
    });
    elementoAuth("formEntrar").addEventListener("submit", entrar);
    elementoAuth("formCriarConta").addEventListener("submit", cadastrarUsuario);
}

document.addEventListener("DOMContentLoaded", iniciarLogin);
