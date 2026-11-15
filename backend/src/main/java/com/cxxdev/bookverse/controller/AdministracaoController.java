package com.cxxdev.bookverse.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxxdev.bookverse.dto.response.UsuarioResponse;
import com.cxxdev.bookverse.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdministracaoController {
    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<UsuarioResponse> historicoDeUsuarios() { return usuarioService.buscarHistoricoDeUsuarios(); }
}
