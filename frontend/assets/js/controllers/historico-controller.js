import { UsuarioModel } from "../models/usuario-model.js";
import { escaparHtml, porId } from "../core/dom.js";
import { renderizarHistoricoUsuarios } from "../views/historico-view.js";

export async function iniciarHistorico() {
    const carregando = porId("carregandoHistorico");
    const lista = porId("listaHistoricoUsuarios");
    try {
        const usuarios = await UsuarioModel.listarHistorico();
        carregando?.classList.add("d-none");
        renderizarHistoricoUsuarios(usuarios);
    } catch (erro) {
        carregando?.classList.add("d-none");
        if (lista) lista.innerHTML = `<div class="history-empty"><i class="bi bi-exclamation-circle"></i><h2>Não foi possível carregar o histórico</h2><p>${escaparHtml(erro.message)}</p></div>`;
    }
}
