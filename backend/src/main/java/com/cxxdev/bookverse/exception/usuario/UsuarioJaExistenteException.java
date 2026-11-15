package com.cxxdev.bookverse.exception.usuario;

public class UsuarioJaExistenteException extends RuntimeException {
    public UsuarioJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
