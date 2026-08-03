package backend.exception.autor;

public class ListaDeAutoresVaziaException extends RuntimeException{
   public ListaDeAutoresVaziaException(String mensagem) {
        super(mensagem);
    } 
}
