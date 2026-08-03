package backend.exception.autor;

public class AutorJaExistenteException extends RuntimeException {
    
    public AutorJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
