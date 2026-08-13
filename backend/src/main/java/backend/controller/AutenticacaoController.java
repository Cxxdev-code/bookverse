package backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.Request.LoginDto;
import backend.dto.Request.RegistroDto;
import backend.dto.Response.AutenticacaoResponse;
import backend.service.AutenticacaoService;
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
