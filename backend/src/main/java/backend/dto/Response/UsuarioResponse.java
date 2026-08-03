package backend.dto.Response;



import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor 
@Setter
@Getter
@Builder
public class UsuarioResponse {
    
    @NotBlank
    private String nome;

    @NotBlank
    private String sexo;

    @NotNull
    private Integer idade;
    
    @NotNull 
    private LocalDate dataNascimento;

    @NotNull
    private Integer matricula;


}
