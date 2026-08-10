function atualizarPerfil(id, valor) {
    const elemento = document.getElementById(id);
    if (elemento) elemento.textContent = valor;
}

function formatarData(data) {
    if (!data) return "Não informada";

    return new Date(`${data}T00:00:00`).toLocaleDateString("pt-BR", {
        timeZone: "UTC"
    });
}

async function carregarPerfil() {
    const carregando = document.getElementById("carregandoPerfil");
    const conteudo = document.getElementById("conteudoPerfil");
    const perfilVazio = document.getElementById("perfilVazio");

    try {
        const [usuarios, livros, autores, categorias] = await Promise.all([
            buscarUsuarios(),
            buscarLivros(),
            buscarAutores(),
            buscarCategorias()
        ]);

        carregando.classList.add("d-none");

        if (usuarios.length === 0) {
            perfilVazio.classList.remove("d-none");
            return;
        }

        // Enquanto não há tokens, a matrícula salva durante o login identifica
        // o perfil atual. Sem login, mantém o primeiro usuário cadastrado.
        const matriculaAtiva = sessionStorage.getItem("bookverseUsuarioMatricula");
        const usuario = usuarios.find(item => String(item.matricula) === matriculaAtiva) || usuarios[0];
        atualizarPerfil("nomeUsuario", usuario.nome || "Leitor BookVerse");
        atualizarPerfil("matriculaUsuario", usuario.matricula || "—");
        atualizarPerfil("sexoUsuario", usuario.sexo || "Não informado");
        atualizarPerfil("nascimentoUsuario", formatarData(usuario.dataNascimento));
        atualizarPerfil("idadeUsuario", usuario.idade ? `${usuario.idade} anos` : "Não informada");
        atualizarPerfil("livrosPerfil", livros.length);
        atualizarPerfil("autoresPerfil", autores.length);
        atualizarPerfil("categoriasPerfil", categorias.length);

        conteudo.classList.remove("d-none");
    } catch (erro) {
        console.error("Erro ao carregar perfil:", erro);
        carregando.classList.add("d-none");
        perfilVazio.classList.remove("d-none");
        perfilVazio.innerHTML = `
            <i class="bi bi-exclamation-circle display-1"></i>
            <h2>Não foi possível carregar o perfil</h2>
            <p>Verifique se a API está em execução e tente novamente.</p>
        `;
    }
}

document.addEventListener("DOMContentLoaded", carregarPerfil);
