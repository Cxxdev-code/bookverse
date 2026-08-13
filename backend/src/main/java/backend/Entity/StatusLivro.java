package backend.Entity;

/**
 * Define em qual etapa editorial um livro se encontra.
 * Apenas livros PUBLICADO devem ser exibidos no catálogo público.
 */
public enum StatusLivro {
    RASCUNHO,
    EM_REVISAO,
    PUBLICADO,
    ARQUIVADO
}
