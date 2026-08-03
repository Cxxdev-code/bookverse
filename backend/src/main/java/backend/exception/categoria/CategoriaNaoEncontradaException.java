package backend.exception.categoria;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(Integer id) {
        super("Categoria não encontrada com o ID: " + id);
    }
}