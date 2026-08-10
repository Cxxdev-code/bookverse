package backend.dto.Response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter 
@Setter
public class AutorResponse {
    private Integer id; 
    private String nome;
    private String biografia;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private Long quantidadeLivros;
}
