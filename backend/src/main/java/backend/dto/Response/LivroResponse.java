package backend.dto.Response;

import java.time.LocalDate;

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
public class LivroResponse {
    
    private Integer id;
    private String titulo;
    private String descricao;
    private Integer autorId;
    private String autor;
    private String isbn;      
    private Integer categoriaId;
    private String categoria;  
    private LocalDate publicado;
}
