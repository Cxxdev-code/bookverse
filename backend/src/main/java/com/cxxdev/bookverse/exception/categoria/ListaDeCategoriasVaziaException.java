package com.cxxdev.bookverse.exception.categoria;

public class ListaDeCategoriasVaziaException extends RuntimeException {
    public ListaDeCategoriasVaziaException() {
        super("Nenhuma categoria foi encontrada.");
    }
}