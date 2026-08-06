package backend.controller;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.UsuarioDto;
import backend.dto.Response.UsuarioResponse;
import backend.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioResponse> buscarUsuario() {
        return usuarioService.buscarTodosUsuarios();
    }

    @PostMapping
    public UsuarioResponse criarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        return usuarioService.criarUsuario(usuarioDto);
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @GetMapping(params = "nome")
    public List<UsuarioResponse> buscarUsuarioPorNome(@RequestParam String nome){
        return usuarioService.buscarUsuarioPorNome(nome);
    }


    @PutMapping("/{id}")
    public UsuarioResponse editarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDto usuarioDto) {
        return usuarioService.editarUsuario(id, usuarioDto);
    }

    @DeleteMapping("/{id}")
    public UsuarioResponse deletarUsuario(@PathVariable Integer id) {
        return usuarioService.deletarUsuario(id);
    }
}