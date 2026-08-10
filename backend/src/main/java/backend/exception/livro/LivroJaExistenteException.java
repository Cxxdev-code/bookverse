package backend.exception.livro;

public class LivroJaExistenteException extends RuntimeException {
    
    public LivroJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
