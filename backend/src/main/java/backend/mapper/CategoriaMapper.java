package backend.mapper;

import java.util.List;
import org.springframework.stereotype.Component;

import backend.Entity.CategoriaEntity;
import backend.dto.Response.CategoriaResponse;

@Component
public class CategoriaMapper {

    public CategoriaResponse converterParaResponse(CategoriaEntity categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .build();
    }

    public List<CategoriaResponse> converterParaListaDeResponse(List<CategoriaEntity> categorias) {
        return categorias.stream()
                .map(this::converterParaResponse)
                .toList();
    }
}