package backend.exception.autor;

public class AutorIdNaoEncontrado extends RuntimeException{
    
    public AutorIdNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
