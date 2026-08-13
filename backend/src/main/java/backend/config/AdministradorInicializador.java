package backend.config;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import backend.Entity.PapelUsuario;
import backend.Entity.UsuarioEntity;
import backend.repository.UsuarioRepository;

@Configuration
public class AdministradorInicializador {
    @Bean
    ApplicationRunner criarAdministradorPadrao(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            @Value("${bookverse.admin.email}") String email, @Value("${bookverse.admin.senha}") String senha,
            @Value("${bookverse.admin.nome}") String nome) {
        return argumentos -> {
            String emailNormalizado = email.trim().toLowerCase(Locale.ROOT);
            if (usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)) return;
            UsuarioEntity admin = UsuarioEntity.builder().nome(nome.trim()).email(emailNormalizado)
                    .senhaHash(passwordEncoder.encode(senha)).sexo("Não informado")
                    .dataNascimento(LocalDate.of(2000, 1, 1))
                    .matricula((int) usuarioRepository.countAllByMatriculaIsNotNull() + 3)
                    .papel(PapelUsuario.ADMIN).ativo(true).build();
            usuarioRepository.save(admin);
        };
    }
}
