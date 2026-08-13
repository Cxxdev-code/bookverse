import { requisicao } from "../core/api-client.js";

export const LivroModel = {
    listarCatalogo: ({ page = 0, size = 12, busca = "", categoriaId, autorId, ordem = "recentes" } = {}) => {
        const parametros = new URLSearchParams({ page: String(page), size: String(size), ordem });
        if (busca?.trim()) parametros.set("busca", busca.trim());
        if (categoriaId) parametros.set("categoriaId", String(categoriaId));
        if (autorId) parametros.set("autorId", String(autorId));
        return requisicao(`/livros?${parametros.toString()}`);
    },
    carregarHome: () => requisicao("/home"),
    listar: () => requisicao("/livros/todos"),
    buscarPorId: (id) => requisicao(`/livros/${id}`),
    buscarPorTitulo: (titulo) => requisicao(`/livros?titulo=${encodeURIComponent(titulo)}`),
    criar: (livro) => requisicao("/livros", { method: "POST", body: livro }),
    atualizar: (id, livro) => requisicao(`/livros/${id}`, { method: "PUT", body: livro }),
    remover: (id) => requisicao(`/livros/${id}`, { method: "DELETE" })
};
