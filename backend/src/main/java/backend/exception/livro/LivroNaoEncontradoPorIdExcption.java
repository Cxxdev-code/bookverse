package backend.exception.livro;

public class LivroNaoEncontradoPorIdExcption  extends RuntimeException {

    public LivroNaoEncontradoPorIdExcption(Integer  id) {
        super("Livro não encontrado com o ID: " + id);
    }
    
}
