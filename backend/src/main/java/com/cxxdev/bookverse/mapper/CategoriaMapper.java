package com.cxxdev.bookverse.mapper;

import java.util.List;
import org.springframework.stereotype.Component;

import com.cxxdev.bookverse.entity.CategoriaEntity;
import com.cxxdev.bookverse.dto.response.CategoriaResponse;

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
