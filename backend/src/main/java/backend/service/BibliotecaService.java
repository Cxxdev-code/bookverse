package backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import backend.Entity.AutorEntity;
import backend.Entity.CategoriaEntity;
import backend.Entity.LivroEntity;
import backend.Entity.StatusLivro;
import backend.dto.Request.LivroDto;
import backend.dto.Response.LivroCardResponse;
import backend.dto.Response.LivroDetalheResponse;
import backend.dto.Response.LivroResponse;
import backend.dto.Response.PageResponse;
import backend.exception.RequisicaoInvalidaException;
import backend.exception.autor.AutorIdNaoEncontrado;
import backend.exception.categoria.CategoriaNaoEncontradaException;
import backend.exception.livro.LivroJaExistenteException;
import backend.exception.livro.LivroNaoEncontradoPorIdExcption;
import backend.mapper.LivroMapper;
import backend.repository.AutorRepository;
import backend.repository.CategoriaRepository;
import backend.repository.LivroRepository;
import backend.repository.LivroSpecifications;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BibliotecaService {

    private static final int TAMANHO_MAXIMO_PAGINA = 50;

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroMapper livroMapper;

    /** Rota legada: preservada para o front atual durante a migração. */
    public List<LivroResponse> buscarTodosOsLivros() {
        return livroMapper.converterParaListaDeResponse(livroRepository.findAll());
    }

    /** Catálogo público paginado, leve e filtrável. */
    public PageResponse<LivroCardResponse> buscarCatalogo(
            int page, int size, String busca, Integer categoriaId, Integer autorId, String ordem) {
        if (size > TAMANHO_MAXIMO_PAGINA) {
            throw new RequisicaoInvalidaException("O tamanho máximo da página é " + TAMANHO_MAXIMO_PAGINA + ".");
        }

        Specification<LivroEntity> filtros = LivroSpecifications.publicado()
                .and(LivroSpecifications.textoContem(busca))
                .and(LivroSpecifications.categoriaIgual(categoriaId))
                .and(LivroSpecifications.autorIgual(autorId));

        Page<LivroEntity> resultado = livroRepository.findAll(
                filtros, criarPaginacao(page, size, ordem));
        return PageResponse.from(resultado.map(livroMapper::converterParaCardResponse));
    }

    public LivroResponse adicionarLivro(LivroDto livroDto) {
        validarUnicidadeParaCriacao(livroDto);
        AutorEntity autor = buscarAutor(livroDto.getAutorId());
        CategoriaEntity categoria = buscarCategoria(livroDto.getCategoriaId());

        LivroEntity livro = LivroEntity.builder()
                .titulo(livroDto.getTitulo().trim())
                .isbn(livroDto.getIsbn().trim())
                .descricao(livroDto.getDescricao().trim())
                .dataPublicacao(livroDto.getPublicado())
                .autor(autor)
                .categoria(categoria)
                .capaUrl(limpar(livroDto.getCapaUrl()))
                .urlLeitura(limpar(livroDto.getUrlLeitura()))
                .numeroPaginas(livroDto.getNumeroPaginas())
                .idioma(limpar(livroDto.getIdioma()))
                .editora(limpar(livroDto.getEditora()))
                .edicao(limpar(livroDto.getEdicao()))
                .classificacaoEtaria(limpar(livroDto.getClassificacaoEtaria()))
                // Enquanto não existe autenticação de administrador, novos livros continuam visíveis.
                .status(livroDto.getStatus() == null ? StatusLivro.PUBLICADO : livroDto.getStatus())
                .destaque(Boolean.TRUE.equals(livroDto.getDestaque()))
                .build();
        return livroMapper.converterParaResponse(livroRepository.save(livro));
    }

    public LivroDetalheResponse buscarLivroPorId(Integer id, boolean podeVerNaoPublicados) {
        LivroEntity livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        if (livro.getStatus() != StatusLivro.PUBLICADO && !podeVerNaoPublicados) {
            throw new AccessDeniedException("Este livro ainda não está disponível para leitura.");
        }
        return livroMapper.converterParaDetalheResponse(livro);
    }

    /** Busca legada por título; a nova busca está em GET /api/livros?busca=. */
    public List<LivroResponse> buscarLivroPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) return List.of();
        return livroMapper.converterParaListaDeResponse(livroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream().filter(livro -> livro.getStatus() == StatusLivro.PUBLICADO).toList());
    }

    public LivroResponse editarLivro(Integer id, LivroDto livroDto) {
        LivroEntity livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        validarUnicidadeParaEdicao(id, livroDto);

        livro.setTitulo(livroDto.getTitulo().trim());
        livro.setDescricao(livroDto.getDescricao().trim());
        livro.setDataPublicacao(livroDto.getPublicado());
        livro.setIsbn(livroDto.getIsbn().trim());
        livro.setAutor(buscarAutor(livroDto.getAutorId()));
        livro.setCategoria(buscarCategoria(livroDto.getCategoriaId()));
        livro.setCapaUrl(limpar(livroDto.getCapaUrl()));
        livro.setUrlLeitura(limpar(livroDto.getUrlLeitura()));
        livro.setNumeroPaginas(livroDto.getNumeroPaginas());
        livro.setIdioma(limpar(livroDto.getIdioma()));
        livro.setEditora(limpar(livroDto.getEditora()));
        livro.setEdicao(limpar(livroDto.getEdicao()));
        livro.setClassificacaoEtaria(limpar(livroDto.getClassificacaoEtaria()));
        if (livroDto.getStatus() != null) livro.setStatus(livroDto.getStatus());
        if (livroDto.getDestaque() != null) livro.setDestaque(livroDto.getDestaque());

        return livroMapper.converterParaResponse(livroRepository.save(livro));
    }

    public LivroResponse deletarLivro(Integer id) {
        LivroEntity livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        livroRepository.delete(livro);
        return livroMapper.converterParaResponse(livro);
    }

    private Pageable criarPaginacao(int page, int size, String ordem) {
        String valor = ordem == null || ordem.isBlank() ? "recentes" : ordem;
        Sort sort = switch (valor) {
            case "recentes" -> Sort.by(Sort.Direction.DESC, "dataPublicacao").and(Sort.by(Sort.Direction.DESC, "id"));
            case "antigos" -> Sort.by(Sort.Direction.ASC, "dataPublicacao").and(Sort.by(Sort.Direction.ASC, "id"));
            case "titulo_asc" -> Sort.by(Sort.Direction.ASC, "titulo");
            case "titulo_desc" -> Sort.by(Sort.Direction.DESC, "titulo");
            default -> throw new RequisicaoInvalidaException(
                    "A ordenação deve ser: recentes, antigos, titulo_asc ou titulo_desc.");
        };
        return PageRequest.of(page, size, sort);
    }

    private AutorEntity buscarAutor(Integer autorId) {
        return autorRepository.findById(autorId)
                .orElseThrow(() -> new AutorIdNaoEncontrado("Autor não encontrado com ID: " + autorId));
    }

    private CategoriaEntity buscarCategoria(Integer categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(categoriaId));
    }

    private void validarUnicidadeParaCriacao(LivroDto dto) {
        if (livroRepository.existsByTituloIgnoreCase(dto.getTitulo())) {
            throw new LivroJaExistenteException("Já existe um livro cadastrado com o título: " + dto.getTitulo());
        }
        if (livroRepository.existsByIsbn(dto.getIsbn())) {
            throw new LivroJaExistenteException("Já existe um livro cadastrado com o ISBN: " + dto.getIsbn());
        }
    }

    private void validarUnicidadeParaEdicao(Integer id, LivroDto dto) {
        if (livroRepository.existsByTituloIgnoreCaseAndIdNot(dto.getTitulo(), id)) {
            throw new LivroJaExistenteException("Já existe um livro cadastrado com o título: " + dto.getTitulo());
        }
        if (livroRepository.existsByIsbnAndIdNot(dto.getIsbn(), id)) {
            throw new LivroJaExistenteException("Já existe um livro cadastrado com o ISBN: " + dto.getIsbn());
        }
    }

    private String limpar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
