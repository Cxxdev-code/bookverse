package backend.dto.Request;

import java.time.LocalDate;

import backend.Entity.StatusLivro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class LivroDto {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 5000, message = "A descriçao deve ter no máximo 5000 caracteres")
    private String descricao;

    @NotNull(message = "A data de publicacao e obrigatoria")
    private LocalDate publicado;

    @NotBlank(message = "O ISBN é obrigatório")
    private String isbn;

    @NotNull(message = "O ID do autor é obrigatório")
    private Integer autorId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Integer categoriaId;

    @Size(max = 2048, message = "A URL da capa deve ter no mÃ¡ximo 2048 caracteres")
    private String capaUrl;

    @Size(max = 2048, message = "O link de leitura deve conter no máximo 2048 caracteres")
    @Pattern(regexp = "https?://\\S+", message = "O link de leitura deve usar http:// ou https://")
    private String urlLeitura;

    @jakarta.validation.constraints.Positive(message = "O nÃºmero de pÃ¡ginas deve ser maior que zero")
    private Integer numeroPaginas;

    @Size(max = 100, message = "O idioma deve ter no mÃ¡ximo 100 caracteres")
    private String idioma;

    @Size(max = 255, message = "A editora deve ter no mÃ¡ximo 255 caracteres")
    private String editora;

    @Size(max = 100, message = "A ediÃ§Ã£o deve ter no mÃ¡ximo 100 caracteres")
    private String edicao;

    @Size(max = 20, message = "A classificaÃ§Ã£o etÃ¡ria deve ter no mÃ¡ximo 20 caracteres")
    private String classificacaoEtaria;

    private StatusLivro status;

    private Boolean destaque;
}
