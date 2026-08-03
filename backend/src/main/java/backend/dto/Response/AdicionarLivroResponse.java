package backend.dto.Response;

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
public class AdicionarLivroResponse {

    @NotBlank 
    private String titulo;

    @NotBlank
    private String autor;
}
