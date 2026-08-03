package backend.controller;



import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.AutorDto;
import backend.dto.Response.AutorResponse;
import backend.service.AutorService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/autores")
@AllArgsConstructor
public class AutorController {

    private final AutorService autorService;
    
    @PostMapping
    public AutorResponse criarAutor(@Valid @RequestBody AutorDto autorDto){
        return autorService.criarAutor(autorDto);
    }

    @GetMapping
    public List<AutorResponse> buscarAutores(){
        return autorService.buscarTodosAutores();
    }

    @GetMapping("/{id}")
    public AutorResponse buscarAutoresId(@PathVariable("id") Integer id){
        return autorService.buscarPorId(id);
    }

    @GetMapping(params = "nome")
    public List<AutorResponse> buscarAutoresNomes(@RequestParam String nome){
        return autorService.buscarPorNome(nome);
    }
}
