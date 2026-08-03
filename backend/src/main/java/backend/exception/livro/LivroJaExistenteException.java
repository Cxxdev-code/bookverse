package backend.exception.livro;

public class LivroJaExistenteException extends RuntimeException {
    
    public LivroJaExistenteException(String titulo) {
        super("Livro Já cadastrado com Titulo: " + titulo);
    }
}
