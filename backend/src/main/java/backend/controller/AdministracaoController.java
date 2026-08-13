package backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Response.UsuarioResponse;
import backend.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdministracaoController {
    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<UsuarioResponse> historicoDeUsuarios() { return usuarioService.buscarHistoricoDeUsuarios(); }
}
