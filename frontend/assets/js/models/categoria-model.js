import { requisicao } from "../core/api-client.js?v=production-3";

export const CategoriaModel = {
    listar: () => requisicao("/categorias"),
    buscarPorId: (id) => requisicao(`/categorias/${id}`),
    criar: (categoria) => requisicao("/categorias", { method: "POST", body: categoria }),
    atualizar: (id, categoria) => requisicao(`/categorias/${id}`, { method: "PUT", body: categoria }),
    remover: (id) => requisicao(`/categorias/${id}`, { method: "DELETE" })
};
