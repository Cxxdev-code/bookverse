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
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarPerfilDto {
    @NotBlank(message = "Informe seu nome.")
    @Size(max = 255)
    private String nome;

    @NotBlank(message = "Informe o sexo ou selecione a opção correspondente.")
    @Size(max = 100)
    private String sexo;

    @NotNull(message = "Informe a data de nascimento.")
    private LocalDate dataNascimento;

    @Size(max = 2048)
    private String imagemPerfilUrl;
}
