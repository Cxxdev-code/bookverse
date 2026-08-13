package backend.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutenticacaoResponse {
    private String token;
    private String tipo;
    private long expiraEmSegundos;
    private UsuarioResponse usuario;
}
