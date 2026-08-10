package backend.mapper;

import java.util.List;
import org.springframework.stereotype.Component;

import backend.Entity.CategoriaEntity;
import backend.dto.Response.CategoriaResponse;

@Component
public class CategoriaMapper {

    public CategoriaResponse converterParaResponse(CategoriaEntity categoria) {
        return converterParaResponse(categoria, 0L);
    }

    public CategoriaResponse converterParaResponse(CategoriaEntity categoria, Long quantidadeLivros) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .quantidadeLivros(quantidadeLivros)
                .build();
    }

    public List<CategoriaResponse> converterParaListaDeResponse(List<CategoriaEntity> categorias) {
        return categorias.stream()
                .map(this::converterParaResponse)
                .toList();
    }
}
