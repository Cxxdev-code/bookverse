package backend.exception.livro;

public class ListaDeLivrosVaziaException extends RuntimeException {
    public ListaDeLivrosVaziaException() {
        super("Nenhum livro encontrado");
    }
}
