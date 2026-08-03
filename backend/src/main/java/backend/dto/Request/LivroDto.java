package backend.dto.Request;

import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class LivroDto {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private LocalDate publicado;

    @NotBlank(message = "O ISBN é obrigatório")
    private String isbn;

    @NotNull(message = "O ID do autor é obrigatório")
    private Integer autorId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Integer categoriaId;
}