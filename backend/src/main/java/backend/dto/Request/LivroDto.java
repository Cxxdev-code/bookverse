package backend.dto.Request;

import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 5000, message = "A descriçao deve ter no máximo 5000 caracteres")
    private String descricao;

    @NotNull(message = "A data de publicacao e obrigatoria")
    private LocalDate publicado;

    @NotBlank(message = "O ISBN é obrigatório")
    private String isbn;

    @NotNull(message = "O ID do autor é obrigatório")
    private Integer autorId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Integer categoriaId;
}
