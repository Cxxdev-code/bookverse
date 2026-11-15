package com.cxxdev.bookverse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cxxdev.bookverse.dto.request.LoginDto;
import com.cxxdev.bookverse.dto.request.RegistroDto;
import com.cxxdev.bookverse.dto.response.AutenticacaoResponse;
import com.cxxdev.bookverse.service.AutenticacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AutenticacaoController {
    private final AutenticacaoService autenticacaoService;

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public AutenticacaoResponse registrar(@Valid @RequestBody RegistroDto dto) { return autenticacaoService.registrar(dto); }

    @PostMapping("/login")
    public AutenticacaoResponse entrar(@Valid @RequestBody LoginDto dto) { return autenticacaoService.entrar(dto); }
}
