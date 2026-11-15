package com.cxxdev.bookverse.exception.livro;

public class LivroNaoEncontradoPorIdException  extends RuntimeException {

    public LivroNaoEncontradoPorIdException(Integer  id) {
        super("Livro não encontrado com o ID: " + id);
    }
    
}
