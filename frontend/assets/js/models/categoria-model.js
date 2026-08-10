import { requisicao } from "../core/api-client.js";

export const CategoriaModel = {
    listar: () => requisicao("/categorias"),
    buscarPorId: (id) => requisicao(`/categorias/${id}`),
    criar: (categoria) => requisicao("/categorias", { method: "POST", body: categoria }),
    atualizar: (id, categoria) => requisicao(`/categorias/${id}`, { method: "PUT", body: categoria }),
    remover: (id) => requisicao(`/categorias/${id}`, { method: "DELETE" })
};
