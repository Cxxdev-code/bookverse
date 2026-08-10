import { LivroModel } from "../models/livro-model.js";
import { UsuarioModel } from "../models/usuario-model.js";
import { obterMatriculaAtiva } from "../core/session.js";
import { escaparHtml, porId } from "../core/dom.js";
import { renderizarPerfil } from "../views/usuario-view.js";

async function iniciar() {
    const carregando = porId("carregandoPerfil");
    const vazio = porId("perfilVazio");
    try {
        const [usuarios, home] = await Promise.all([UsuarioModel.listar(), LivroModel.carregarHome()]);
        carregando?.classList.add("d-none");
        if (usuarios.length === 0) {
            vazio?.classList.remove("d-none");
            return;
        }
        const matricula = obterMatriculaAtiva();
        const usuario = usuarios.find(item => String(item.matricula) === matricula) || usuarios[0];
        renderizarPerfil(usuario, home.totais || { livros: 0, autores: 0, categorias: 0 });
    } catch (erro) {
        console.error(erro);
        carregando?.classList.add("d-none");
        vazio?.classList.remove("d-none");
        vazio.innerHTML = `<i class="bi bi-exclamation-circle display-1"></i><h2>Não foi possível carregar o perfil</h2><p>${escaparHtml(erro.message)}</p>`;
    }
}

export function iniciarPerfil() {
    return iniciar();
}
