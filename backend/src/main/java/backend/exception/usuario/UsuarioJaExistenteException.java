package backend.exception.usuario;

public class UsuarioJaExistenteException extends RuntimeException {
    public UsuarioJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
