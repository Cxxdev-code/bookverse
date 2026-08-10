package backend.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDestaqueResponse {
    private Integer id;
    private String nome;
    private String descricao;
    private long quantidadeLivros;
}
