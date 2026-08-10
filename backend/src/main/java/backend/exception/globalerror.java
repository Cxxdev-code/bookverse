package backend.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import backend.exception.autor.AutorIdNaoEncontrado;
import backend.exception.autor.AutorJaExistenteException;
import backend.exception.categoria.CategoriaJaExistenteException;
import backend.exception.categoria.CategoriaNaoEncontradaException;
import backend.exception.livro.ListaDeLivrosVaziaException;
import backend.exception.livro.LivroJaExistenteException;
import backend.exception.livro.LivroNaoEncontradoPorIdExcption;
import backend.exception.livro.LivroNaoEncontradoPorTituloException;
import backend.exception.usuario.UsuarioIdNaoEncontradoException;
import jakarta.validation.ConstraintViolationException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.putIfAbsent(erro.getField(), erro.getDefaultMessage()));

        return buildResponse(HttpStatus.BAD_REQUEST, "Existem campos inválidos.", campos);
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            RequisicaoInvalidaException.class
    })
    public ResponseEntity<Map<String, Object>> handleRequisicaoInvalida(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Dados da solicitação são inválidos.");
    }

    @ExceptionHandler({
            AutorIdNaoEncontrado.class,
            CategoriaNaoEncontradaException.class,
            UsuarioIdNaoEncontradoException.class
    })
    public ResponseEntity<Map<String, Object>> handleRecursoNaoEncontrado(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            AutorJaExistenteException.class,
            CategoriaJaExistenteException.class,
            RecursoEmUsoException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflito(RuntimeException ex) {
        String mensagem = ex instanceof DataIntegrityViolationException
                ? "A operação viola uma regra de integridade dos dados."
                : ex.getMessage();

        return buildResponse(HttpStatus.CONFLICT, mensagem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        return buildResponse(status, message, null);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String message,
            Map<String, String> campos) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (campos != null && !campos.isEmpty()) {
            body.put("fields", campos);
        }
        return ResponseEntity.status(status).body(body);
    }
}
