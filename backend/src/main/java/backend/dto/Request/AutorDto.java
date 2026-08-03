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

@Builder
@Getter
@Setter
@NoArgsConstructor // Necessário para o Jackson conseguir instanciar o objeto via JSON
@AllArgsConstructor
public class AutorDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;


    @Size(min = 30, max = 500, message = "A biografia deve ter entre 30 e 500 caracteres.")
    private String biografia;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotBlank(message = "A nacionalidade é obrigatória")
    private String nacionalidade;
}
