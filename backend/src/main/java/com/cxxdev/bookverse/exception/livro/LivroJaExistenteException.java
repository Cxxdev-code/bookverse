package com.cxxdev.bookverse.exception.livro;

public class LivroJaExistenteException extends RuntimeException {
    
    public LivroJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
