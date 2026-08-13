package backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.CategoriaDto;
import backend.dto.Response.CategoriaResponse;
import backend.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/api/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaResponse> buscarTodasAsCategorias() {
        return categoriaService.buscarTodasAsCategorias();
    }

    @PostMapping
    public CategoriaResponse adicionarCategoria(@Valid @RequestBody CategoriaDto categoriaDto) {
        return categoriaService.adicionarCategoria(categoriaDto);
    }

    @GetMapping("/{id}")
    public CategoriaResponse buscarCategoriaPorId(@PathVariable("id") Integer id) {
        return categoriaService.buscarCategoriaPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaResponse atualizarCategoria(@PathVariable("id") Integer id, @Valid @RequestBody CategoriaDto categoriaDto) {
        return categoriaService.editarCategoria(id, categoriaDto);
    }

    @DeleteMapping("/{id}")
    public CategoriaResponse deletarCategoria(@PathVariable("id") Integer id) {
        return categoriaService.deletarCategoria(id);
    }
}
