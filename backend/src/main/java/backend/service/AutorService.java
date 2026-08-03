package backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.Entity.AutorEntity;
import backend.dto.Request.AutorDto;
import backend.dto.Response.AutorResponse;
import backend.exception.autor.AutorIdNaoEncontrado;
import backend.exception.autor.AutorJaExistenteException;
import backend.mapper.AutorMapper;
import backend.repository.AutorRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AutorService {

    private final AutorMapper autorMapper;
    private final AutorRepository autorRepository;
    
    public AutorResponse criarAutor(AutorDto autorDto) {

        if (autorRepository.existsByNome(autorDto.getNome())) {
            throw new AutorJaExistenteException(
                    "Autor já cadastrado com nome: " + autorDto.getNome());
        }

        AutorEntity autorEntity =
                autorMapper.converterParaEntidade(autorDto);

        AutorEntity autorSalvo =
                autorRepository.save(autorEntity);

        return autorMapper.converterParaResponse(autorSalvo);
    }

    public List<AutorResponse> buscarTodosAutores(){
        List<AutorEntity> autoresEntitys = autorRepository.findAll();


        return autorMapper.converterParaListaDeResponse(autoresEntitys);

    }

    public AutorResponse buscarPorId(Integer id){
        AutorEntity autorEntity =  autorRepository.findById(id)
            .orElseThrow(() -> new AutorIdNaoEncontrado("Id do autor não encontrado"));
        
        return autorMapper.converterParaResponse(autorEntity);
    }


    public List<AutorResponse> buscarPorNome(String nome){
        if (nome == null || nome.trim().isEmpty()) {
            return List.of(); 
        }

        List<AutorEntity> autorEntity = autorRepository.findByNomeContainingIgnoreCase(nome);
        return autorMapper.converterParaListaDeResponse(autorEntity);
    }

}