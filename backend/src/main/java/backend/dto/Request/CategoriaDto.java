package backend.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição da categoria é obrigatória")
    private String descricao;
}
