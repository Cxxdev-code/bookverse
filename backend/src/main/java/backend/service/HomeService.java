package backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import backend.Entity.CategoriaEntity;
import backend.Entity.StatusLivro;
import backend.dto.Response.CategoriaDestaqueResponse;
import backend.dto.Response.HomeResponse;
import backend.dto.Response.HomeTotaisResponse;
import backend.dto.Response.LivroCardResponse;
import backend.mapper.LivroMapper;
import backend.repository.AutorRepository;
import backend.repository.CategoriaRepository;
import backend.repository.LivroRepository;
import backend.repository.projection.ContagemPorId;
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
