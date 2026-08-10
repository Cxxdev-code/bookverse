import { requisicao } from "../core/api-client.js";

export const AutorModel = {
    listar: () => requisicao("/autores/todos"),
    buscarPorId: (id) => requisicao(`/autores/${id}`),
    buscarPorNome: (nome) => requisicao(`/autores?nome=${encodeURIComponent(nome)}`),
    criar: (autor) => requisicao("/autores", { method: "POST", body: autor }),
    atualizar: (id, autor) => requisicao(`/autores/${id}`, { method: "PUT", body: autor }),
    remover: (id) => requisicao(`/autores/${id}`, { method: "DELETE" })
};
