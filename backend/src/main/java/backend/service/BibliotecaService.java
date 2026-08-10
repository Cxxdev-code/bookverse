package backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.Entity.AutorEntity;
import backend.Entity.CategoriaEntity;
import backend.Entity.LivroEntity;
import backend.dto.Request.LivroDto;
import backend.dto.Response.LivroResponse;
import backend.exception.autor.AutorIdNaoEncontrado;
import backend.exception.categoria.CategoriaNaoEncontradaException;
import backend.exception.livro.LivroJaExistenteException;
import backend.exception.livro.LivroNaoEncontradoPorIdExcption;
import backend.mapper.LivroMapper;
import backend.repository.AutorRepository;
import backend.repository.CategoriaRepository;
import backend.repository.LivroRepository;
import lombok.AllArgsConstructor;



@AllArgsConstructor
@Service
public class BibliotecaService {
    
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroMapper livroMapper;

    public List<LivroResponse> buscarTodosOsLivros() {
        List<LivroEntity> listaDeLivros = livroRepository.findAll();
        return livroMapper.converterParaListaDeResponse(listaDeLivros);
    }

    public LivroResponse adicionarLivro(LivroDto livroDto) {
        String titulo = livroDto.getTitulo();
        if (livroRepository.existsByTituloIgnoreCase(titulo)) {
            throw new LivroJaExistenteException("Já existe um livro cadastrado com o título: " + titulo);
        }

        if (livroRepository.existsByIsbn(livroDto.getIsbn())) {
            throw new LivroJaExistenteException("Já existe um livro cadastrado com o ISBN: " + livroDto.getIsbn());
        }

        // 1. Buscar Autor e Categoria pelos IDs fornecidos
        AutorEntity autor = autorRepository.findById(livroDto.getAutorId())
            .orElseThrow(() -> new AutorIdNaoEncontrado("Autor não encontrado com ID: " + livroDto.getAutorId()));
            
        CategoriaEntity categoria = categoriaRepository.findById(livroDto.getCategoriaId())
            .orElseThrow(() -> new CategoriaNaoEncontradaException(livroDto.getCategoriaId()));

        // 2. Montar a entidade de Livro utilizando os objetos encontrados no banco
        LivroEntity livroAdicionado = LivroEntity.builder()
            .titulo(livroDto.getTitulo())
            .isbn(livroDto.getIsbn())
            .descricao(livroDto.getDescricao())
            .dataPublicacao(livroDto.getPublicado())
            .autor(autor)         // Atribui a entidade Autor completa
            .categoria(categoria) // Atribui a entidade Categoria completa
            .build();

        LivroEntity livroSalvo = livroRepository.save(livroAdicionado);

        return livroMapper.converterParaResponse(livroSalvo);
    }  

    public LivroResponse buscarLivroPorId(Integer id) {
        LivroEntity entidadeLivroRecebida = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        return livroMapper.converterParaResponse(entidadeLivroRecebida);
    }

    public List<LivroResponse> buscarLivroPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return List.of(); 
        }
        List<LivroEntity> livrosEncontrados = livroRepository.findByTituloContainingIgnoreCase(titulo);
        return livroMapper.converterParaListaDeResponse(livrosEncontrados);
    }

    public LivroResponse editarLivro(Integer id, LivroDto livroDto) {
        LivroEntity entidadeLivroRecebida = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));

        if (livroRepository.existsByTituloIgnoreCaseAndIdNot(livroDto.getTitulo(), id)) {
            throw new LivroJaExistenteException(
                    "Já existe um livro cadastrado com o título: " + livroDto.getTitulo());
        }

        if (livroRepository.existsByIsbnAndIdNot(livroDto.getIsbn(), id)) {
            throw new LivroJaExistenteException(
                    "Já existe um livro cadastrado com o ISBN: " + livroDto.getIsbn());
        }
        
        AutorEntity autor = autorRepository.findById(livroDto.getAutorId())
            .orElseThrow(() -> new AutorIdNaoEncontrado("Autor não encontrado com ID: " + livroDto.getAutorId()));
            
        CategoriaEntity categoria = categoriaRepository.findById(livroDto.getCategoriaId())
            .orElseThrow(() -> new CategoriaNaoEncontradaException(livroDto.getCategoriaId()));
        
        entidadeLivroRecebida.setTitulo(livroDto.getTitulo());
        entidadeLivroRecebida.setDescricao(livroDto.getDescricao());
        entidadeLivroRecebida.setDataPublicacao(livroDto.getPublicado());
        entidadeLivroRecebida.setIsbn(livroDto.getIsbn());
        entidadeLivroRecebida.setAutor(autor);
        entidadeLivroRecebida.setCategoria(categoria);

        LivroEntity livroAtualizado = livroRepository.save(entidadeLivroRecebida);

        return livroMapper.converterParaResponse(livroAtualizado);
    }

    public LivroResponse deletarLivro(Integer id) {
        LivroEntity entidadeLivroRecebida = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoPorIdExcption(id));
        
        livroRepository.delete(entidadeLivroRecebida);

        return livroMapper.converterParaResponse(entidadeLivroRecebida);
    }
}
