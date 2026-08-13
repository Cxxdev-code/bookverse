package backend.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.AtualizarPerfilDto;
import backend.dto.Request.UsuarioDto;
import backend.dto.Response.UsuarioResponse;
import backend.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    /** Listagem administrativa. A autorização é configurada no SecurityConfig. */
    @GetMapping
    public List<UsuarioResponse> buscarUsuarios() { return usuarioService.buscarTodosUsuarios(); }

    @GetMapping("/me")
    public UsuarioResponse meuPerfil(Authentication authentication) {
        return usuarioService.buscarPorEmail(authentication.getName());
    }

    @PutMapping("/me")
    public UsuarioResponse atualizarMeuPerfil(Authentication authentication, @Valid @RequestBody AtualizarPerfilDto dto) {
        return usuarioService.atualizarPerfil(authentication.getName(), dto);
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarUsuarioPorId(@PathVariable Integer id) { return usuarioService.buscarUsuarioPorId(id); }

    @GetMapping(params = "nome")
    public List<UsuarioResponse> buscarUsuarioPorNome(@RequestParam String nome) {
        return usuarioService.buscarUsuarioPorNome(nome);
    }

    @PutMapping("/{id}")
    public UsuarioResponse editarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDto usuarioDto) {
        return usuarioService.editarUsuario(id, usuarioDto);
    }

    @DeleteMapping("/{id}")
    public UsuarioResponse deletarUsuario(@PathVariable Integer id) { return usuarioService.deletarUsuario(id); }
}
