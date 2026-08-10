package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.LivroDto;
import backend.dto.Response.LivroResponse;
import backend.dto.Response.LivroCardResponse;
import backend.dto.Response.LivroDetalheResponse;
import backend.dto.Response.PageResponse;
import backend.service.BibliotecaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/livros")
@AllArgsConstructor
@Validated

public class BibliotecaController {
    
    private final BibliotecaService bibliotecaService;

    @GetMapping("/todos")
    public List<LivroResponse> buscarTodosOsLivros() {

        return bibliotecaService.buscarTodosOsLivros();
    }

    @PostMapping
    public LivroResponse adicionarLivro(@Valid @RequestBody  LivroDto livrodto) {

        return bibliotecaService.adicionarLivro(livrodto);
    }

    @GetMapping("/{id}")
    public LivroDetalheResponse buscarLivroPorId(@PathVariable("id") Integer id) {

        return bibliotecaService.buscarLivroPorId(id);
    }

    @GetMapping(params = "titulo")
    public List<LivroResponse> buscarPorTitulo(@RequestParam  String titulo) {

        return bibliotecaService.buscarLivroPorTitulo(titulo);
    }

    /**
     * Catálogo público para as telas novas. A resposta inclui metadados de paginação.
     */
    @GetMapping
    public PageResponse<LivroCardResponse> buscarCatalogo(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "page não pode ser negativo") int page,
            @RequestParam(defaultValue = "12") @Min(value = 1, message = "size deve ser maior que zero")
                    @Max(value = 50, message = "size deve ser no máximo 50") int size,
            @RequestParam(defaultValue = "") String busca,
            @RequestParam(required = false) @Min(value = 1, message = "categoriaId deve ser maior que zero") Integer categoriaId,
            @RequestParam(required = false) @Min(value = 1, message = "autorId deve ser maior que zero") Integer autorId,
            @RequestParam(defaultValue = "recentes") String ordem) {
        return bibliotecaService.buscarCatalogo(page, size, busca, categoriaId, autorId, ordem);
    }

    @PutMapping("/{id}")
    public LivroResponse atualizarLivro(@PathVariable("id") Integer id, @Valid @RequestBody LivroDto livroDto) {
        
        return bibliotecaService.editarLivro(id, livroDto);
    }

    @DeleteMapping("/{id}")
    public LivroResponse deletarLivro(@PathVariable("id") Integer id) {

        return bibliotecaService.deletarLivro(id);
    }
}
