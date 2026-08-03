package backend.exception.usuario;

public class UsuarioNaoEncontradoPeloNomeException  extends RuntimeException {
    public UsuarioNaoEncontradoPeloNomeException(String message) {
        super(message);
    }
    
}
