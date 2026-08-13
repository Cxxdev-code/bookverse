package backend.dto.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
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
public class RegistroDto {
    @NotBlank(message = "Informe seu nome.")
    @Size(max = 255)
    private String nome;

    @NotBlank(message = "Informe seu e-mail.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "Informe uma senha.")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @NotBlank(message = "Informe o sexo ou selecione a opção correspondente.")
    @Size(max = 100)
    private String sexo;

    @NotNull(message = "Informe a data de nascimento.")
    private LocalDate dataNascimento;

    @Size(max = 2048)
    private String imagemPerfilUrl;
}
