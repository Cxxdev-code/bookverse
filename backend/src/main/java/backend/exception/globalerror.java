package backend.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import backend.exception.livro.ListaDeLivrosVaziaException;
import backend.exception.livro.LivroJaExistenteException;
import backend.exception.livro.LivroNaoEncontradoPorIdExcption;
import backend.exception.livro.LivroNaoEncontradoPorTituloException;

@RestControllerAdvice
public class globalerror {

    @ExceptionHandler(ListaDeLivrosVaziaException.class)
    public ResponseEntity<Map<String, Object>> handleListaDeLivrosVaziaException(ListaDeLivrosVaziaException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LivroJaExistenteException.class)
    public ResponseEntity<Map<String, Object>> handleLivroJaExistenteException(LivroJaExistenteException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(LivroNaoEncontradoPorIdExcption.class)
    public ResponseEntity<Map<String, Object>> handleLivroNaoEncontradoPorIdExcption(LivroNaoEncontradoPorIdExcption ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LivroNaoEncontradoPorTituloException.class)
    public ResponseEntity<Map<String, Object>> handleLivroNaoEncontradoPorTituloException(LivroNaoEncontradoPorTituloException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
