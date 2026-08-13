import { definirTexto, formatarData, porId } from "../core/dom.js";

function imagemSegura(url) {
    return /^(https?:\/\/|assets\/)/i.test(String(url || "").trim()) ? String(url).trim() : null;
}

export function renderizarPerfil(usuario, estatisticas) {
    definirTexto("nomeUsuario", usuario.nome || "Leitor BookVerse");
    definirTexto("matriculaUsuario", usuario.matricula || "—");
    definirTexto("sexoUsuario", usuario.sexo || "Não informado");
    definirTexto("nascimentoUsuario", formatarData(usuario.dataNascimento));
    definirTexto("idadeUsuario", usuario.idade ? `${usuario.idade} anos` : "Não informada");
    definirTexto("emailUsuario", usuario.email || "Não informado");
    definirTexto("idUsuario", usuario.id ? `#${usuario.id}` : "");
    if (estatisticas) {
        definirTexto("livrosPerfil", estatisticas.livros);
        definirTexto("autoresPerfil", estatisticas.autores);
        definirTexto("categoriasPerfil", estatisticas.categorias);
    }
    const imagem = porId("imagemPerfil");
    const icone = porId("iconePerfil");
    const url = imagemSegura(usuario.imagemPerfilUrl);
    if (imagem && icone) {
        imagem.classList.toggle("d-none", !url);
        icone.classList.toggle("d-none", Boolean(url));
        if (url) imagem.src = url;
        else imagem.removeAttribute("src");
    }
    porId("conteudoPerfil")?.classList.remove("d-none");
}

export function preencherFormularioPerfil(usuario) {
    if (porId("nomePerfilEdicao")) porId("nomePerfilEdicao").value = usuario.nome || "";
    if (porId("sexoPerfilEdicao")) porId("sexoPerfilEdicao").value = usuario.sexo || "";
    if (porId("nascimentoPerfilEdicao")) porId("nascimentoPerfilEdicao").value = usuario.dataNascimento || "";
    if (porId("imagemPerfilEdicao")) porId("imagemPerfilEdicao").value = usuario.imagemPerfilUrl || "";
}
