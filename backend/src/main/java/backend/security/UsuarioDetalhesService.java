package backend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.Entity.UsuarioEntity;
import backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioDetalhesService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        if (usuario.getSenhaHash() == null || usuario.getSenhaHash().isBlank()) {
            throw new UsernameNotFoundException("Usuário sem credencial de acesso.");
        }
        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenhaHash())
                .authorities("ROLE_" + usuario.getPapel().name())
                .disabled(!Boolean.TRUE.equals(usuario.getAtivo()))
                .build();
    }
}
