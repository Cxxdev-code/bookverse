package backend.dto.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import backend.Entity.StatusLivro;
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
    private String capaUrl;
    private String urlLeitura;
    private Integer numeroPaginas;
    private String idioma;
    private String editora;
    private String edicao;
    private String classificacaoEtaria;
    private StatusLivro status;
    private Boolean destaque;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
