import { definirTexto, escaparHtml, formatarData, porId } from "../core/dom.js";

export function renderizarPerfil(usuario, estatisticas) {
    definirTexto("nomeUsuario", usuario.nome || "Leitor BookVerse");
    definirTexto("matriculaUsuario", usuario.matricula || "—");
    definirTexto("sexoUsuario", usuario.sexo || "Não informado");
    definirTexto("nascimentoUsuario", formatarData(usuario.dataNascimento));
    definirTexto("idadeUsuario", usuario.idade ? `${usuario.idade} anos` : "Não informada");
    definirTexto("livrosPerfil", estatisticas.livros);
    definirTexto("autoresPerfil", estatisticas.autores);
    definirTexto("categoriasPerfil", estatisticas.categorias);
    definirTexto("idUsuario", usuario.id ? `#${usuario.id}` : "");
    porId("conteudoPerfil")?.classList.remove("d-none");
}

export function renderizarOpcoesUsuarios(usuarios) {
    const select = porId("usuarioExistente");
    if (!select) return;

    select.innerHTML = '<option value="">Selecione seu nome</option>' + usuarios
        .map(usuario => `<option value="${escaparHtml(usuario.matricula)}">${escaparHtml(usuario.nome)} — matrícula ${escaparHtml(usuario.matricula)}</option>`)
        .join("");
}
