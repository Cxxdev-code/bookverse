package backend.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import backend.Entity.PapelUsuario;
import backend.Entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {
    private final SecretKey chave;
    private final long expiracaoMinutos;

    public JwtTokenService(
            @Value("${bookverse.security.jwt.secret}") String segredo,
            @Value("${bookverse.security.jwt.expiracao-minutos}") long expiracaoMinutos) {
        if (segredo == null || segredo.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("BOOKVERSE_JWT_SECRET deve ter ao menos 32 caracteres.");
        }
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8));
        this.expiracaoMinutos = expiracaoMinutos;
    }

    public String gerarToken(UsuarioEntity usuario) {
        Instant agora = Instant.now();
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("papel", usuario.getPapel().name())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plus(expiracaoMinutos, ChronoUnit.MINUTES)))
                .signWith(chave)
                .compact();
    }

    public String extrairEmail(String token) {
        return lerClaims(token).getSubject();
    }

    public PapelUsuario extrairPapel(String token) {
        return PapelUsuario.valueOf(lerClaims(token).get("papel", String.class));
    }

    public boolean tokenValido(String token, String email) {
        return email.equalsIgnoreCase(extrairEmail(token)) && lerClaims(token).getExpiration().after(new Date());
    }

    public long getExpiracaoEmSegundos() {
        return expiracaoMinutos * 60;
    }

    private Claims lerClaims(String token) {
        return Jwts.parser().verifyWith(chave).build()
                .parseSignedClaims(token).getPayload();
    }
}
