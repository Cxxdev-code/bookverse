package backend.exception.usuario;

public class UsuarioIdNaoEncontradoException extends RuntimeException {
    public UsuarioIdNaoEncontradoException(Integer id) {
        super("Usuário não encontrado com ID: " + id);
    }
    
}
