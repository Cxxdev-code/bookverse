package backend.exception.livro;

public class LivroNaoEncontradoPorTituloException extends RuntimeException {

    public LivroNaoEncontradoPorTituloException(String titulo) {
        super("Livro não encontrado com o título: " + titulo);
    }
    
}
