package backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import backend.Entity.CategoriaEntity;
import backend.dto.Request.CategoriaDto;
import backend.dto.Response.CategoriaResponse;
import backend.exception.RecursoEmUsoException;
import backend.exception.categoria.CategoriaJaExistenteException;
import backend.exception.categoria.CategoriaNaoEncontradaException;
import backend.mapper.CategoriaMapper;
import backend.repository.CategoriaRepository;
import backend.repository.LivroRepository;
import backend.repository.projection.ContagemPorId;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final LivroRepository livroRepository;

    public List<CategoriaResponse> buscarTodasAsCategorias() {
        Map<Integer, Long> contagens = categoriaRepository.contarLivrosPorCategoria().stream()
                .collect(Collectors.toMap(ContagemPorId::getId, ContagemPorId::getQuantidadeLivros));
        return categoriaRepository.findAll().stream()
                .map(categoria -> categoriaMapper.converterParaResponse(
                        categoria, contagens.getOrDefault(categoria.getId(), 0L)))
                .toList();
    }

    public CategoriaResponse adicionarCategoria(CategoriaDto categoriaDto) {
        if (categoriaRepository.existsByNomeIgnoreCase(categoriaDto.getNome())) {
            throw new CategoriaJaExistenteException("Categoria já cadastrada com o nome: " + categoriaDto.getNome());
        }
        CategoriaEntity nova = CategoriaEntity.builder()
                .nome(categoriaDto.getNome())
                .descricao(categoriaDto.getDescricao())
                .build();
        return categoriaMapper.converterParaResponse(categoriaRepository.save(nova), 0L);
    }

    public CategoriaResponse buscarCategoriaPorId(Integer id) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        return categoriaMapper.converterParaResponse(categoria, livroRepository.countByCategoria_Id(id));
    }

    public CategoriaResponse editarCategoria(Integer id, CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        if (categoriaRepository.existsByNomeIgnoreCaseAndIdNot(categoriaDto.getNome(), id)) {
            throw new CategoriaJaExistenteException("Categoria já cadastrada com o nome: " + categoriaDto.getNome());
        }
        categoria.setNome(categoriaDto.getNome());
        categoria.setDescricao(categoriaDto.getDescricao());
        return categoriaMapper.converterParaResponse(categoriaRepository.save(categoria), livroRepository.countByCategoria_Id(id));
    }

    public CategoriaResponse deletarCategoria(Integer id) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        if (livroRepository.existsByCategoria_Id(id)) {
            throw new RecursoEmUsoException("Não é possível excluir uma categoria que possui livros vinculados.");
        }
        categoriaRepository.delete(categoria);
        return categoriaMapper.converterParaResponse(categoria, 0L);
    }
}
