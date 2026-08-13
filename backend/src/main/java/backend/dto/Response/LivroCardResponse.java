package backend.dto.Response;

import java.time.LocalDate;

import backend.Entity.StatusLivro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Dados leves para exibir um livro em listas e cards. */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroCardResponse {
    private Integer id;
    private String titulo;
    private String autor;
    private Integer autorId;
    private String categoria;
    private Integer categoriaId;
    private String capaUrl;
    private String descricaoResumo;
    private LocalDate publicado;
    private Integer numeroPaginas;
    private StatusLivro status;
    private Boolean destaque;
}
