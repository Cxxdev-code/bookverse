package com.cxxdev.bookverse.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cxxdev.bookverse.dto.request.LoginDto;
import com.cxxdev.bookverse.dto.request.RegistroDto;
import com.cxxdev.bookverse.dto.response.AutenticacaoResponse;
import com.cxxdev.bookverse.dto.response.UsuarioResponse;
import com.cxxdev.bookverse.entity.PapelUsuario;
import com.cxxdev.bookverse.entity.UsuarioEntity;
import com.cxxdev.bookverse.exception.usuario.CredenciaisInvalidasException;
import com.cxxdev.bookverse.exception.usuario.UsuarioJaExistenteException;
import com.cxxdev.bookverse.mapper.UsuarioMapper;
import com.cxxdev.bookverse.repository.UsuarioRepository;
import com.cxxdev.bookverse.security.JwtTokenService;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Captor
    private ArgumentCaptor<UsuarioEntity> usuarioCaptor;

    private RegistroDto registroDto;

    @BeforeEach
    void setUp() {
        registroDto = RegistroDto.builder()
                .nome("  Maria Silva  ")
                .email("  MARIA@Email.COM ")
                .senha("senhaSegura123")
                .sexo(" F ")
                .dataNascimento(LocalDate.of(2000, 5, 10))
                .build();
    }

    @Test
    @DisplayName("registrar deve rejeitar e-mail já cadastrado")
    void registrarDeveRejeitarEmailJaCadastrado() {
        when(usuarioRepository.existsByEmailIgnoreCase("maria@email.com")).thenReturn(true);

        assertThatThrownBy(() -> autenticacaoService.registrar(registroDto))
                .isInstanceOf(UsuarioJaExistenteException.class);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("registrar deve normalizar dados e criar usuário com papel USUARIO")
    void registrarDeveNormalizarDadosECriarUsuarioComum() {
        when(usuarioRepository.existsByEmailIgnoreCase("maria@email.com")).thenReturn(false);
        when(passwordEncoder.encode("senhaSegura123")).thenReturn("hash-seguro");
        when(usuarioService.gerarMatricula()).thenReturn(20260001);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jwtTokenService.gerarToken(any(UsuarioEntity.class))).thenReturn("token-jwt");
        when(jwtTokenService.getExpiracaoEmSegundos()).thenReturn(7200L);
        when(usuarioMapper.converterParaResponse(any(UsuarioEntity.class)))
                .thenReturn(mock(UsuarioResponse.class));

        AutenticacaoResponse resposta = autenticacaoService.registrar(registroDto);

        verify(usuarioRepository).save(usuarioCaptor.capture());
        UsuarioEntity salvo = usuarioCaptor.getValue();
        assertThat(salvo.getEmail()).isEqualTo("maria@email.com");
        assertThat(salvo.getNome()).isEqualTo("Maria Silva");
        assertThat(salvo.getSenhaHash()).isEqualTo("hash-seguro");
        assertThat(salvo.getPapel()).isEqualTo(PapelUsuario.USUARIO);
        assertThat(salvo.getAtivo()).isTrue();
        assertThat(resposta.getToken()).isEqualTo("token-jwt");
        assertThat(resposta.getTipo()).isEqualTo("Bearer");
        assertThat(resposta.getExpiraEmSegundos()).isEqualTo(7200L);
    }

    @Test
    @DisplayName("entrar deve converter falha de autenticação em CredenciaisInvalidasException")
    void entrarDeveConverterFalhaDeAutenticacao() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("credenciais inválidas"));

        LoginDto login = new LoginDto("maria@email.com", "senhaErrada");

        assertThatThrownBy(() -> autenticacaoService.entrar(login))
                .isInstanceOf(CredenciaisInvalidasException.class);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("entrar deve registrar último acesso e devolver token")
    void entrarDeveRegistrarUltimoAcessoEDevolverToken() {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome("Maria Silva")
                .email("maria@email.com")
                .senhaHash("hash-seguro")
                .papel(PapelUsuario.USUARIO)
                .ativo(true)
                .build();
        when(usuarioRepository.findByEmailIgnoreCase("maria@email.com")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jwtTokenService.gerarToken(usuario)).thenReturn("token-jwt");
        when(jwtTokenService.getExpiracaoEmSegundos()).thenReturn(7200L);
        when(usuarioMapper.converterParaResponse(usuario)).thenReturn(mock(UsuarioResponse.class));

        AutenticacaoResponse resposta = autenticacaoService.entrar(new LoginDto(" MARIA@email.com ", "senha"));

        assertThat(usuario.getUltimoAcessoEm()).isNotNull();
        assertThat(resposta.getToken()).isEqualTo("token-jwt");
    }
}
