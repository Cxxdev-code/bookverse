package backend.dto.Request;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor   
public class UsuarioDto {
    
    @NotBlank
    private String nome;

    @NotBlank
    private String sexo;
    
    @NotNull 
    private LocalDate dataNascimento;

}
