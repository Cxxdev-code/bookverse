package backend.service;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.Entity.PapelUsuario;
import backend.Entity.UsuarioEntity;
import backend.dto.Request.LoginDto;
import backend.dto.Request.RegistroDto;
import backend.dto.Response.AutenticacaoResponse;
import backend.exception.usuario.CredenciaisInvalidasException;
import backend.exception.usuario.UsuarioJaExistenteException;
import backend.mapper.UsuarioMapper;
import backend.repository.UsuarioRepository;
import backend.security.JwtTokenService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutenticacaoService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AutenticacaoResponse registrar(RegistroDto dto) {
        String email = normalizarEmail(dto.getEmail());
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new UsuarioJaExistenteException("Já existe uma conta cadastrada com este e-mail.");
        }
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome(dto.getNome().trim()).email(email).senhaHash(passwordEncoder.encode(dto.getSenha()))
                .sexo(dto.getSexo().trim()).dataNascimento(dto.getDataNascimento())
                .imagemPerfilUrl(limpar(dto.getImagemPerfilUrl())).matricula(usuarioService.gerarMatricula())
                .papel(PapelUsuario.USUARIO).ativo(true).build();
        return respostaAutenticada(usuarioRepository.save(usuario));
    }

    public AutenticacaoResponse entrar(LoginDto dto) {
        String email = normalizarEmail(dto.getEmail());
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(email, dto.getSenha()));
        } catch (AuthenticationException exception) {
            throw new CredenciaisInvalidasException();
        }
        UsuarioEntity usuario = usuarioRepository.findByEmailIgnoreCase(email).orElseThrow(CredenciaisInvalidasException::new);
        usuario.setUltimoAcessoEm(LocalDateTime.now());
        return respostaAutenticada(usuarioRepository.save(usuario));
    }

    private AutenticacaoResponse respostaAutenticada(UsuarioEntity usuario) {
        return AutenticacaoResponse.builder().token(jwtTokenService.gerarToken(usuario)).tipo("Bearer")
                .expiraEmSegundos(jwtTokenService.getExpiracaoEmSegundos())
                .usuario(usuarioMapper.converterParaResponse(usuario)).build();
    }

    private String normalizarEmail(String email) { return email.trim().toLowerCase(Locale.ROOT); }
    private String limpar(String valor) { return valor == null || valor.isBlank() ? null : valor.trim(); }
}
