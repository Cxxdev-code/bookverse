import { requisicao } from "../core/api-client.js";

export const UsuarioModel = {
    listar: () => requisicao("/usuarios"),
    buscarPorId: (id) => requisicao(`/usuarios/${id}`),
    buscarPorNome: (nome) => requisicao(`/usuarios?nome=${encodeURIComponent(nome)}`),
    criar: (usuario) => requisicao("/usuarios", { method: "POST", body: usuario }),
    atualizar: (id, usuario) => requisicao(`/usuarios/${id}`, { method: "PUT", body: usuario }),
    remover: (id) => requisicao(`/usuarios/${id}`, { method: "DELETE" })
};
