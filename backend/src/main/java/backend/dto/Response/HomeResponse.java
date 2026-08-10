package backend.dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {
    private HomeTotaisResponse totais;
    private List<LivroCardResponse> destaques;
    private List<LivroCardResponse> recentes;
    private List<CategoriaDestaqueResponse> categorias;
}
