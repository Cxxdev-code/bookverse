package com.cxxdev.bookverse.exception.livro;

public class ListaDeLivrosVaziaException extends RuntimeException {
    public ListaDeLivrosVaziaException() {
        super("Nenhum livro encontrado");
    }
}
