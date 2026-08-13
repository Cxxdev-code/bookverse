import { requisicao } from "../core/api-client.js";

export const UsuarioModel = {
    meuPerfil: () => requisicao("/usuarios/me"),
    atualizarMeuPerfil: usuario => requisicao("/usuarios/me", { method: "PUT", body: usuario }),
    listarHistorico: () => requisicao("/admin/usuarios"),
    listar: () => requisicao("/usuarios"),
    buscarPorId: (id) => requisicao(`/usuarios/${id}`),
    buscarPorNome: (nome) => requisicao(`/usuarios?nome=${encodeURIComponent(nome)}`),
    atualizar: (id, usuario) => requisicao(`/usuarios/${id}`, { method: "PUT", body: usuario }),
    remover: (id) => requisicao(`/usuarios/${id}`, { method: "DELETE" })
};
