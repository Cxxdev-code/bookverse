package backend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import backend.Entity.AutorEntity;
import backend.dto.Request.AutorDto;
import backend.dto.Response.AutorResponse;

@Component
public class AutorMapper {

    public AutorResponse converterParaResponse(AutorEntity autor) {
        return converterParaResponse(autor, 0L);
    }

    public AutorResponse converterParaResponse(AutorEntity autor, Long quantidadeLivros) {

        return AutorResponse.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .biografia(autor.getBiografia())
                .dataNascimento(autor.getDataNascimento())
                .nacionalidade(autor.getNacionalidade())
                .quantidadeLivros(quantidadeLivros)
                .build();
    }

    public List<AutorResponse> converterParaListaDeResponse(List<AutorEntity> autores) {

        return autores.stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public AutorEntity converterParaEntidade(AutorDto autorDto) {

        return AutorEntity.builder()
                .nome(autorDto.getNome())
                .biografia(autorDto.getBiografia())
                .dataNascimento(autorDto.getDataNascimento())
                .nacionalidade(autorDto.getNacionalidade())
                .build();
    }

    public AutorEntity editarEntidade(AutorEntity autor, AutorDto autorDto) {

        autor.setNome(autorDto.getNome());
        autor.setBiografia(autorDto.getBiografia());
        autor.setDataNascimento(autorDto.getDataNascimento());
        autor.setNacionalidade(autorDto.getNacionalidade());

        return autor;
    }

    
}
