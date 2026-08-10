package backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import backend.repository.projection.ContagemPorId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AutorService {

    private final AutorMapper autorMapper;
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public AutorResponse criarAutor(AutorDto autorDto) {
        if (autorRepository.existsByNomeIgnoreCase(autorDto.getNome())) {
            throw new AutorJaExistenteException("Autor já cadastrado com nome: " + autorDto.getNome());
        }
        AutorEntity autorSalvo = autorRepository.save(autorMapper.converterParaEntidade(autorDto));
        return autorMapper.converterParaResponse(autorSalvo, 0L);
    }

    public List<AutorResponse> buscarTodosAutores() {
        Map<Integer, Long> contagens = autorRepository.contarLivrosPorAutor().stream()
                .collect(Collectors.toMap(ContagemPorId::getId, ContagemPorId::getQuantidadeLivros));
        return autorRepository.findAll().stream()
                .map(autor -> autorMapper.converterParaResponse(autor, contagens.getOrDefault(autor.getId(), 0L)))
                .toList();
    }

    public AutorResponse buscarPorId(Integer id) {
        AutorEntity autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorIdNaoEncontrado("Id do autor não encontrado"));
        return autorMapper.converterParaResponse(autor, livroRepository.countByAutor_Id(id));
    }

    public List<AutorResponse> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return List.of();
        return autorRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(autor -> autorMapper.converterParaResponse(
                        autor, livroRepository.countByAutor_Id(autor.getId())))
                .toList();
    }

    public AutorResponse editarAutor(Integer id, AutorDto autorDto) {
        AutorEntity autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorIdNaoEncontrado("Id do autor não encontrado"));
        if (autorRepository.existsByNomeIgnoreCaseAndIdNot(autorDto.getNome(), id)) {
            throw new AutorJaExistenteException("Autor já cadastrado com nome: " + autorDto.getNome());
        }
        AutorEntity atualizado = autorMapper.editarEntidade(autor, autorDto);
        return autorMapper.converterParaResponse(autorRepository.save(atualizado), livroRepository.countByAutor_Id(id));
    }

    public AutorResponse deletarAutor(Integer id) {
        AutorEntity autor = autorRepository.findById(id)
                .orElseThrow(() -> new AutorIdNaoEncontrado("Id do autor não encontrado"));
        if (livroRepository.existsByAutor_Id(id)) {
            throw new RecursoEmUsoException("Não é possível excluir um autor que possui livros vinculados.");
        }
        autorRepository.delete(autor);
        return autorMapper.converterParaResponse(autor, 0L);
    }
}
