import { requisicao } from "../core/api-client.js";

export const LivroModel = {
    listar: () => requisicao("/livros/todos"),
    buscarPorId: (id) => requisicao(`/livros/${id}`),
    buscarPorTitulo: (titulo) => requisicao(`/livros?titulo=${encodeURIComponent(titulo)}`),
    criar: (livro) => requisicao("/livros", { method: "POST", body: livro }),
    atualizar: (id, livro) => requisicao(`/livros/${id}`, { method: "PUT", body: livro }),
    remover: (id) => requisicao(`/livros/${id}`, { method: "DELETE" })
};
