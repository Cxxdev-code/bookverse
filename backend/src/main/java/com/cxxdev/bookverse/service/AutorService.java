package com.cxxdev.bookverse.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cxxdev.bookverse.entity.AutorEntity;
import com.cxxdev.bookverse.dto.request.AutorDto;
import com.cxxdev.bookverse.dto.response.AutorResponse;
import com.cxxdev.bookverse.exception.RecursoEmUsoException;
import com.cxxdev.bookverse.exception.autor.AutorIdNaoEncontrado;
import com.cxxdev.bookverse.exception.autor.AutorJaExistenteException;
import com.cxxdev.bookverse.mapper.AutorMapper;
import com.cxxdev.bookverse.repository.AutorRepository;
import com.cxxdev.bookverse.repository.LivroRepository;
import com.cxxdev.bookverse.repository.projection.ContagemPorId;
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
