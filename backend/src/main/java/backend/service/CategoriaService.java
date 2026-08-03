package backend.service;
import java.util.List;
import org.springframework.stereotype.Service;

import backend.Entity.CategoriaEntity;
import backend.dto.Request.CategoriaDto;
import backend.dto.Response.CategoriaResponse;
import backend.exception.categoria.CategoriaJaExistenteException;
import backend.exception.categoria.CategoriaNaoEncontradaException;
import backend.mapper.CategoriaMapper;
import backend.repository.CategoriaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public List<CategoriaResponse> buscarTodasAsCategorias() {
        List<CategoriaEntity> listaDeCategorias = categoriaRepository.findAll();

        return categoriaMapper.converterParaListaDeResponse(listaDeCategorias);
    }

    public CategoriaResponse adicionarCategoria(CategoriaDto categoriaDto) {
        if (categoriaRepository.existsByNomeIgnoreCase(categoriaDto.getNome())) {
            throw new CategoriaJaExistenteException("Categoria já cadastrada com o Nome: " + categoriaDto.getNome());
        }

        CategoriaEntity categoriaNova = CategoriaEntity.builder()
                .nome(categoriaDto.getNome())
                .descricao(categoriaDto.getDescricao())
                .build();

        CategoriaEntity categoriaSalva = categoriaRepository.save(categoriaNova);

        return categoriaMapper.converterParaResponse(categoriaSalva);
    }

    public CategoriaResponse buscarCategoriaPorId(Integer id) {
        CategoriaEntity entidadeRecebida = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        
        return categoriaMapper.converterParaResponse(entidadeRecebida);
    }

    public CategoriaResponse editarCategoria(Integer id, CategoriaDto categoriaDto) {

        if (categoriaRepository.existsByNomeIgnoreCase(categoriaDto.getNome())) {
            throw new CategoriaJaExistenteException("Categoria já cadastrada com o Nome: " + categoriaDto.getNome());
        }

        CategoriaEntity entidadeRecebida = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));

        entidadeRecebida.setNome(categoriaDto.getNome());
        entidadeRecebida.setDescricao(categoriaDto.getDescricao());

        CategoriaEntity categoriaAtualizada = categoriaRepository.save(entidadeRecebida);

        return categoriaMapper.converterParaResponse(categoriaAtualizada);
    }

    public CategoriaResponse deletarCategoria(Integer id) {
        CategoriaEntity entidadeRecebida = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));

        categoriaRepository.delete(entidadeRecebida);

        return categoriaMapper.converterParaResponse(entidadeRecebida);
    }
}