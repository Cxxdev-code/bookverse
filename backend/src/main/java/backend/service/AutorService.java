package backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.Entity.AutorEntity;
import backend.dto.Request.AutorDto;
import backend.dto.Response.AutorResponse;
import backend.exception.RecursoEmUsoException;
import backend.exception.autor.AutorIdNaoEncontrado;
import backend.exception.autor.AutorJaExistenteException;
import backend.mapper.AutorMapper;
import backend.repository.AutorRepository;
import backend.repository.LivroRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AutorService {

    private final AutorMapper autorMapper;
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    
    public AutorResponse criarAutor(AutorDto autorDto) {

        if (autorRepository.existsByNomeIgnoreCase(autorDto.getNome())) {
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

    public AutorResponse editarAutor(Integer id, AutorDto autorDto) {
        AutorEntity autorEntity = autorRepository.findById(id)
                .orElseThrow(() -> new AutorIdNaoEncontrado("Id do autor não encontrado"));

        if (autorRepository.existsByNomeIgnoreCaseAndIdNot(autorDto.getNome(), id)) {
            throw new AutorJaExistenteException(
                    "Autor já cadastrado com nome: " + autorDto.getNome());
        }

        AutorEntity autorAtualizado = autorMapper.editarEntidade(autorEntity, autorDto);
        return autorMapper.converterParaResponse(autorRepository.save(autorAtualizado));
    }

    public AutorResponse deletarAutor(Integer id) {
        AutorEntity autorEntity = autorRepository.findById(id)
                .orElseThrow(() -> new AutorIdNaoEncontrado("Id do autor não encontrado"));

        if (livroRepository.existsByAutor_Id(id)) {
            throw new RecursoEmUsoException(
                    "Não é possível excluir um autor que possui livros vinculados.");
        }

        autorRepository.delete(autorEntity);
        return autorMapper.converterParaResponse(autorEntity);
    }

}
