package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.LivroDto;
import backend.dto.Response.LivroResponse;
import backend.service.BibliotecaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/livros")
@AllArgsConstructor

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
    public LivroResponse buscarLivroPorId(@PathVariable("id") Integer id) {

        return bibliotecaService.buscarLivroPorId(id);
    }

    @GetMapping(params = "titulo")
    public List<LivroResponse> buscarPorTitulo(@RequestParam  String titulo) {

        return bibliotecaService.buscarLivroPorTitulo(titulo);
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
