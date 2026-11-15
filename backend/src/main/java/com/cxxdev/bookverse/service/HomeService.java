package com.cxxdev.bookverse.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cxxdev.bookverse.entity.CategoriaEntity;
import com.cxxdev.bookverse.entity.StatusLivro;
import com.cxxdev.bookverse.dto.response.CategoriaDestaqueResponse;
import com.cxxdev.bookverse.dto.response.HomeResponse;
import com.cxxdev.bookverse.dto.response.HomeTotaisResponse;
import com.cxxdev.bookverse.dto.response.LivroCardResponse;
import com.cxxdev.bookverse.mapper.LivroMapper;
import com.cxxdev.bookverse.repository.AutorRepository;
import com.cxxdev.bookverse.repository.CategoriaRepository;
import com.cxxdev.bookverse.repository.LivroRepository;
import com.cxxdev.bookverse.repository.projection.ContagemPorId;
import lombok.AllArgsConstructor;

/** Reúne em uma única chamada os dados necessários para montar a página inicial. */
@Service
@AllArgsConstructor
public class HomeService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroMapper livroMapper;

    public HomeResponse carregarHome() {
        List<LivroCardResponse> destaques = livroRepository
                .findTop6ByStatusAndDestaqueTrueOrderByDataPublicacaoDesc(StatusLivro.PUBLICADO)
                .stream().map(livroMapper::converterParaCardResponse).toList();
        List<LivroCardResponse> recentes = livroRepository
                .findTop6ByStatusOrderByDataPublicacaoDesc(StatusLivro.PUBLICADO)
                .stream().map(livroMapper::converterParaCardResponse).toList();

        Map<Integer, Long> contagens = categoriaRepository.contarLivrosPorCategoria().stream()
                .collect(Collectors.toMap(ContagemPorId::getId, ContagemPorId::getQuantidadeLivros));
        List<CategoriaDestaqueResponse> categorias = categoriaRepository.findAll().stream()
                .sorted(Comparator.comparingLong((CategoriaEntity categoria) ->
                        contagens.getOrDefault(categoria.getId(), 0L)).reversed()
                        .thenComparing(CategoriaEntity::getNome, String.CASE_INSENSITIVE_ORDER))
                .limit(6)
                .map(categoria -> CategoriaDestaqueResponse.builder()
                        .id(categoria.getId())
                        .nome(categoria.getNome())
                        .descricao(categoria.getDescricao())
                        .quantidadeLivros(contagens.getOrDefault(categoria.getId(), 0L))
                        .build())
                .toList();

        return HomeResponse.builder()
                .totais(HomeTotaisResponse.builder()
                        .livros(livroRepository.countByStatus(StatusLivro.PUBLICADO))
                        .autores(autorRepository.count())
                        .categorias(categoriaRepository.count())
                        .build())
                .destaques(destaques)
                .recentes(recentes)
                .categorias(categorias)
                .build();
    }
}
